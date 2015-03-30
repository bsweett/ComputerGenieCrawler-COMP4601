package edu.carleton.comp4601.project.dao;

public class Processor {

	private String processorType;
	private Integer numberOfCores;
	private Double Speed;
	private String brand;
	
	public Processor() {
		
	}

	public String getProcessorType() {
		return processorType;
	}

	public void setProcessorType(String processorType) {
		this.processorType = processorType;
	}

	public Integer getNumberOfCores() {
		return numberOfCores;
	}

	public void setNumberOfCores(Integer numberOfCores) {
		this.numberOfCores = numberOfCores;
	}

	public Double getSpeed() {
		return Speed;
	}

	public void setSpeed(Double speed) {
		Speed = speed;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
