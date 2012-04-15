package com.games.tk5;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Main extends Activity implements ViewCallBack {
	
	private IndexView m_IndexView = null;
	private ViewBase m_TopView = null;
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        changeIndex(false);
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();		
		if (m_TopView != null)
			m_TopView.setDestroy();			
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (m_TopView != null && m_TopView == m_IndexView) {
			endGames();
			return true;
		}
		
		if (m_TopView != null && m_TopView.setKeyDown(keyCode, event))
			return true;
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		super.onPause();		
		if (m_TopView != null)
			m_TopView.setPause();			
	}

	@Override
	protected void onRestart() {		
		super.onRestart();
		if (m_TopView != null)
			m_TopView.setRestart();
	}
	
	private void setDestroy() {
		if (m_TopView != null) {
			m_TopView.setDestroy();
			m_TopView = null;
		}
	}

	@Override
	public void changeIndex(boolean lastView) {
		setDestroy();
		if (m_IndexView == null) {
			m_IndexView = new IndexView(this, this);			
		} else
			m_IndexView.setLastView(lastView);
		
		if (m_IndexView.init()) {
			m_TopView = m_IndexView;
			setContentView(m_IndexView);
		}
	}

	@Override
	public void changeGames(ViewBase view) {
		setDestroy();
		if (view.init()) {
			m_TopView = view;
			setContentView(view);
		}
	}

	@Override
	public void endGames() {
		finish();
	}	
}