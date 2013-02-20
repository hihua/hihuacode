package com.apps.game.market.task;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.game.market.R;
import com.apps.game.market.activity.ActivityBase;
import com.apps.game.market.entity.app.EntityApp;
import com.apps.game.market.enums.EnumAppStatus;
import com.apps.game.market.enums.EnumDownloadStatus;
import com.apps.game.market.global.GlobalData;
import com.apps.game.market.global.GlobalObject;
import com.apps.game.market.request.RequestDownload;
import com.apps.game.market.request.callback.RequestCallBackDownload;
import com.apps.game.market.util.ApkManager;
import com.apps.game.market.util.FileManager;

public class TaskDownload implements RequestCallBackDownload {
	private final GlobalObject mGlobalObject = GlobalObject.globalObject;
	private final GlobalData mGlobalData = GlobalData.globalData;	
	private final int Max = 2;
	private ActivityBase mActivityBase = null;
	private final List<EntityApp> mList = new Vector<EntityApp>();
	private final DecimalFormat mFormat = new DecimalFormat("##0.00");
	private RequestDownload[] mRequestDownload = new RequestDownload[Max];
		
	public TaskDownload() {
		for (int i = 0;i < Max;i++)
			mRequestDownload[i] = new RequestDownload(this);
	}
		
	public void setActivity(ActivityBase activityBase) {
		mActivityBase = activityBase;
	}
	
	public void downloadApp(Context context, final EntityApp entityApp) {
		String filesize = context.getString(R.string.dialog_app_filesize);
		String download = context.getString(R.string.dialog_app_download);
		String confirm = context.getString(R.string.dialog_confirm);
		String cancel = context.getString(R.string.dialog_cancel);
		
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_download, null);
		TextView textView = (TextView) view.findViewById(R.id.dialog_download_app_name);
		textView.setText(entityApp.getName());
		textView = (TextView) view.findViewById(R.id.dialog_download_app_size);
		long size = entityApp.getSize();
		double d = (double)size / 1024d / 1024d;
		textView.setText(filesize + mFormat.format(d) + "M");
		AlertDialog.Builder builder = new Builder(context);			
		builder.setTitle(download);
		builder.setView(view);
		builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();						
				add(entityApp);
			}
		});

		builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
						
		builder.create().show();
	}
	
	public boolean installApp(Context context, EntityApp entityApp) {
		final FileManager fileManager = mGlobalObject.getFileManager();
		String packageName = entityApp.getPackageName();
		String filename = fileManager.getAppsPath() + "/" + packageName + ".apk";
		File file = new File(filename);
		if (file.exists() && file.isFile()) {
			ApkManager.installApk(context, file);
			return true;
		} else {
			String msg = context.getString(R.string.error_no_apkfile);
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show(); 
			entityApp.setStatus(EnumAppStatus.NOINSTALL);
			return false;
		}			
	}
	
	public void runApp(final Context context, final EntityApp entityApp) {
		final String packageName = entityApp.getPackageName();
		AlertDialog.Builder builder = new Builder(context);
		String name = entityApp.getName();
		String run = context.getString(R.string.dialog_app_run);
		String confirm = context.getString(R.string.dialog_confirm);
		String cancel = context.getString(R.string.dialog_cancel);
		builder.setMessage(run);
		builder.setTitle(name);
		builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();						
				ApkManager.runApp(context, packageName);
			}
		});

		builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
						
		builder.create().show();
	}
	
	public void downloadCancel(final Context context, final EntityApp entityApp) {
		AlertDialog.Builder builder = new Builder(context);
		String name = entityApp.getName();
		String downloadCancel = context.getString(R.string.dialog_app_download_cancel);
		String confirm = context.getString(R.string.dialog_confirm);
		String cancel = context.getString(R.string.dialog_cancel);
		builder.setMessage(downloadCancel);
		builder.setTitle(name);
		builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();						
				cancel(entityApp);
			}
		});

		builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
						
		builder.create().show();
	}
		
	private void add(EntityApp entityApp) {				
		for (RequestDownload requestDownload : mRequestDownload) {			
			boolean busy = requestDownload.getBusy();
			if (!busy) {
				requestDownload.request(entityApp);
				if (mActivityBase != null)
					mActivityBase.onAppStatus(entityApp);
				
				return;
			}
		}
		
		entityApp.setStatus(EnumAppStatus.WAITING);				
		mList.add(entityApp);
		if (mActivityBase != null)
			mActivityBase.onAppStatus(entityApp);
	}
	
	private void cancel(EntityApp entityApp) {
		if (mList.indexOf(entityApp) > -1)
			mList.remove(entityApp);
		else {
			for (RequestDownload requestDownload : mRequestDownload) {
				if (requestDownload.getEntityApp() != null && entityApp.equals(requestDownload.getEntityApp())) {
					requestDownload.cancel();
					return;
				}
			}
		}		
	}
		
	@Override
	public void onCallBackDownload(RequestDownload requestDownload, EnumDownloadStatus status, EntityApp entityApp) {
		requestDownload.setEntityApp(null);
		switch (status) {
			case FINISH:
				entityApp.setStatus(EnumAppStatus.INSTALL);
				break;
				
			case CANCEL:
				entityApp.setStatus(EnumAppStatus.NOINSTALL);
				break;
				
			case FAILED:
				entityApp.setStatus(EnumAppStatus.NOINSTALL);
				break;
	
			default:
				entityApp.setStatus(EnumAppStatus.NOINSTALL);
				break;
		}
		
		if (mActivityBase != null)
			mActivityBase.onAppStatus(entityApp);
		
		if (mList.size() > 0) {
			entityApp = mList.remove(0);
			requestDownload.request(entityApp);
		} else
			requestDownload.setBusy(false);
	}
}
