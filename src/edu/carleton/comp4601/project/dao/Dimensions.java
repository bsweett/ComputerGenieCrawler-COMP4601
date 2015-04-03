package edu.carleton.comp4601.project.dao;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Dimensions {

	private String width;
	private String height;
	private String depth;
	private String weight;
	
	public Dimensions() {
		this.width = "";
		this.height = "";
		this.depth = "";
		this.weight = "";
	}
	
	public Dimensions(String width, String height, String depth, String weight) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.weight = weight;
	}
	
	public String getWidth() {
		return width.toLowerCase();
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height.toLowerCase();
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getDepth() {
		return depth.toLowerCase();
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getWeight() {
		return weight.toLowerCase();
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
}
