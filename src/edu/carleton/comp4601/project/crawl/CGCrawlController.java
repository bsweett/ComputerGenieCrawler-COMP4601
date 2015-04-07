package edu.carleton.comp4601.project.crawl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.carleton.comp4601.project.dao.Retailer;
import edu.carleton.comp4601.project.main.CrawlTask;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CGCrawlController implements CrawlTask {
	private static final Logger logger = LoggerFactory.getLogger(CGCrawlController.class);
	private String rootCrawlStoragePath;
	private CrawlController controller;

	public CGCrawlController(String rootCrawlStoragePath) {
		this.rootCrawlStoragePath = rootCrawlStoragePath;
	}

	/**
	 * Configures the crawlers with their own controller and configurations for every domain
	 * given. 
	 * 
	 * @return true if configuration was complete false if an exception was thrown
	 */
	public boolean configure(Retailer retailer) {

		try {
			CrawlConfig configure = new CrawlConfig();
			configure.setCrawlStorageFolder(rootCrawlStoragePath + "/crawler/" + retailer.getName().toString() + "/");
			configure.setMaxPagesToFetch(10000);
			configure.setResumableCrawling(false);

			PageFetcher pageFetcher = new PageFetcher(configure);

			RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
			RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

			controller = new CrawlController(configure, pageFetcher, robotstxtServer);
			controller.setCustomData(retailer);
			
			for(String s : retailer.getSeeds()) {
				controller.addSeed(s);
			}
			
		} catch (Exception e) {
			logger.warn("Exception thrown configuring crawler: " + e.getLocalizedMessage());
			return false;
		}

		return true;
	}

	/**
	 * Tells the controller to start crawling. 
	 * 
	 * @return true when complete
	 */
	@Override
	public boolean execute() {
		controller.start(CGWebCrawler.class, 3);
		return true;
	}
}
