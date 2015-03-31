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
		Retailer ncix = new Retailer(RetailerName.NCIX, ncixSeeds, ncixFilters, productRoot);
		
		if(crawlController.configure(ncix)) {
			
			//For now lets just run the task ourselves until we know everything is working
			
			/*CrawlTaskExecutor executor = new CrawlTaskExecutor(crawlController);
			executor.startExecutionAt(12, 0, 0);*/
			//crawlController.execute();
			//crawlController.waitUntilFinished();
		}
	}
}
