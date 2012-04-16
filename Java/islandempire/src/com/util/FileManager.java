package com.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileManager {
	
	private static void closeStream(FileOutputStream out) {
		if (out == null)
			return;
		
		try {
			out.close();
		} catch (IOException e) {
			
		}
	}
	
	private static void closeStream(OutputStreamWriter out) {
		if (out == null)
			return;
		
		try {
			out.close();
		} catch (IOException e) {
			
		}
	}
	
	public static boolean writeFile(String path, boolean append, String charsetName, String content) {
		FileOutputStream fileStream = null;
		
		try {
			fileStream = new FileOutputStream(path, append);
		} catch (FileNotFoundException e) {
			return false;
		}
		
		OutputStreamWriter outputStream = null;
		
		try {
			outputStream = new OutputStreamWriter(fileStream, charsetName);
		} catch (UnsupportedEncodingException e) {
			closeStream(fileStream);
			return false;
		}
		
		try {
			outputStream.write(content);
			closeStream(outputStream);
			closeStream(fileStream);
			return true;
		} catch (IOException e) {
			closeStream(outputStream);
			closeStream(fileStream);
			return false;
		}		
	}
}
