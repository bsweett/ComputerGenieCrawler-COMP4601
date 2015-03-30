package edu.carleton.comp4601.project.crawl;

import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class CGWebCrawler extends WebCrawler {

	private static final Logger logger = LoggerFactory.getLogger(CGWebCrawler.class);

	private static final Pattern FILTERS = Pattern.compile(
			".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf" +
			"|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private String crawlDomain;

	@Override
	public void onStart() {
		crawlDomain = (String) myController.getCustomData();
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		if (FILTERS.matcher(href).matches()) {
			return false;
		}

		if (href.startsWith(crawlDomain)) {
			return true;
		}

		return false;
	}

	@Override
	public void visit(Page page) {
		int docid = page.getWebURL().getDocid();
	    String url = page.getWebURL().getURL();
	    int parentDocid = page.getWebURL().getParentDocid();
	    
	    if (page.getParseData() instanceof HtmlParseData) {
	    	HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	        String text = htmlParseData.getText();
	        String html = htmlParseData.getHtml();
	        Set<WebURL> links = htmlParseData.getOutgoingUrls();
	        
	        //TODO: Parse data differently based on domain name 
	    }
	}
}
