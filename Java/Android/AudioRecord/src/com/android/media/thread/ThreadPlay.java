package com.android.media.thread;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;
import android.util.SparseIntArray;

import com.android.media.handle.HandlePlay;
import com.android.media.speex.Speex;

public class ThreadPlay extends Thread {
	
	private boolean mExit = false;
	private boolean mStop = true;
	private final Object mSync = new Object();
	private HandlePlay mHandle;
	private final int mSampleRateInHz = 8000;
	private final int mChannelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private final int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;
	private Speex mSpeex;
	private SparseIntArray mTable = new SparseIntArray();
	
	public ThreadPlay(HandlePlay handle) {
		mHandle = handle;
		initTable();
	}
	
	public void initTable() {
		mTable.put(0, 5);
		mTable.put(1, 9);
		mTable.put(2, 14);
		mTable.put(3, 20);
		mTable.put(4, 20);
		mTable.put(5, 27);
		mTable.put(6, 27);
		mTable.put(7, 37);
		mTable.put(8, 37);
		mTable.put(9, 45);
		mTable.put(10, 61);
	}
	
	public void setHandle(HandlePlay handle) {
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
	
	public void play(Speex speex) {
		mSpeex = speex;
		if (speex != null)
			setStop(false);
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
			
			if (!getStop()) {
				mSpeex.initDecode();
				int quality = mSpeex.getQuality();
				int total = mSpeex.getTotal();
				int position = 0;
				List<byte[]> buffers = new Vector<byte[]>();
				List<byte[]> buffer = mSpeex.getBuffers();
				buffers.addAll(buffer);			
				int frameSize = mSpeex.getDecodeFrameSize();			
				frameSize *= 2;
				int part = 4, size = 0;
				int in_offset = 0, in_size = 0;
				boolean inited = false;
				AudioTrack audioTrack = null;
				byte[] in = null;
				byte[] out = new byte[frameSize * part]; 
				int out_offset = 0, out_size = out.length;
																							
				while (!getStop()) {
					if (!inited) {
						android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
						int bufferSize = AudioTrack.getMinBufferSize(mSampleRateInHz, mChannelConfig, mAudioFormat);
						audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mSampleRateInHz, mChannelConfig, mAudioFormat, bufferSize, AudioTrack.MODE_STREAM);
						audioTrack.play();
						inited = true;
					}
					
					if (in_size == 0) {
						if (buffers.size() > 0) {
							in = buffers.remove(0);
							in_offset = 0;
							in_size = in.length;
						} else
							break;										
					}
					
					if (size == 0) {			
						size = mTable.get(quality, 0);
						if (size == 0)
							break;
					}
					
					if (size > in_size) {
						byte[] temp = new byte[size];
						System.arraycopy(in, in_offset, temp, 0, in_size);
						position += in_size;
						int offset = in_size;
						int left = size - in_size;
						
						while (buffers.size() > 0 && left > 0) {
							in = buffers.remove(0);
							in_offset = 0;
							in_size = in.length;
							if (left > in_size) {
								System.arraycopy(in, in_offset, temp, offset, in_size);
								position += in_size;
								offset += in_size;							
								left -= in_size;
								in_offset = 0;
								in_size = 0;
							} else {
								System.arraycopy(in, in_offset, temp, offset, left);
								position += left;
								in_offset += left;
								in_size -= left;
								break;
							}
						}							
						
						mSpeex.decodeSpeex(temp, 0, size);
					} else {
						mSpeex.decodeSpeex(in, in_offset, size);
						position += size;
						in_offset += size;
						in_size -= size;
					}													
									
					if (mHandle != null)
						mHandle.onPosition(position, total);
													
					if (out_offset + frameSize > out_size)
						out_offset = 0;
							
					int ret = mSpeex.decodeSpeexs(out, out_offset);
					
					audioTrack.write(out, out_offset, frameSize);
					out_offset += frameSize;
					
					Log.i("play", ret + "," + out_offset + "," + in_offset + "," + in_size + "," + size);
				}
											
				if (audioTrack != null) {
					if (audioTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED)
						audioTrack.stop();
					
					audioTrack.release();
				}
							
				mSpeex.releaseDecode();
				setStop(true);			
				if (mHandle != null)
					mHandle.onPlayStop();
			}		
		}
	}
}
