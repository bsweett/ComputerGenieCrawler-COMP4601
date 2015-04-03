package edu.carleton.comp4601.project.dao;

public class Processor {

	private String processorType;
	private String numberOfCores;
	private String Speed;
	private String brand;
	
	public Processor() {
		this.processorType = "";
		this.numberOfCores = "";
		this.Speed = "";
		this.brand = "";
	}

	public String getProcessorType() {
		return processorType;
	}

	public void setProcessorType(String processorType) {
		this.processorType = processorType;
	}

	public String getNumberOfCores() {
		return numberOfCores;
	}

	public void setNumberOfCores(String numberOfCores) {
		this.numberOfCores = numberOfCores;
	}

	public String getSpeed() {
		return Speed;
	}

	public void setSpeed(String speed) {
		Speed = speed;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
