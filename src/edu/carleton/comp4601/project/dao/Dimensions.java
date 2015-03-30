package edu.carleton.comp4601.project.dao;

public class Dimensions {

	private Double width;
	private Double height;
	private Double depth;
	private Double weight;
	
	public Dimensions(Double width, Double height, Double depth, Double weight) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.weight = weight;
	}
	
	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getDepth() {
		return depth;
	}

	public void setDepth(Double depth) {
		this.depth = depth;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
}
