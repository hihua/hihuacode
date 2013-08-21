package com.apps.game.market.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

public class DeviceInfo {
	private String deviceId;
	private String brand;
	private String model;
	private String sdk;
	private String mobileType;
	
	public DeviceInfo(Context context) {
		init(context);
	}

	private void init(Context context) {
		final TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		setDeviceId(manager.getDeviceId());
		setBrand(Build.MANUFACTURER);
		setModel(Build.MODEL);
		setSdk(Build.VERSION.SDK);
		setMobileType(manager.getSimOperator());		
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public String getSdk() {
		return sdk;
	}
	
	public String getMobileType() {
		return mobileType;
	}
	
	public String getNet(Context context) {
		final ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo network = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (network != null && network.isAvailable())
			return "1";
		else
			return "0";
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setSdk(String sdk) {
		this.sdk = sdk;
	}

	public void setMobileType(String mobileType) {
		if (mobileType != null) {
			if (mobileType.equals("46000") || mobileType.equals("46002")) {
				this.mobileType = "1";
				return;
			}
			
			if (mobileType.equals("46001")) {
				this.mobileType = "2";
				return;
			}
			
			if (mobileType.equals("46003")) {
				this.mobileType = "3";
				return;
			}
		}
		
		this.mobileType = "0";
	}
}
