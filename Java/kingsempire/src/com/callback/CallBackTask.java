package com.callback;

import com.entity.Cities;

public interface CallBackTask {
	public void onCancel(String taskName);
	public void onCities(String userName, Cities cities);	
}
