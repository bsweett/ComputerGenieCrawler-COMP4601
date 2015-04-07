package edu.carleton.comp4601.project.main;

import edu.carleton.comp4601.project.crawl.CGCrawlController;
import edu.carleton.comp4601.project.dao.Retailer;
import edu.carleton.comp4601.project.dao.RetailerName;
import edu.carleton.comp4601.project.util.UrlBank;

public class CGCrawlMain {

	public static final String crawlStorageRoot = System.getProperty("user.home") + "/data/computergenie";
	
	public static void main(String[] args) {
				
		CGCrawlController bestBuyController = new CGCrawlController(crawlStorageRoot);
		CGCrawlController ncixController = new CGCrawlController(crawlStorageRoot);
		CGCrawlController tdController = new CGCrawlController(crawlStorageRoot);
		
		Retailer ncix = new Retailer(RetailerName.ncix, UrlBank.ncixSeeds, UrlBank.ncixFilters, UrlBank.productRoot);
		Retailer tigerD = new Retailer(RetailerName.tigerdirect, UrlBank.tdSeeds, UrlBank.tdFilters, UrlBank.tdPRoot);
		Retailer bestbuy = new Retailer(RetailerName.bestbuy, UrlBank.bbSeeds, UrlBank.bbFilters, UrlBank.bbPRoot);
				
		if(tdController.configure(tigerD)) {
			
		}
		
		if(ncixController.configure(ncix)) {
			ncixController.execute();
		}
		
		if(bestBuyController.configure(bestbuy)) {
			bestBuyController.execute();
		}
		
		//For now lets just run the tasks ourselves until we know everything is working
		/*CrawlTaskExecutor executor = new CrawlTaskExecutor(crawlController);
		executor.startExecutionAt(12, 0, 0);*/
	}
}
