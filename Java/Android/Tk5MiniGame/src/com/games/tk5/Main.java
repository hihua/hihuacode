package com.games.tk5;

import com.games.tk5.welcome.WelcomeView;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Main extends Activity implements ViewCallBack {
	
	private VideoView m_VideoView = null;
	private WelcomeView m_WelcomeView = null;
	private IndexView m_IndexView = null;
	private ViewBase m_TopView = null;	
		    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        m_VideoView = new VideoView(this, this);
        setContentView(m_VideoView);        
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (m_VideoView != null)
			m_VideoView.stop();			
		else {
			if (m_TopView != null)
				m_TopView.setDestroy();
		}				
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (m_VideoView != null) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
				m_VideoView.stop();
				endGames();
				return true;
			}			
		} else {
			if (m_TopView != null && (m_TopView == m_WelcomeView || m_TopView == m_IndexView)) {
				if (m_TopView.setKeyDown(keyCode, event))
					return true;
			} else {
				if (m_TopView != null && m_TopView.setKeyDown(keyCode, event))
					return true;
			}			
		}		
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (m_VideoView != null)
			m_VideoView.stop();			
		else {
			if (m_TopView != null)
				m_TopView.setPause();
		}					
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
	public void changeWelcome() {
		m_WelcomeView = new WelcomeView(this, this);
		if (m_WelcomeView.init()) {
			m_TopView = m_WelcomeView;
			setContentView(m_WelcomeView);
		}		
	}	

	@Override
	public void changeIndex(boolean lastView) {		
		if (m_IndexView == null) {
			m_WelcomeView = null;
			m_IndexView = new IndexView(this, this);			
		} else {
			setDestroy();
			m_IndexView.setLastView(lastView);
		}
		
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
	public void onEndVideo() {
		m_VideoView = null;
		changeWelcome();
	}

	@Override
	public void endGames() {
		finish();
	}
	
	
}