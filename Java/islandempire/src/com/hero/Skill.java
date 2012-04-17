package com.hero;

public class Skill {
	private String name;
	private String iconUrl;
	private Long level;
	private Long type;
	private String desc;
	private String iconName;

	public String getName() {
		return name;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public Long getLevel() {
		return level;
	}

	public Long getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public String getIconName() {
		return iconName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
}
