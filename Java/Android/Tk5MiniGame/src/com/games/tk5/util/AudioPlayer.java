package com.games.tk5.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;

public class AudioPlayer {
	private static MediaPlayer MusicPlayer = null;
				
	public static void musicStart(Context context, int res) {
		try {
			MusicPlayer = MediaPlayer.create(context, res);			
			MusicPlayer.setVolume(10f, 10f);
			MusicPlayer.setLooping(true);
			MusicPlayer.start();
			MusicPlayer.setOnErrorListener(new CallBack());
		} catch (Exception e) {
			Logs.LogsError(e);
		}		
	}
	
	public static void musicPause() {
		if (MusicPlayer != null)
			MusicPlayer.pause();
	}
	
	public static void musicRestart() {
		if (MusicPlayer != null)
			MusicPlayer.start();
	}
	
	public static void musicStop() {
		if (MusicPlayer != null) {		
			MusicPlayer.stop();	
			MusicPlayer.release();
			MusicPlayer = null;
		}		
	}		
}

class CallBack implements OnErrorListener {
	@Override
	public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
		mediaPlayer.release();
		return false;
	}
}
