package com.apps.game.market.service;

import com.apps.game.market.entity.EntityUpgrade;
import com.apps.game.market.request.RequestVersion;
import com.apps.game.market.request.callback.RequestCallBackVersion;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServiceVersion extends Service implements RequestCallBackVersion {

	@Override
	public IBinder onBind(Intent intent) {		
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		ServiceManager.Upgrade = false;
		ServiceManager.stopUpgrade(this);
		final int upgradeVersionCode = intent.getIntExtra("upgrade_version_code", 0);
		final String upgradeVersionName = intent.getStringExtra("upgrade_version_name");
		final String upgradeUrl = intent.getStringExtra("upgrade_url");
		final long upgradeFileSize = intent.getLongExtra("upgrade_filesize", 0);
		final int upgradeForce = intent.getIntExtra("upgrade_force", 0);
		final int entryTable = intent.getIntExtra("entry_table", 0);
		
		final EntityUpgrade entityUpgrade = new EntityUpgrade();
		entityUpgrade.setUpgradeVersionCode(upgradeVersionCode);
		entityUpgrade.setUpgradeVersionName(upgradeVersionName);
		entityUpgrade.setUpgradeUrl(upgradeUrl);
		entityUpgrade.setUpgradeFileSize(upgradeFileSize);
		entityUpgrade.setUpgradeForce(upgradeForce);
		entityUpgrade.setEntryTable(entryTable);
		
		final RequestVersion requestVersion = new RequestVersion(this, this);
		requestVersion.request(entityUpgrade);
		super.onStart(intent, startId);		
	}

	@Override
	public void onCallBackVersion(boolean success, EntityUpgrade entityUpgrade) {
		ServiceManager.Upgrade = true;
		stopSelf();
	}
}
