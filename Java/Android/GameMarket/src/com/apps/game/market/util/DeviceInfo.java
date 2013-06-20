package com.apps.game.market.util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class DeviceInfo {
	private String deviceId;
	private String brand;
	private String model;
	private String sdk;
	
	public DeviceInfo(Context context) {
		init(context);
	}
	
	private void init(Context context) {
		final TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		setDeviceId(manager.getDeviceId());
		setBrand(Build.MANUFACTURER);
		setModel(Build.MODEL);
		setSdk(Build.VERSION.SDK);
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
}
