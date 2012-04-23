package com.task;

import com.callback.CallBackTask;
import com.util.Logs;

public abstract class TaskBase extends Thread {
	protected final Logs Log = Logs.getInstance();
	private String m_TaskName = "";
	protected volatile boolean m_Cancel = false;
	private volatile long m_Delay = 0;
	protected CallBackTask m_CallBack;
	
	public TaskBase(String taskName, long delay, CallBackTask callBack) {
		m_TaskName = taskName;
		m_Delay = delay;
		m_CallBack = callBack;
	}
	
	public void run() {
		while (!getCancel()) {
			if (onCheck()) {
				onEntry();
			
				try {
					sleep(m_Delay);
				} catch (InterruptedException e) {
					
				}
			}
		}
		
		m_CallBack.onCancel(getTaskName(), this);
	}

	protected String getTaskName() {
		return m_TaskName;
	}
	
	public boolean getCancel() {
		return m_Cancel;
	}
	
	public void setCancel(boolean cancel) {
		m_Cancel = cancel;
	}
	
	public long getDelay() {
		return m_Delay;
	}
	
	public void setDelay(long delay) {
		m_Delay = delay;
	}
	
	protected abstract void onEntry();
	protected abstract boolean onCheck();
}
