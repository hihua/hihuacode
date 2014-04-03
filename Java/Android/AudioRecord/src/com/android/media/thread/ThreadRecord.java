package com.android.media.thread;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.android.media.handle.HandleRecord;
import com.android.media.speex.Speex;

public class ThreadRecord extends Thread {
	
	private boolean mExit = false;
	private boolean mStop = true;
	private final Object mSync = new Object();
	private HandleRecord mHandle;
	private final int mSampleRateInHz = 8000;
	private final int mChannelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private final int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;
	private final Speex mSpeex = new Speex();
	private int mQuality = 4;

	public ThreadRecord(HandleRecord handle) {
		mHandle = handle;
	}
	
	public void setHandle(HandleRecord handle) {
		mHandle = handle;
	}
		
	public boolean getExit() {
		synchronized (mSync) {
			return mExit;
		}		
	}

	public void setExit(boolean exit) {
		synchronized (mSync) {
			mExit = exit;
			mSync.notify();
		}		
	}

	public boolean getStop() {
		synchronized (mSync) {
			return mStop;
		}		
	}

	public void setStop(boolean stop) {
		synchronized (mSync) {
			mStop = stop;
			if (!stop)
				mSync.notify();
		}		
	}
	
	public Speex getSpeex() {
		return mSpeex;
	}
	
	public void setQuality(int quality) {
		mQuality = quality;
	}
	
	public int getQuality() {
		return mQuality;
	}

	@Override
	public void run() {				
		while (!getExit()) {
			synchronized (mSync) {
				try {
					mSync.wait();
				} catch (InterruptedException e) {
					
				}
			}
			
			int quality = getQuality();
			mSpeex.initEncode(quality);
			mSpeex.setQuality(quality);
			int frameSize = mSpeex.getEncodeFrameSize();
			frameSize *= 2;
			mSpeex.clear();
			boolean inited = false;
			AudioRecord audioRecord = null;
			byte[] buffer = new byte[512];
			int offset = 0;
			int length = buffer.length;
			byte[] in = new byte[frameSize];
			int in_size = 0, in_length = in.length;
			long start = System.currentTimeMillis();
			long end = start;
															
			//double dB = 10*Math.log10(v/(double)r);	
						
			while (!getStop()) {
				if (!inited) {				
					int bufferSize = AudioRecord.getMinBufferSize(mSampleRateInHz, mChannelConfig, mAudioFormat);
					audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, mSampleRateInHz, mChannelConfig, mAudioFormat, bufferSize);					
					audioRecord.startRecording();
					inited = true;					
				}
				
				int len = audioRecord.read(buffer, offset, length);
								
				while (len > 0) {										
					int size = frameSize;
					if (len < size)
						size = len;
					
					if (in_size + size > in_length)
						size = in_length - in_size;
					
					System.arraycopy(buffer, offset, in, in_size, size);
					in_size += size;
					
					if (in_size == in_length) {
						mSpeex.encode(in, frameSize);
						in_size = 0;
					}
								
					offset += size;
					len -= size;					
				}
																
				offset = 0;
				
				long current = System.currentTimeMillis();
				if (current - end > 100) {
					end = current;
					if (mHandle != null)
						mHandle.onTimerSize(end - start, mSpeex.getTotal());
				}
			}
						
			if (in_size > 0) {
				for (int i = in_size;i < in_length;i++)
					in[i] = 0;
				
				mSpeex.encode(in, frameSize);
			}
			
			mSpeex.encodeFinish();
															
			if (audioRecord != null) {
				audioRecord.stop();
				audioRecord.release();
			}
						
			mSpeex.releaseEncode();
			setStop(true);
		}	
	}
}
