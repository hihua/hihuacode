package com.apps.game.market.entity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.apps.game.market.util.Numeric;

import android.content.Context;
import android.util.Log;

public class EntityEntry {
	private static final String mEntry = "entry.xml";
	private int entryTable = 0;
	private boolean entryOpen = false;

	public int getEntryTable() {
		return entryTable;
	}

	public void setEntryTable(int entryTable) {
		this.entryTable = entryTable;
	}

	public boolean getEntryOpen() {
		return entryOpen;
	}

	public void setEntryOpen(boolean entryOpen) {
		this.entryOpen = entryOpen;
	}
	
	public static EntityEntry diffEntry(EntityEntry entitySrcEntry, EntityEntry entityDestEntry) {
		if (entitySrcEntry == null && entityDestEntry == null)
			return null;
		
		if (entitySrcEntry == null && entityDestEntry != null)
			return entityDestEntry;
		
		if (entitySrcEntry != null && entityDestEntry == null)
			return entitySrcEntry;				
	
		if (entitySrcEntry.getEntryTable() != entityDestEntry.getEntryTable())			
			return entitySrcEntry;
		else
			return entityDestEntry;	
	}
	
	public static EntityEntry getSrcEntry(Context context) {
		InputStream in = null;
		
		try {
			in = context.getAssets().open(mEntry);
			if (in != null) {
				final EntityEntry entityEntry = readEntry(in);
				in.close();
				return entityEntry;
			} else
				return null;			
		} catch (IOException e) {
			return null;
		}
	}
	
	public static EntityEntry getDestEntry(Context context) {
		InputStream in = null;
		
		try {
			in = context.openFileInput(mEntry);
			if (in != null) {
				final EntityEntry entityEntry = readEntry(in);
				in.close();
				return entityEntry;
			} else
				return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static EntityEntry readEntry(InputStream in) {
		if (in == null)
			return null;
		
		Document document = null;
		
		try {
			final SAXReader saxReader = new SAXReader();
			document = saxReader.read(in);			
		} catch (DocumentException e) {			
			return null;
		}
		
		if (document == null)
			return null;
		
		try {
			final EntityEntry entityEntry = new EntityEntry();
			final Element root = document.getRootElement();
			Element element = root.element("entry_table");			
			if (element != null) {
				final String s = element.getText();
				if (Numeric.isNumber(s))
					entityEntry.setEntryTable(Integer.parseInt(s));			
			}
				
			element = root.element("entry_open");			
			if (element != null) {
				final String s = element.getText();
				if (s != null && s.equals("1"))
					entityEntry.setEntryOpen(true);
				else
					entityEntry.setEntryOpen(false);
			}
			
			return entityEntry;
		} catch (Exception e) {
			return null;
		}		
	}
	
	public static boolean writeEntry(Context context, EntityEntry entityEntry) {
		final Document document = DocumentHelper.createDocument();
		final Element root = document.addElement("entry");
		Element element = root.addElement("entry_table");
		final int entryTable = entityEntry.getEntryTable();
		if (entryTable > 0)
			element.setText(String.valueOf(entryTable));
		else
			element.setText("");
		
		element = root.addElement("entry_open");
		final boolean entryOpen = entityEntry.getEntryOpen();
		if (entryOpen)
			element.setText("1");
		else
			element.setText("0");
				
		FileOutputStream out = null;
		
		try {
			out = context.openFileOutput(mEntry, Context.MODE_PRIVATE);
			if (out != null) {
				final OutputFormat format = new OutputFormat("", true, "UTF-8");
				final XMLWriter write = new XMLWriter(out, format);
				write.write(document);
				out.close();
				return true;
			} else
				return false;
		} catch (FileNotFoundException e) {
			return false;
		} catch (UnsupportedEncodingException e) {
			return false;
		} catch (IOException e) {
			Log.e("a", e.toString(), e);
			return false;
		}		
	}
}
