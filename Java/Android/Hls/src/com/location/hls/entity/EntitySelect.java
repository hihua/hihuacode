package com.location.hls.entity;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class EntitySelect {
	private String name;
	private String color;	
	private List<String> icons;
		
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(final String color) {
		this.color = color;
	}

	public List<String> getIcons() {
		return icons;
	}

	public void setIcons(final List<String> icons) {
		this.icons = icons;
	}

	public static EntitySelect parseSelect(final InputStream in) {
		Document document = null;
		
		try {
			final SAXReader saxReader = new SAXReader();
			document = saxReader.read(in);
		} catch (final DocumentException e) {
			return null;
		}
		
		if (document == null)
			return null;
		
		final Element root = document.getRootElement();
		final Element nCode = root.element("code");
		if (nCode == null)
			return null;
		
		final String code = nCode.getText();
		if (code == null || !code.equals("0"))
			return null;
		
		final Element nName = root.element("name");
		if (nName == null)
			return null;
		
		final String name = nName.getText();
		if (name == null)
			return null;
		
		final Element nColor = root.element("color");
		if (nColor == null)
			return null;
		
		final String color = nColor.getText();
		if (color == null)
			return null;
		
		final Element nIcons = root.element("icons");
		if (nIcons != null) {
			final Iterator<Element> nIcon = nIcons.elementIterator("icon");
			if (nIcon != null) {
				final List<String> icons = new Vector<String>();
				while (nIcon.hasNext()) {
					final Element element = nIcon.next();
					if (element == null)
						continue;
					
					final String icon = element.getText();
					if (icon == null)
						continue;
					
					icons.add(icon);
				}
				
				if (icons.size() > 0) {
					final EntitySelect entitySelect = new EntitySelect();
					entitySelect.setName(name);
					entitySelect.setColor(color);
					entitySelect.setIcons(icons);
					return entitySelect;
				}
			}			
		}
		
		return null;
	}
	
	public static List<EntitySelect> parseSelects(final InputStream in) {
		Document document = null;
		
		try {
			final SAXReader saxReader = new SAXReader();
			document = saxReader.read(in);
		} catch (final DocumentException e) {
			return null;
		}
		
		if (document == null)
			return null;
		
		final Element root = document.getRootElement();				
		final Iterator<Element> nSelects = root.elementIterator("selects");
		if (nSelects != null) {			
			List<EntitySelect> entitySelects = new Vector<EntitySelect>();
			while (nSelects.hasNext()) {
				final Element nSelect = nSelects.next();
				if (nSelect == null)
					continue;
				
				final Element nName = nSelect.element("name");
				if (nName == null)
					return null;
				
				final String name = nName.getText();
				if (name == null)
					return null;
				
				final Element nColor = nSelect.element("color");
				if (nColor == null)
					return null;
				
				final String color = nColor.getText();
				if (color == null)
					return null;
				
				final Element nIcons = nSelect.element("icons");
				if (nIcons == null)
					return null;
				
				final Iterator<Element> nIcon = nIcons.elementIterator("icon");
				if (nIcon != null) {
					final List<String> icons = new Vector<String>();
					while (nIcon.hasNext()) {
						final Element element = nIcon.next();
						if (element == null)
							continue;
						
						final String icon = element.getText();
						if (icon == null)
							continue;
						
						icons.add(icon);
					}
					
					if (icons.size() > 0) {
						final EntitySelect entitySelect = new EntitySelect();
						entitySelect.setName(name);
						entitySelect.setColor(color);
						entitySelect.setIcons(icons);
						entitySelects.add(entitySelect);
					}
				}
			}
			
			if (entitySelects.size() > 0)
				return entitySelects;
		}
		
		return null;
	}
}
