package com.games.tk5;

import java.io.IOException;

import com.games.tk5.util.Logs;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VideoView extends SurfaceView implements OnBufferingUpdateListener, OnCompletionListener, MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {
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
	
	public boolean start(int res) {
		m_MediaPlayer = MediaPlayer.create(getContext(), res);
		m_MediaPlayer.setDisplay(m_SurfaceHolder);
		
		try {
			m_MediaPlayer.prepare();
		} catch (IllegalStateException e) {			
			Logs.LogsError(e);
			return false;
		} catch (IOException e) {			
			Logs.LogsError(e);
			return false;
		}
		
		m_MediaPlayer.setOnBufferingUpdateListener(this); 
		m_MediaPlayer.setOnPreparedListener(this); 
		m_MediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		return true;
	}
	
	public void stop() {
		if (m_MediaPlayer != null) {
			m_MediaPlayer.stop();
			m_MediaPlayer.release();
		}
	}
	
	public void pause() {
		if (m_MediaPlayer != null && m_MediaPlayer.isPlaying())
			m_MediaPlayer.pause();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		if (mediaPlayer.getVideoWidth() > 0 && mediaPlayer.getVideoHeight() > 0) {
			m_SurfaceHolder.setFixedSize(mediaPlayer.getVideoWidth(), mediaPlayer.getVideoHeight()); 
			mediaPlayer.start();
		} else
			m_ViewCallBack.changeIndex(false);
	}

	@Override
	public void onCompletion(MediaPlayer mediaPlayer) {
		stop();
		m_ViewCallBack.changeIndex(false);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
		// TODO Auto-generated method stub
		
	}		
}
