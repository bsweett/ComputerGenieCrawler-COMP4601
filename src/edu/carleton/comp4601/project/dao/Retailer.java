package edu.carleton.comp4601.project.dao;

public class Retailer {

	private String[] seeds;
	private String[] filters;
	private String productRoot;
	private RetailerName name;
	
	public Retailer(RetailerName name, String[] seeds, String[] filters, String productRoot) {
		this.setName(name);
		this.seeds = seeds;
		this.filters = filters;
		this.productRoot = productRoot;
	}
	
	public RetailerName getName() {
		return name;
	}

	public void setName(RetailerName name) {
		this.name = name;
	}

	public String[] getSeeds() {
		return this.seeds;
	}
	
	public String[] getFilters() {
		return this.filters;
	}	
	
	public String getProductRoot() {
		return this.productRoot;
	}
}
