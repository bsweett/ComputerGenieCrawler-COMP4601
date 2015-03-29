package edu.carleton.comp4601.project.crawl;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.carleton.comp4601.project.main.CrawlTask;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CGCrawlController implements CrawlTask {
	private static final Logger logger = LoggerFactory.getLogger(CGCrawlController.class);
	private String rootCrawlStoragePath;
	private HashSet<String> domains;
	private ConcurrentHashMap<String, CrawlController> controllers;
	
	public CGCrawlController(HashSet<String> domains, String rootCrawlStoragePath) {
		this.rootCrawlStoragePath = rootCrawlStoragePath;
		this.domains = domains;
		this.controllers = new ConcurrentHashMap<String, CrawlController>(domains.size());
	}
	
	/**
	 * Configures the crawlers with their own controller and configurations for every domain
	 * given. 
	 * 
	 * @return true if configuration was complete false if an exception was thrown
	 */
	public boolean configure() {
		
		try {
			int index = 0;
			for(String domain : domains) {
				CrawlConfig configure = new CrawlConfig();
				configure.setCrawlStorageFolder(rootCrawlStoragePath + "/crawler" + Integer.toString(index));
				configure.setMaxPagesToFetch(1000);
				configure.setPolitenessDelay(1000);
				configure.setResumableCrawling(true);
				
				PageFetcher pageFetcher = new PageFetcher(configure);
				
				RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
				RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
				
				CrawlController controller = new CrawlController(configure, pageFetcher, robotstxtServer);
				controller.setCustomData(domain);	// NOTE: Check URL for domain name and parse according to that (ie things to look for from the HTML)
				controller.addSeed(domain);
				
				controllers.put(domain, controller);
				index++;
			}
		} catch (Exception e) {
			logger.warn("Exception thrown configuring crawlers: " + e.getLocalizedMessage());
			return false;
		}
		
		
		return true;
	}
	
	/**
	 * Tells all of the controllers to block until complete
	 */
	@Override
	public void waitUntilFinished() {
		for(String domain : domains) {
			CrawlController controller = controllers.get(domain);
			controller.waitUntilFinish();
		}
	}

	/**
	 * Tells all of the controllers to start crawling
	 */
	@Override
	public void execute() {
		for(String domain: domains) {
			CrawlController controller = controllers.get(domain);
			controller.startNonBlocking(CGWebCrawler.class, 7);
		}
		
	}
}
