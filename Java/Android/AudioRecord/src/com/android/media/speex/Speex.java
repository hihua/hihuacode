package com.android.media.speex;

import java.util.List;
import java.util.Vector;

public class Speex {
	
	private final List<byte[]> mBuffers = new Vector<byte[]>();
	private final int mSize = 8192;
	private byte[] mBuffer;
	private int mOffset = 0;
	private int mLength = 0;
	private int mTotal = 0;
	private int mQuality = 4;
	
	public List<byte[]> getBuffers() {
		return mBuffers;
	}
	
	public void clear() {
		mBuffer = null;
		mBuffers.clear();
		mTotal = 0;
		mOffset = 0;		
	}
	
	public int getTotal() {
		return mTotal;
	}
	
	public void setQuality(int quality) {
		mQuality = quality;
	}
	
	public int getQuality() {
		return mQuality;
	}
			
	public void encode(byte[] in, int frameSize) {				
		encodeSpeex(in);
		byte[] buffer = new byte[frameSize];
		int len = encodeSpeexs(buffer, 0, frameSize);		
		int offset = 0;
		int count = len;
		mTotal += len;
		
		while (count > 0) {
			if (mBuffer == null) {
				mBuffer = new byte[mSize];
				mOffset = 0;
				mLength = mBuffer.length;
			}
			
			int size = count;			
			if (size > mLength)		
				size = mLength;
						
			System.arraycopy(buffer, offset, mBuffer, mOffset, size);
			offset += size;
			mOffset += size;			
			mLength -= size;
			count -= size;
						
			if (mLength == 0) {
				mBuffers.add(mBuffer);
				mBuffer = null;
			}			
		}				
	}
	
	public void encodeFinish() {
		if (mOffset > 0) {
			byte[] buffer = new byte[mOffset];
			System.arraycopy(mBuffer, 0, buffer, 0, mOffset);
			mBuffers.add(buffer);			
		}		
	}
				
	static {		
		System.loadLibrary("speex");
        System.loadLibrary("audiorecord");  
    }
	
	public native void initEncode(int compression);
	public native void initDecode();
	public native void releaseEncode();
	public native void releaseDecode();
	public native int getEncodeFrameSize();
	public native int getDecodeFrameSize();
	public native int encodeSpeex(byte[] in);
	private native int encodeSpeexs(byte[] out, int out_offset, int out_size);
	public native void decodeSpeex(byte[] in, int in_offset, int in_size);
	public native int decodeSpeexs(byte[] out, int out_offset);
	public native int decodeRemaining();
}
