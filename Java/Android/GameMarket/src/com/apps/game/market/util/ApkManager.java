package com.apps.game.market.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.apps.game.market.entity.EntityAppInfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.Log;

public class ApkManager {
	public static void installApk(Context context, String dir, String filename) {
		try {
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(new File(dir, filename)), "application/vnd.android.package-archive");
			context.startActivity(intent);			
		} catch (Exception e) {
			Log.e("installApk", e.toString());			
		}		
	}
	
	public static void installApk(Context context, String filepath) {
		try {
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(new File(filepath)), "application/vnd.android.package-archive");
			context.startActivity(intent);			
		} catch (Exception e) {
			Log.e("installApk", e.toString());			
		}		
	}
	
	public static void installApk(Context context, File file) {
		try {
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			context.startActivity(intent);			
		} catch (Exception e) {
			Log.e("installApk", e.toString());			
		}		
	}
	
	public static void uninstallApk(Context context, String packageName) {
		try {
			Uri uri = Uri.parse("package:" + packageName);
			Intent intent = new Intent(Intent.ACTION_DELETE, uri);
			context.startActivity(intent);			
		} catch (Exception e) {
			Log.e("uninstallApk", e.toString());
		}
	}
	
	public static boolean runApp(Context context, String packageName) {
		try {
			PackageManager packageManager = context.getPackageManager();
			Intent intent = packageManager.getLaunchIntentForPackage(packageName);
			if (intent != null) {
				context.startActivity(intent);
				return true;
			} else
				return false;
		} catch (Exception e) {
			Log.e("runApp", e.toString());
			return false;
		}		
	}
	
	public static List<EntityAppInfo> getApps(Context context) {
		try {
			List<EntityAppInfo> list = new ArrayList<EntityAppInfo>();
			PackageManager packageManager = context.getPackageManager();
			List<PackageInfo> packages = packageManager.getInstalledPackages(0);
			for (PackageInfo packinfo : packages) {				
				if ((packinfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && !packinfo.packageName.equals("com.apps.game.market")) {
					EntityAppInfo appInfo = new EntityAppInfo();
					appInfo.setAppName(packinfo.applicationInfo.loadLabel(packageManager).toString());
					appInfo.setAppIcon(packinfo.applicationInfo.loadIcon(packageManager));
					appInfo.setPackageName(packinfo.packageName);					
					String versionName = packinfo.versionName;
					int p = versionName.indexOf(" ");
					if (p > -1)
						versionName = versionName.substring(0, p);
					
					appInfo.setVersionName(versionName);
					appInfo.setVersionCode(packinfo.versionCode);
					String dir = packinfo.applicationInfo.publicSourceDir;
					File file = new File(dir);
					if (file.exists())						
						appInfo.setSize(file.length());
					
					list.add(appInfo);
				}				
			}
						
			if (list.size() > 0)
				return list;
			else
				return null;
		} catch (Exception e) {
			Log.e("getApps", e.toString());
			return null;
		}		
	}
	
	public static EntityAppInfo getApp(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		
		try {
			PackageInfo packinfo = packageManager.getPackageInfo(packageName, 0);
			if (packinfo != null) {
				EntityAppInfo appInfo = new EntityAppInfo();
				appInfo.setAppName(packinfo.applicationInfo.loadLabel(packageManager).toString());
				appInfo.setAppIcon(packinfo.applicationInfo.loadIcon(packageManager));
				appInfo.setPackageName(packinfo.packageName);					
				String versionName = packinfo.versionName;
				int p = versionName.indexOf(" ");
				if (p > -1)
					versionName = versionName.substring(0, p);
				
				appInfo.setVersionName(versionName);
				appInfo.setVersionCode(packinfo.versionCode);
				String dir = packinfo.applicationInfo.publicSourceDir;
				File file = new File(dir);
				if (file.exists())						
					appInfo.setSize(file.length());
				
				return appInfo;
			} else
				return null;
		} catch (NameNotFoundException e) {
			Log.e("getApp", e.toString());
			return null;
		}
	}
}
