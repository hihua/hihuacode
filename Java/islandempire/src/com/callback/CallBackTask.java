package com.callback;

import com.entity.Towns;
import com.task.TaskBase;

public interface CallBackTask {
	public void onCancel(String taskName, TaskBase taskBase);
	public void onTowns(Towns towns);
}
