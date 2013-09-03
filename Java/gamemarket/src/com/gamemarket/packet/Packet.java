package com.gamemarket.packet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.gamemarket.util.Logs;

public class Packet {
	protected final Logger Log = Logs.getDefault();
	private final String mCharset = "UTF-8";
	
	public Packet() {
		
	}
	
	private void closeStream(InputStream in) {
		if (in == null)
			return;
		
		try {
			in.close();
		} catch (IOException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
		}
	}
	
	private void closeStream(FileInputStream in) {
		if (in == null)
			return;
		
		try {
			in.close();
		} catch (IOException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
		}
	}
	
	private void closeStream(FileOutputStream out) {
		if (out == null)
			return;
		
		try {
			out.close();
		} catch (IOException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
		}
	}
	
	private void closeReader(InputStreamReader reader) {
		if (reader == null)
			return;
		
		try {
			reader.close();
		} catch (IOException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
		}
	}
	
	private void closeBuffered(BufferedReader buffered) {
		if (buffered == null)
			return;
		
		try {
			buffered.close();
		} catch (IOException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
		}
	}
	
	private void closeStream(BufferedInputStream in) {
		if (in == null)
			return;
		
		try {
			in.close();
		} catch (IOException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
		}
	}
	
	private void closeStream(BufferedOutputStream out) {
		if (out == null)
			return;
		
		try {
			out.close();
		} catch (IOException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
		}
	}
	
	private boolean writeXML(final Document document, final String filepath, final String charset) {
		final OutputFormat format = new OutputFormat("	", true, charset);
		format.setLineSeparator("\r\n");
		format.setTrimText(true);
		final StringWriter sw = new StringWriter();		
		final XMLWriter writer = new XMLWriter(sw, format);		
		String xml = null;
		
		try {
			writer.write(document);
			xml = sw.toString();
		} catch (IOException e) {
			Log.log(Level.ERROR, e.getMessage(), e);			
		} finally {
			try {
				sw.close();
				writer.close();
			} catch (IOException e) {
				Log.log(Level.ERROR, e.getMessage(), e);
			}
		}
		
		if (xml == null)
			return false;
		
		FileOutputStream out = null;
		
		try {
			out = new FileOutputStream(filepath, false);
		} catch (FileNotFoundException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}
				
		byte[] b = null;
		
		try {
			b = xml.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			closeStream(out);
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}
		
		try {
			out.write(b);
			closeStream(out);
			return true;
		} catch (IOException e) {
			closeStream(out);
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}
	}
	
	private Document getDocument(final String filepath) {
		final File file = new File(filepath);
		if (!file.exists())
			return null;
		
		FileInputStream in = null;
		
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
			return null;
		} 
						
		Document document = null;
				
		try {
			final SAXReader saxReader = new SAXReader();
			document = saxReader.read(file);
		} catch (DocumentException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
		} finally {
			closeStream(in);
		}
		
		return document;
	}
	
	public boolean deleteDirectory(final File dir) {
		if (!dir.exists())			
			return true;
				
		if (dir.isDirectory()) {			
			final File[] files = dir.listFiles();
			if (files != null) {
				for (final File file : files) {
					if (file.isFile() && !file.delete())
						return false;
										
					if (file.isDirectory() && !deleteDirectory(file))
						return false;
				}
			}					  
		}
		
		return dir.delete();		
	}
	
	public boolean copyFile(final File src, final File dest) {
		FileInputStream in = null;
		
		try {
			in = new FileInputStream(src);
		} catch (FileNotFoundException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}
		
		final BufferedInputStream inbuff = new BufferedInputStream(in);		
		FileOutputStream out = null;		
		
		try {
			out = new FileOutputStream(dest);
		} catch (FileNotFoundException e) {
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}
		
		final BufferedOutputStream outbuff = new BufferedOutputStream(out);		
		final byte[] b = new byte[8192];
				
		try {
			while (true) {
				final int len = inbuff.read(b);
				if (len > 0)
					outbuff.write(b, 0, len);
				else
					break;
			}
			
			closeStream(outbuff);
			closeStream(out);
			closeStream(inbuff);
			closeStream(in);
			return true;
		} catch (IOException e) {
			closeStream(outbuff);
			closeStream(out);
			closeStream(inbuff);
			closeStream(in);
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}
	}
	
	private boolean copyDirectiory(final String src, final String dest) {
		final File target = new File(dest);
		if (!target.mkdirs())
			return false;
		
		final File source = new File(src);		
		final File[] files = source.listFiles();
		if (files != null) {
			for (final File file : files) {				
				if (file.isFile()) {
					final String s = target.getAbsolutePath() + File.separator + file.getName();				
					final File f = new File(s);  
	                if (!copyFile(file, f))
	                	return false;
				}
				
				if (file.isDirectory()) {
	                final String from = src + File.separator + file.getName();
	                final String to = dest + File.separator + file.getName();
	                if (!copyDirectiory(from, to))
	                	return false;  
	            } 
			}
		}		
		
		return true;
	}
		
	public boolean setVersion(final String manifest, final int versionCode, final String versionName) {
		final Document document = getDocument(manifest);	
		if (document == null)
			return false;
						
		try {
			final Element root = document.getRootElement();
			Attribute attribute = root.attribute("versionCode");
			attribute.setText(String.valueOf(versionCode));
			
			attribute = root.attribute("versionName");
			attribute.setText(versionName);
		} catch (Exception e) {
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}
		
		return writeXML(document, manifest, mCharset);
	}
	
	public boolean setEntry(final String entry, final int entryTable) {
		final Document document = getDocument(entry);	
		if (document == null)
			return false;
		
		try {
			final Element root = document.getRootElement();
			final Element element = root.element("entry_table");
			element.setText(String.valueOf(entryTable));			
		} catch (Exception e) {
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}
		
		return writeXML(document, entry, mCharset);
	}
	
	public boolean execute(final String bat, final String outpath, final String outfile, final String srcpath) {
		InputStream in = null;
		InputStreamReader reader = null;
		BufferedReader buffered = null;		
		final String command = bat + " " + outpath + " " + outfile + " " + srcpath;
		
		try {
			final Process p = Runtime.getRuntime().exec(command);			
			in = p.getInputStream(); 
			reader = new InputStreamReader(in);
			buffered = new BufferedReader(reader);
			final StringBuilder content = new StringBuilder();
			String line = null;
						
			while ((line = buffered.readLine()) != null) {  
				content.append(line);
				content.append("\r\n");
			}
			
			closeBuffered(buffered);
			closeReader(reader);
			closeStream(in);			
			
			if (content.length() > 0)
				Log.log(Level.INFO, content.toString());
			
			return true;
		} catch (IOException e) {
			closeBuffered(buffered);
			closeReader(reader);
			closeStream(in);
			Log.log(Level.ERROR, e.getMessage(), e);
			return false;
		}  
	}
	
	public boolean newPacket(final String from, final String to) {
		final File dest = new File(to);		
		if (!deleteDirectory(dest))
			return false;
		
		if (!dest.mkdirs())
			return false;
		
		final File src = new File(from);
		final File[] files = src.listFiles();  
        for (final File file : files) {  
            if (file.isFile()) {               
            	final String s = dest.getAbsolutePath() + File.separator + file.getName();				
				final File f = new File(s);  
                if (!copyFile(file, f))
                	return false;
            }
            
            if (file.isDirectory()) {              
                final String source = from + File.separator + file.getName();  
                final String target = to + File.separator + file.getName();  
                if (!copyDirectiory(source, target))
                	return false;
            }  
        }
        
        return true;
	}
}
