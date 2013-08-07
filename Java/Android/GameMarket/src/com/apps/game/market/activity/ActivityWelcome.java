package com.apps.game.market.activity;

import com.apps.game.market.App;
import com.apps.game.market.R;
import com.apps.game.market.entity.EntityEntry;
import com.apps.game.market.entity.EntityUpgrade;
import com.apps.game.market.entity.app.EntityColumn;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.RequestAd;
import com.apps.game.market.request.RequestColumn;
import com.apps.game.market.request.RequestEntry;
import com.apps.game.market.request.RequestTag;
import com.apps.game.market.request.RequestUpgrade;
import com.apps.game.market.request.callback.RequestCallBackAd;
import com.apps.game.market.request.callback.RequestCallBackColumn;
import com.apps.game.market.request.callback.RequestCallBackEntry;
import com.apps.game.market.request.callback.RequestCallBackTag;
import com.apps.game.market.request.callback.RequestCallBackUpgrade;
import com.apps.game.market.service.ServiceManager;
import com.apps.game.market.service.ServiceVersion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.Intent;
import android.os.Bundle;

public class ActivityWelcome extends Activity implements RequestCallBackColumn, RequestCallBackTag, RequestCallBackAd, RequestCallBackEntry, RequestCallBackUpgrade {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);		
		init();		
	}
			
	@Override
	protected void onPause() {
		ServiceManager.startUpgrade(this, 10000L, 7200000L);
		super.onPause();
	}

	@Override
	protected void onResume() {		
		ServiceManager.stopUpgrade(this);
		super.onResume();		
	}

	@Override
	protected void onStop() {
		super.onStop();				
		finish();
	}

	@Override
	protected void onDestroy() {		
		super.onDestroy();				
	}
	
	@Override
	public void onCallBackAd(boolean success) {		
		final Intent intent = new Intent(this, ActivityIndex.class); 
        startActivity(intent);
	}
	
	@Override
	public void onCallBackTag(boolean success) {
		final GlobalData globalData = GlobalData.globalData;
		final EntityColumn entityColumn = globalData.getColumn(0);
		if (entityColumn != null) {
			final RequestAd requestAd = new RequestAd(this);
			requestAd.request(entityColumn.getId());
		}
	}

	@Override
	public void onCallBackColumn(boolean success) {
		if (success) {
			final RequestTag requestTag = new RequestTag(this);
			requestTag.request();
		} else {
			final RequestCallBackColumn callback = this;
			final ActivityWelcome activity = this;
			final Dialog dialog = new AlertDialog.Builder(this).setTitle(R.string.welcome_error).setMessage(R.string.welcome_no_network).setPositiveButton(R.string.welcome_retry, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					final RequestColumn requestColumn = new RequestColumn(callback);
					requestColumn.request();
				}
			}).setNegativeButton(R.string.welcome_quit, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					activity.finish();					
				}
			}).create();
			
			dialog.show();
		}
	}
	
	@Override
	public void onCallBackEntry(boolean success, EntityEntry entityEntry) {
		if (success && entityEntry.getEntryOpen()) {
			entityEntry.setEntryOpen(false);
			EntityEntry.writeEntry(this, entityEntry);
		}
		
		final RequestColumn requestColumn = new RequestColumn(this);
		requestColumn.request();	
	}
	
	@Override
	public void onCallBackUpgrade(final EntityUpgrade entityUpgrade) {
		if (entityUpgrade != null) {			
			final int upgradeVersionCode = entityUpgrade.getUpgradeVersionCode();
			final String upgradeVersionName = entityUpgrade.getUpgradeVersionName();
			final String upgradeUrl = entityUpgrade.getUpgradeUrl();
			final long upgradeFileSize = entityUpgrade.getUpgradeFileSize();
			final int upgradeForce = entityUpgrade.getUpgradeForce();
			final int entryTable = entityUpgrade.getEntryTable();
			
			final Intent intent = new Intent(this, ServiceVersion.class);				
			intent.putExtra("upgrade_version_code", upgradeVersionCode);
			intent.putExtra("upgrade_version_name", upgradeVersionName);
			intent.putExtra("upgrade_url", upgradeUrl);
			intent.putExtra("upgrade_filesize", upgradeFileSize);
			intent.putExtra("upgrade_force", upgradeForce);
			intent.putExtra("entry_table", entryTable);
			
			final String version = getString(R.string.welcome_version, upgradeVersionName);
			
			if (upgradeForce == 0) {
				final Dialog dialog = new AlertDialog.Builder(this).setTitle(version).setMessage(R.string.welcome_comfirm_upgrade).setPositiveButton(R.string.welcome_upgrade, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						entry();
						startService(intent);
					}
				}).setNegativeButton(R.string.welcome_later, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						entry();					
					}
				}).create();			
				
				dialog.show();
			} else {
				final ActivityWelcome activity = this;
				final Dialog dialog = new AlertDialog.Builder(this).setTitle(version).setMessage(R.string.welcome_comfirm_upgrade).setPositiveButton(R.string.welcome_upgrade, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {					
						startService(intent);
					}
				}).setNegativeButton(R.string.welcome_quit, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						activity.finish();
					}
				}).create();			
				
				dialog.show();
			}
		} else
			entry();	
	}
			
	private boolean init() {
		final App app = (App) getApplication();
		final GlobalData globalData = app.globalData;
		if (!globalData.init(this))
			return false;
				
		final GlobalObject globalObject = app.globalObject;
		globalObject.setContext(this);
		globalObject.initDeviceInfo();		
		
		final EntityEntry entitySrcEntry = EntityEntry.getSrcEntry(this);
		final RequestUpgrade upgrade = new RequestUpgrade(this, entitySrcEntry, this);
		upgrade.request();
						
		return true;
	}
	
	private String getVersionName() {
		final PackageManager packageManager = getPackageManager();
		final String packageName = getPackageName();
		
		try {
			final PackageInfo packInfo = packageManager.getPackageInfo(packageName, 0);
			return packInfo.versionName;			
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	private void entry() {
		final EntityEntry entitySrcEntry = EntityEntry.getSrcEntry(this);
		final EntityEntry entityDestEntry = EntityEntry.getDestEntry(this);
		final EntityEntry entityEntry = EntityEntry.diffEntry(entitySrcEntry, entityDestEntry);
		final String versionName = getVersionName();
		
		if (entityEntry != null) {
			final RequestEntry requestEntry = new RequestEntry(this, entityEntry, versionName);
			requestEntry.request();
		} else {
			final RequestColumn requestColumn = new RequestColumn(this);
			requestColumn.request();
		}
	}
}
