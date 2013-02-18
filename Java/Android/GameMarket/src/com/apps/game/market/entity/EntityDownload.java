package com.apps.game.market.entity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EntityDownload {
	private File file;
	private FileOutputStream outStream;
	private BufferedOutputStream bufferStream;
	private long position;
	private boolean append;
	private boolean finish;

	public File getFile() {
		return file;
	}

	public FileOutputStream getOutStream() {
		return outStream;
	}

	public BufferedOutputStream getBufferStream() {
		return bufferStream;
	}

	public long getPosition() {
		return position;
	}

	public boolean getAppend() {
		return append;
	}

	public boolean getFinish() {
		return finish;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setOutStream(FileOutputStream outStream) {
		this.outStream = outStream;
	}

	public void setBufferStream(BufferedOutputStream bufferStream) {
		this.bufferStream = bufferStream;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	
	public boolean write(byte[] buffer, int offset, int length) {
		try {
			bufferStream.write(buffer, offset, length);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void close() {
		if (bufferStream != null) {
			try {
				bufferStream.close();
			} catch (IOException e) {

			}
		}

		if (outStream != null) {
			try {
				outStream.close();
			} catch (IOException e) {

			}
		}
	}
}
