package com.gamemarket.servlet.web;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.omg.CORBA_2_3.portable.OutputStream;

import com.gamemarket.servlet.ServletBase;
import com.gamemarket.util.Logs;
import com.gamemarket.util.Numeric;

public abstract class WebBase extends ServletBase {
	protected final Logger Log = Logs.getDefault();	
				
	@Override
	protected void writeLog(final Priority priority, final Object message, final Throwable t) {
		Log.log(priority, message, t);
	}
	
	@Override
	protected void writeLog(final Priority priority, final Object message) {
		Log.log(priority, message);
	}
					
	protected Map<String, Object> getAdmin(final Map<String, Object> content, final HttpServletRequest request) {
		final HttpSession session = request.getSession();
		if (session == null) {
			setCode(content, -1);
			return null;
		}
		
		final Object object = session.getAttribute("admin");
		if (object == null) {
			setCode(content, -1);
			return null;
		}
		
		return (Map<String, Object>) object;		
	}
	
	protected int getAdminId(final Map<String, Object> admin) {
		if (admin == null)
			return -1;
		
		final Object object = admin.get("admin_id");
		return Numeric.toInteger(object);
	}
	
	protected int getAdminParent(final Map<String, Object> admin) {
		if (admin == null)
			return -1;
		
		final Object object = admin.get("admin_parent");
		return Numeric.toInteger(object);
	}
	
	protected String getAdminUsername(final Map<String, Object> admin) {
		if (admin == null)
			return "";
		
		final Object object = admin.get("admin_username");
		return String.valueOf(object);
	}
	
	protected boolean isAdmin(final Map<String, Object> admin) {
		if (admin == null)
			return false;
		
		final Object object = admin.get("admin_parent");
		if (Numeric.toInteger(object) == 0)
			return true;
		else
			return false;
	}
	
	protected boolean isManager(final Map<String, Object> admin) {
		if (admin == null)
			return false;
		
		final Object object = admin.get("admin_parent");
		if (Numeric.toInteger(object) == 1)
			return true;
		else
			return false;
	}
	
	protected boolean isUser(final Map<String, Object> admin) {
		if (admin == null)
			return false;
		
		final Object object = admin.get("admin_parent");
		final int v = Numeric.toInteger(object);
		if (v != 0 && v != 1)
			return true;
		else
			return false;
	}
		
	protected boolean checkAuthority(final Map<String, Object> admin, final Map<String, Object> map) {
		Object object = admin.get("admin_parent");
		final int adminParent = Numeric.toInteger(object);
		if (adminParent == 0)
			return true;
		
		object = admin.get("admin_id");
		final int adminId = Numeric.toInteger(object);
						
		object = map.get("admin_id");
		final int mapId = Numeric.toInteger(object);		
		if (adminId == mapId)
			return true;
		
		object = map.get("admin_parent");
		final int mapParent = Numeric.toInteger(object);
		if (adminId == mapParent)
			return true;
		
		return false;
	}
	
	protected void closeStream(final BufferedOutputStream out) {
		if (out == null)
			return;
		
		try {
			out.close();
		} catch (IOException e) {
			
		}
	}
	
	protected void closeStream(final FileOutputStream out) {
		if (out == null)
			return;
		
		try {
			out.close();
		} catch (IOException e) {
			
		}
	}
	
	protected void closeStream(final OutputStream out) {
		if (out == null)
			return;
		
		try {
			out.close();
		} catch (IOException e) {
			
		}
	}
	
	protected void closeStream(final InputStream in) {
		if (in == null)
			return;
		
		try {
			in.close();
		} catch (IOException e) {
			
		}
	}
	
	protected boolean upload(final HttpServletRequest request, final Map<String, String> param, String dir, final String filename) {
		boolean success = false;
		boolean multipart = ServletFileUpload.isMultipartContent(request);
		if (multipart) {
			final FileItemFactory factory = new DiskFileItemFactory();
			final ServletFileUpload upload = new ServletFileUpload(factory);            			
			List<FileItem> list = null;
			
			try {
				list = upload.parseRequest(request);
			} catch (FileUploadException e) {
				writeLog(Level.ERROR, e.getMessage(), e);
			}
			
			if (!dir.endsWith("/") && !dir.endsWith("\\"))
				dir += "/";
			
			if (list != null) {				
				for (final FileItem fileItem : list) {
					final String name = fileItem.getName();
					final String field = fileItem.getFieldName();
					final long size = fileItem.getSize();
					InputStream in = null;
					
					try {
						in = fileItem.getInputStream();						
					} catch (IOException e) {
						fileItem.delete();
						writeLog(Level.ERROR, e.getMessage(), e);
						return false;
					}
															
					if (checkString(name) && size > 0) {
						if (!name.endsWith(".apk")) {
							closeStream(in);
							fileItem.delete();
							return false;
						}
						
						if (!writeFileStream(in, dir, filename)) {
							closeStream(in);
							fileItem.delete();
							return false;
						} else
							success = true;
					} else {
						final String s = getFieldValue(in);
						if (param.containsKey(field))
							param.put(field, s);
					}
					
					closeStream(in);
					fileItem.delete();
				}				
			}
		}
		
		return success;
	}
	
	private String getFieldValue(final InputStream in) {
		if (in == null)
			return null;
		
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		int i = -1;
		
		try {
			while ((i = in.read()) != -1)
				out.write(i);
			
			final String s = out.toString();
			out.close();
			return s;
		} catch (IOException e) {
			writeLog(Level.ERROR, e.getMessage(), e);
			return null;
		}		
	}
	
	private boolean writeFileStream(final InputStream in, final String dir, final String filename) {
		if (in == null)
			return false;
		
		File file = new File(dir);
		if (!file.exists() && !file.mkdirs())
			return false;
												
		final String filepath = dir + filename;
		file = new File(filepath);
		if (file.exists() && !file.delete())
			return false;
		
		FileOutputStream out = null;
		BufferedOutputStream buf = null;
							
		try {
			out = new FileOutputStream(file);
			buf = new BufferedOutputStream(out);
		} catch (FileNotFoundException e) {
			writeLog(Level.ERROR, e.getMessage(), e);
			return false;
		}		
						
		final byte buffer[] = new byte[8192];
		int len = 0;
		
		try {
			while (true) {
				len = in.read(buffer);
				if (len > 0)
					buf.write(buffer, 0, len);
				else
					break;
			}
			
			closeStream(buf);
			closeStream(out);
			closeStream(in);
			return true;
		} catch (IOException e) {
			closeStream(buf);
			closeStream(out);
			closeStream(in);
			if (file.exists())
				file.delete();
			
			writeLog(Level.ERROR, e.getMessage(), e);
			return false;
		}
	}	
}
