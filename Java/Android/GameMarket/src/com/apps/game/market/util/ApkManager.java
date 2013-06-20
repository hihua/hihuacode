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
			final Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			intent.setDataAndType(Uri.fromFile(new File(dir, filename)), "application/vnd.android.package-archive");
			context.startActivity(intent);			
		} catch (Exception e) {
			Log.e("installApk", e.toString());			
		}		
	}
	
	public static void installApk(Context context, String filepath) {
		try {
			final Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			intent.setDataAndType(Uri.fromFile(new File(filepath)), "application/vnd.android.package-archive");
			context.startActivity(intent);			
		} catch (Exception e) {
			Log.e("installApk", e.toString());			
		}		
	}
	
	public static void installApk(Context context, File file) {
		try {
			final Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			context.startActivity(intent);			
		} catch (Exception e) {
			Log.e("installApk", e.toString());			
		}		
	}
	
	public static void uninstallApk(Context context, String packageName) {
		try {
			final Uri uri = Uri.parse("package:" + packageName);
			final Intent intent = new Intent(Intent.ACTION_DELETE, uri);
			context.startActivity(intent);			
		} catch (Exception e) {
			Log.e("uninstallApk", e.toString());
		}
	}
	
	public static boolean runApp(Context context, String packageName) {
		try {
			final PackageManager packageManager = context.getPackageManager();
			final Intent intent = packageManager.getLaunchIntentForPackage(packageName);
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
			final List<EntityAppInfo> list = new ArrayList<EntityAppInfo>();
			final PackageManager packageManager = context.getPackageManager();
			final List<PackageInfo> packages = packageManager.getInstalledPackages(0);
			for (PackageInfo packinfo : packages) {				
				if ((packinfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && !packinfo.packageName.equals("com.apps.game.market")) {
					final EntityAppInfo appInfo = new EntityAppInfo();
					appInfo.setAppName(packinfo.applicationInfo.loadLabel(packageManager).toString());
					appInfo.setAppIcon(packinfo.applicationInfo.loadIcon(packageManager));
					appInfo.setPackageName(packinfo.packageName);					
					String versionName = packinfo.versionName;
					int p = versionName.indexOf(" ");
					if (p > -1)
						versionName = versionName.substring(0, p);
					
					appInfo.setVersionName(versionName);
					appInfo.setVersionCode(packinfo.versionCode);
					final String dir = packinfo.applicationInfo.publicSourceDir;
					final File file = new File(dir);
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
		final PackageManager packageManager = context.getPackageManager();
		
		try {
			final PackageInfo packinfo = packageManager.getPackageInfo(packageName, 0);
			if (packinfo != null) {
				final EntityAppInfo appInfo = new EntityAppInfo();
				appInfo.setAppName(packinfo.applicationInfo.loadLabel(packageManager).toString());
				appInfo.setAppIcon(packinfo.applicationInfo.loadIcon(packageManager));
				appInfo.setPackageName(packinfo.packageName);					
				String versionName = packinfo.versionName;
				int p = versionName.indexOf(" ");
				if (p > -1)
					versionName = versionName.substring(0, p);
				
				appInfo.setVersionName(versionName);
				appInfo.setVersionCode(packinfo.versionCode);
				final String dir = packinfo.applicationInfo.publicSourceDir;
				final File file = new File(dir);
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
