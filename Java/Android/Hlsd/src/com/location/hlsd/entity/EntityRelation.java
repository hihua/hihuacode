package com.location.hlsd.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;

public class EntityRelation {
	private boolean status;
	private String from;
	private String to;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(final boolean status) {
		this.status = status;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(final String to) {
		this.to = to;
	}

	public static EntityRelation getRelation() {
		final File root = Environment.getExternalStorageDirectory();
		if (root == null)
			return null;
		
		final String apppath = root.getPath() + "/hls";
		final String config = apppath + "/config.txt";
		final String content = readFile(config, "UTF-8");
		if (content != null)
			return parseRelation(content);
		else
			return null;
	}
	
	private static void closeStream(final InputStream in) {
		if (in == null)
			return;
		
		try {
			in.close();
		} catch (final IOException e) {
			
		}
	}
	
	private static void closeStream(final OutputStream out) {
		if (out == null)
			return;
		
		try {
			out.close();
		} catch (final IOException e) {
			
		}
	}

	private static String readFile(final String path, final String charset) {
		final File file = new File(path);		
		if (!file.exists() || !file.isFile())
			return null;
		
		final byte[] b = new byte[(int) file.length()];		
		String content = null;
		InputStream in = null;		

		try {
			in = new FileInputStream(file);			
			in.read(b);
			content = new String(b, charset);			
		} catch (final IOException e) {
			
		} finally {
			closeStream(in);
		}
		
		return content;
	}
	
	private static boolean writeFile(final String path, final String charset, final String content) {
		byte[] b = null;
		
		try {
			b = content.getBytes(charset);
		} catch (final UnsupportedEncodingException e) {
			return false;
		}
		
		if (b == null)
			return false;
		
		boolean success = false;
		OutputStream out = null;
		
		try {
			out = new FileOutputStream(path, false);
			out.write(b);			
			success = true;
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		} finally {
			closeStream(out);
		}
		
		return success;
	}

	private static EntityRelation parseRelation(final String content) {
		try {
			final JSONObject json = new JSONObject(content);
			final boolean status = json.getBoolean("status");
			final String from = json.getString("from");
			final String to = json.getString("to");
			final EntityRelation entityRelation = new EntityRelation();
			entityRelation.setStatus(status);
			entityRelation.setFrom(from);
			entityRelation.setTo(to);
			return entityRelation;
		} catch (final JSONException e) {
			return null;
		}
	}
	
	public static boolean writeRelation(final EntityRelation entityRelation) {
		if (entityRelation == null)
			return false;
		
		final File root = Environment.getExternalStorageDirectory();
		if (root == null)
			return false;
		
		final String apppath = root.getPath() + "/hls";
		final String config = apppath + "/config.txt";
		
		final boolean status = entityRelation.getStatus();
		final String from = entityRelation.getFrom();
		final String to = entityRelation.getTo();
		
		final Map<String, Object> relations = new HashMap<String, Object>();
		relations.put("status", status);
		relations.put("from", from);
		relations.put("to", to);
		
		final JSONObject json = new JSONObject(relations);
		final String content = json.toString();
		return writeFile(config, "UTF-8", content);		
	}
}
