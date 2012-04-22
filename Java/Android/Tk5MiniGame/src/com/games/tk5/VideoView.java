package com.games.tk5;

import com.games.tk5.util.Logs;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VideoView extends SurfaceView implements OnCompletionListener, SurfaceHolder.Callback {
	private MediaPlayer m_MediaPlayer = null;
	private ViewCallBack m_ViewCallBack;
	private SurfaceHolder m_SurfaceHolder;
	
	public VideoView(Context context, ViewCallBack callback) {
		super(context);	
		m_ViewCallBack = callback;
		m_SurfaceHolder = getHolder();		
		m_SurfaceHolder.addCallback(this);		
		m_SurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	public boolean start() {
		try {
			m_MediaPlayer = MediaPlayer.create(getContext(), R.raw.game_welcome);
			m_MediaPlayer.setDisplay(m_SurfaceHolder);	
			m_MediaPlayer.setOnCompletionListener(this);
			m_MediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);			
			m_MediaPlayer.start();			
		} catch (IllegalStateException e) {
			Logs.LogsError(e);
			return false;
		}
		
		return true;
	}
	
	public void stop() {
		if (m_MediaPlayer != null) {
			m_MediaPlayer.stop();
			m_MediaPlayer.release();
			m_MediaPlayer = null;
		}
	}
	
	public void restart() {
		if (m_MediaPlayer != null)
			m_MediaPlayer.start();
	}
	
	public void pause() {
		if (m_MediaPlayer != null)
			m_MediaPlayer.pause();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCompletion(MediaPlayer mediaPlayer) {
		stop();
		m_ViewCallBack.onEndVideo();
	}	
}
