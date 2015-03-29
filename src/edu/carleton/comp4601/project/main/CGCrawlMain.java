package edu.carleton.comp4601.project.main;

import java.util.HashSet;

import edu.carleton.comp4601.project.crawl.CGCrawlController;

public class CGCrawlMain {

	public static final String crawlStorageRoot = System.getProperty("user.home") + "/data/computergenie";
	
	public static void main(String[] args) {
		
		HashSet<String> domains = new HashSet<String>();
		
		// Best Buy
		domains.add("http://www.bestbuy.ca/en-CA/category/laptops-ultrabooks/20352.aspx");
		domains.add("http://www.bestbuy.ca/en-CA/category/desktop-computers/20213.aspx");
		
		// Tiger Direct
		domains.add("http://www.tigerdirect.ca/applications/category/guidedSearch.asp?CatId=17");
		domains.add("http://www.tigerdirect.ca/applications/category/guidedSearch.asp?CatId=6");
		
		// Canada Computers
		domains.add("http://www.canadacomputers.com/index.php?cPath=710_577");
		domains.add("http://www.canadacomputers.com/index.php?cPath=7_1203");
		
		//NCIX
		domains.add("http://www.ncix.com/notebooks/");
		domains.add("http://www.ncix.com/computers/");
		
		CGCrawlController crawlController = new CGCrawlController(domains, crawlStorageRoot);
	
		if(crawlController.configure()) {
			
			//For now lets just run the task ourselves until we know everything is working
			
			/*CrawlTaskExecutor executor = new CrawlTaskExecutor(crawlController);
			executor.startExecutionAt(12, 0, 0);*/
			crawlController.execute();
			crawlController.waitUntilFinished();
		}
	}
}
