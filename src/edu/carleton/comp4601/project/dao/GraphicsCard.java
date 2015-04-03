package edu.carleton.comp4601.project.dao;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class GraphicsCard {

	private String manufacturer;
	private String model;
	private String memoryType;
	private String memoryCap;
	
	public GraphicsCard() {
		this.manufacturer = "";
		this.model = "";
		this.memoryType = "";
		this.memoryCap = "";
	}

	public String getManufacturer() {
		return manufacturer.toLowerCase();
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model.toLowerCase();
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMemoryType() {
		return memoryType.toLowerCase();
	}

	public void setMemoryType(String memoryType) {
		this.memoryType = memoryType;
	}

	public String getMemoryCap() {
		return memoryCap.toLowerCase();
	}

	public void setMemoryCap(String memoryCap) {
		this.memoryCap = memoryCap;
	}
}
