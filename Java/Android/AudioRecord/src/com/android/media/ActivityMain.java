package com.android.media;

import java.text.DecimalFormat;

import com.android.media.handle.HandlePlay;
import com.android.media.handle.HandleRecord;
import com.android.media.speex.Speex;
import com.android.media.thread.ThreadPlay;
import com.android.media.thread.ThreadRecord;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.app.Activity;

public class ActivityMain extends Activity implements OnClickListener, OnTouchListener, OnLongClickListener, OnSeekBarChangeListener, Callback, HandleRecord, HandlePlay {
		
	private ProgressBar mProgressPlay;
	private ProgressBar mProgressRecord;
	private TextView mTextRecordTimer;
	private TextView mTextRecordSize;
	private TextView mTextRecord;
	private TextView mTextPlay;
	private TextView mTextQuality;
	private SeekBar mSeekBar;
	private Handler mHandler;
	private ThreadRecord mThreadRecord;
	private ThreadPlay mThreadPlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		mHandler = new Handler(this);
		mThreadRecord = new ThreadRecord(this);
		mThreadPlay = new ThreadPlay(this);
		mThreadRecord.start();		
		mThreadPlay.start();
		setView();
	}
			
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mThreadRecord.setHandle(null);
		mThreadRecord.setStop(true);
		mThreadRecord.setExit(true);
		mThreadPlay.setHandle(null);
		mThreadPlay.setStop(true);
		mThreadPlay.setExit(true);
	}
	
	private void setView() {		
		mProgressPlay = (ProgressBar) findViewById(R.id.pb_play);
		mProgressRecord = (ProgressBar) findViewById(R.id.pb_record);
		mTextRecordTimer = (TextView) findViewById(R.id.tv_record_timer);
		mTextRecordSize = (TextView) findViewById(R.id.tv_record_size);	
		mTextRecord = (TextView) findViewById(R.id.tv_record);	
		mTextPlay = (TextView) findViewById(R.id.tv_play);
		mTextQuality = (TextView) findViewById(R.id.tv_quality);
		mSeekBar = (SeekBar) findViewById(R.id.sb_quality);
		
		mProgressPlay.setOnClickListener(this);
		mTextPlay.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(this);
		
		mProgressRecord.setOnTouchListener(this);
		mProgressRecord.setOnLongClickListener(this);		
		mTextRecord.setOnTouchListener(this);
		mTextRecord.setOnLongClickListener(this);
		
		int quality = mThreadRecord.getQuality();
		String s = getString(R.string.quality, quality);
		mTextQuality.setText(s);
		mSeekBar.setProgress(quality);
	}
	
	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.pb_play:
			case R.id.tv_play: {
				if (mThreadPlay.getStop()) {
					if (mThreadRecord.getStop() && !mThreadRecord.getExit()) {
						Speex speex = mThreadRecord.getSpeex();						
						mThreadPlay.play(speex);
						mProgressPlay.setProgress(0);
						mTextPlay.setText(R.string.audio_stop);
					}
				} else {
					mThreadPlay.setStop(true);
					mTextPlay.setText(R.string.audio_play);
				}
			}
			break;
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		int id = view.getId();
		int action = event.getAction();
		
		if (id == R.id.pb_record || id == R.id.tv_record) {
			switch (action) {
				case MotionEvent.ACTION_DOWN: {
					
				}
				break;
				
				case MotionEvent.ACTION_UP: 
				case MotionEvent.ACTION_CANCEL: {
					if (!mThreadRecord.getStop())
						mThreadRecord.setStop(true);
				}
				break;
			}
		}
						
		return false;
	}
	
	@Override
	public boolean onLongClick(View view) {
		if (mThreadRecord.getStop()) {
			int progress = mSeekBar.getProgress();
			mThreadRecord.setQuality(progress);
			mProgressRecord.setProgress(0);
			mTextRecordTimer.setText("");
			mTextRecordSize.setText("");
			mThreadRecord.setStop(false);
		}
			
		return true;
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		String s = getString(R.string.quality, progress);
		mTextQuality.setText(s);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int progress = seekBar.getProgress();
		mThreadRecord.setQuality(progress);	
		String s = getString(R.string.quality, progress);
		mTextQuality.setText(s);
	}

	@Override
	public void onTimerSize(long timer, int size) {		
		Bundle bundle = new Bundle();    
		bundle.putLong("timer", timer);
		bundle.putInt("size", size);
		Message message = mHandler.obtainMessage();
		message.what = 0;
		message.setData(bundle);		
		mHandler.sendMessage(message);		
	}
	
	@Override
	public void onPosition(int position, int total) {
		Bundle bundle = new Bundle();    
		bundle.putInt("position", position);
		bundle.putInt("total", total);
		Message message = mHandler.obtainMessage();
		message.what = 1;
		message.setData(bundle);		
		mHandler.sendMessage(message);
	}
	
	@Override
	public void onPlayStop() {
		Message message = mHandler.obtainMessage();
		message.what = 2;
		mHandler.sendMessage(message);
	}

	@Override
	public boolean handleMessage(Message msg) {
		int what = msg.what;
		
		switch (what) {
			case 0: {
				Bundle bundle = msg.getData();
				long timer = bundle.getLong("timer");
				int size = bundle.getInt("size");
								
				double t = (double) timer / 1000.0d;
				double s = (double) size / 1000.0d;
				final double second = 60.0d;		
				
				if (t >= second && !mThreadRecord.getStop())
					mThreadRecord.setStop(true);
						
				int max = mProgressRecord.getMax();		
				int p = (int) (t / second * (double) max);
				mProgressRecord.setProgress(p);
				
				DecimalFormat df = new DecimalFormat("0.000");
				String str = getString(R.string.second, df.format(t));
				mTextRecordTimer.setText(str);
				
				df = new DecimalFormat("0.0");
				mTextRecordSize.setText(df.format(s) + "K");
			}
			break;
		
			case 1: {
				Bundle bundle = msg.getData();
				int position = bundle.getInt("position");
				int total = bundle.getInt("total");
				
				double t = (double) position / (double) total;
				int max = mProgressPlay.getMax();
				int p = (int) (t * (double) max);
				mProgressPlay.setProgress(p);
			}
			break;
			
			case 2: {
				mTextPlay.setText(R.string.audio_play);
			}
			break;
		}	
		
		return false;
	}		
}
