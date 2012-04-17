package com.games.tk5;

import com.games.tk5.util.Logs;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Main extends Activity implements ViewCallBack, MediaCallBack, MediaPlayer.OnErrorListener {
	
	private IndexView m_IndexView = null;
	private ViewBase m_TopView = null;
	private MediaPlayer m_MediaPlayer = null;
	    
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
		mediaPause();
		if (m_TopView != null)
			m_TopView.setPause();			
	}

	@Override
	protected void onRestart() {		
		super.onRestart();
		mediaRestart();
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
	
	@Override
	public void mediaStart(int res) {
		try {
			m_MediaPlayer = MediaPlayer.create(this, res);			
			m_MediaPlayer.setVolume(10f, 10f);
			m_MediaPlayer.setLooping(true);
			m_MediaPlayer.start();
			m_MediaPlayer.setOnErrorListener(this);
		} catch (Exception e) {
			Logs.LogsError(e);
		}		
	}
	
	@Override
	public void mediaPause() {
		if (m_MediaPlayer != null && m_MediaPlayer.isPlaying())
			m_MediaPlayer.pause();
	}	
		
	@Override
	public void mediaRestart() {
		if (m_MediaPlayer != null)
			m_MediaPlayer.start();
	}
	
	@Override
	public void mediaStop() {
		if (m_MediaPlayer != null) {		
			m_MediaPlayer.stop();	
			m_MediaPlayer.release();
			m_MediaPlayer = null;
		}		
	}
	
	@Override
	public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
		mediaPlayer.release();
		return false;
	}
}