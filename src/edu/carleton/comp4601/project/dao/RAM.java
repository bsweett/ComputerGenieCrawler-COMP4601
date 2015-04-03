package edu.carleton.comp4601.project.dao;

public class RAM {

	private String memorySize; 
	private String type;

	public RAM() {
		this.memorySize = "";
		this.type = "";
	}
	
	public String getMemorySize() {
		return memorySize;
	}
	public void setMemorySize(String memorySize) {
		this.memorySize = memorySize;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
