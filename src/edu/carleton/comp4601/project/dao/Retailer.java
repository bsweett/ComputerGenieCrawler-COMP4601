package edu.carleton.comp4601.project.dao;

public class Retailer {

	//BESTBUY("http://www.bestbuy.ca/en-CA/category/desktop-computers/20213.aspx", "http://www.bestbuy.ca/en-CA/category/laptops-ultrabooks/20352.aspx", "http://www.bestbuy.ca/en-CA/product/"),
	//TIGERDIRECT("http://www.tigerdirect.ca/applications/category/guidedSearch.asp?CatId=6", "http://www.tigerdirect.ca/applications/category/guidedSearch.asp?CatId=17", "http://www.tigerdirect.ca/applications/SearchTools/item-details.asp"),
	//CANADACOMPUTERS("http://www.canadacomputers.com/index.php?cPath=7_1203", "http://www.canadacomputers.com/index.php?cPath=710_577", "http://www.ncix.com/detail/"),
	//NCIX("http://www.ncix.com/computers/","http://www.ncix.com/notebooks/", "http://www.ncix.com/detail");

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
