package edu.carleton.comp4601.project.main;

import edu.carleton.comp4601.project.crawl.CGCrawlController;
import edu.carleton.comp4601.project.dao.Retailer;
import edu.carleton.comp4601.project.dao.RetailerName;

public class CGCrawlMain {

	public static final String crawlStorageRoot = System.getProperty("user.home") + "/data/computergenie";
	
	public static void main(String[] args) {
				
		CGCrawlController crawlController = new CGCrawlController(crawlStorageRoot);
	
		String[] ncixSeeds = {"http://www.ncix.com/computers/", "http://www.ncix.com/notebooks/"};
		String[] ncixFilters = {"http://www.ncix.com/computers/", "http://www.ncix.com/notebooks/", 
				"http://www.ncix.com/detail", "http://www.ncix.com/category/business-desktops-cc-1357.htm", 
				"http://www.ncix.com/category/consumer-desktops-96-1356.htm", 
				"http://www.ncix.com/category/gaming-desktop-99-1413.htm" };
		
		String productRoot = "http://www.ncix.com/detail";
		Retailer ncix = new Retailer(RetailerName.ncix, ncixSeeds, ncixFilters, productRoot);
		
		String[] tdSeeds = {"http://www.tigerdirect.ca/applications/category/guidedSearch.asp?CatId=17", "http://www.tigerdirect.ca/applications/category/guidedSearch.asp?CatId=6"};
		String[] tdFilters = {"http://www.tigerdirect.ca/applications/searchtools/item-details.asp", "http://www.tigerdirect.ca/applications/category/guidedSearch.asp?CatId=17", 
				"http://www.tigerdirect.ca/applications/category/guidedSearch.asp?CatId=6" };
		
		
				/*"http://www.tigerdirect.ca/applications/category/category_tlc.asp?CatId=114", 
				"http://www.tigerdirect.ca/applications/category/category_tlc.asp?CatId=3839", "http://www.tigerdirect.ca/applications/category/category_tlc.asp?CatId=2627", 
				"http://www.tigerdirect.ca/applications/category/category_tlc.asp?CatId=4935", "http://www.tigerdirect.ca/applications/category/category_tlc.asp?CatId=927", 
				""};*/
		String tdPRoot = "http://www.tigerdirect.ca/applications/SearchTools/item-details.asp";
		
		Retailer tigerD = new Retailer(RetailerName.tigerdirect, tdSeeds, tdFilters, tdPRoot);
		
		if(crawlController.configure(tigerD)) {
			
			//For now lets just run the task ourselves until we know everything is working
			
			/*CrawlTaskExecutor executor = new CrawlTaskExecutor(crawlController);
			executor.startExecutionAt(12, 0, 0);*/
			//crawlController.execute();
			//crawlController.waitUntilFinished();
		}
	}
}
