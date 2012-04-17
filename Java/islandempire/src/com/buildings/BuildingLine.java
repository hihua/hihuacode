package com.buildings;

public class BuildingLine {
	private Long level;
	private Long id;
	private String status;
	private Long currentOutput;
	
	public Long getLevel() {
		return level;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Long getCurrentOutput() {
		return currentOutput;
	}
	
	public void setLevel(Long level) {
		this.level = level;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setCurrentOutput(Long currentOutput) {
		this.currentOutput = currentOutput;
	}	
}
