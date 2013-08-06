package com.apps.game.market.request;

import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Message;

import com.apps.game.market.R;
import com.apps.game.market.entity.EntityEntry;
import com.apps.game.market.entity.EntityRequest;
import com.apps.game.market.entity.EntityResponse;
import com.apps.game.market.entity.EntityUpgrade;
import com.apps.game.market.request.callback.RequestCallBackUpgrade;

public class RequestUpgrade extends RequestBase {
	private final EntityEntry mEntityEntry;
	private final RequestCallBackUpgrade mCallBack;
	
	public RequestUpgrade(Context context, EntityEntry entityEntry, RequestCallBackUpgrade callBack) {		
		mContext = context;
		mEntityEntry = entityEntry;
		mCallBack = callBack;
		mHandler = new Handler(this);
	}

	@Override
	protected void onTask(EntityRequest req) {
		req.setCharset("UTF-8");
		String url = setSpUrl(R.string.request_sp_upgrade);
		final Map<String, String> tree = new TreeMap<String, String>();
		tree.put("upgrade_table", setString(mEntityEntry.getEntryTable()));
		
		if (!setKey(tree)) {
			mHandler.sendEmptyMessage(1);
			return;
		}
		
		final String s = setRequest(tree);
		url += "?" + s;
		
		req.setString(true);
		req.setUrl(url);
		req.setBody(null);
		
		EntityUpgrade entityUpgrade = null;
		final EntityResponse resp = mHttpClass.request(req);
		if (resp != null)			
			entityUpgrade = parse(resp);
	
		if (entityUpgrade != null) {
			final PackageManager packageManager = mContext.getPackageManager();
			final String packageName = mContext.getPackageName();
			
			try {
				final PackageInfo packInfo = packageManager.getPackageInfo(packageName, 0);
				final int versionCode = packInfo.versionCode;
				final int upgradeVersionCode = entityUpgrade.getUpgradeVersionCode();
				if (versionCode < upgradeVersionCode) {
					final Message msg = new Message();
					msg.what = 0;
					msg.obj = entityUpgrade;
					mHandler.sendMessage(msg);
					return;
				}
			} catch (NameNotFoundException e) {
							
			}
		}	
		
		mHandler.sendEmptyMessage(1);
	}

	@Override
	protected void onMessage(Message msg) {
		if (msg.what == 0) {
			final EntityUpgrade entityUpgrade = (EntityUpgrade) msg.obj;
			mCallBack.onCallBackUpgrade(entityUpgrade);
		} else
			mCallBack.onCallBackUpgrade(null);
	}
	
	private EntityUpgrade parse(EntityResponse resp) {
		final JSONObject content = getContent(resp);
		if (content == null)
			return null;
		
		try {
			final int upgradeVersionCode = content.getInt("upgrade_version_code");
			final String upgradeVersionName = content.getString("upgrade_version_name");
			final String upgradeUrl = content.getString("upgrade_url");
			final long upgradeFileSize = content.getLong("upgrade_filesize");
			final int upgradeForce = content.getInt("upgrade_force");
			final EntityUpgrade entityUpgrade = new EntityUpgrade();
			entityUpgrade.setUpgradeVersionCode(upgradeVersionCode);
			entityUpgrade.setUpgradeVersionName(upgradeVersionName);
			entityUpgrade.setUpgradeUrl(upgradeUrl);
			entityUpgrade.setUpgradeFileSize(upgradeFileSize);
			entityUpgrade.setUpgradeForce(upgradeForce);
			entityUpgrade.setEntryTable(mEntityEntry.getEntryTable());
			return entityUpgrade;
		} catch (JSONException e) {
			return null;
		}
	}
}
