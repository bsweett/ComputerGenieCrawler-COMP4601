package edu.carleton.comp4601.project.dao;

public class RAM {

	private Integer memorySize; 
	private String type;

	public RAM() {
		
	}
	
	public Integer getMemorySize() {
		return memorySize;
	}
	public void setMemorySize(Integer memorySize) {
		this.memorySize = memorySize;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
