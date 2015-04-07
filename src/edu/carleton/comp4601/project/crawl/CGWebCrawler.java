package edu.carleton.comp4601.project.crawl;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.carleton.comp4601.project.dao.Product;
import edu.carleton.comp4601.project.dao.ProductType;
import edu.carleton.comp4601.project.dao.Retailer;
import edu.carleton.comp4601.project.dao.RetailerName;
import edu.carleton.comp4601.project.database.DatabaseManager;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class CGWebCrawler extends WebCrawler {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CGWebCrawler.class);

	private static final Pattern FILTERS = Pattern.compile(
			".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf" +
			"|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private Retailer retailer;

	@Override
	public void onStart() {
		retailer = (Retailer) myController.getCustomData();
	}

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();

		if (FILTERS.matcher(href).matches()) {
			return false;
		}

		for(String s : retailer.getFilters()) {
			if(href.startsWith(s)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void visit(Page page) {
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();

			//logger.info("Visited url: " + url);

			Document doc = Jsoup.parse(html);
			if(doc != null) {
				configureParserByDomain(docid, url, doc);
			}
		}
	}

	private void configureParserByDomain(Integer id, String url, Document doc) {

		ProductType type = null;
		Product product = null;

		if(url.contains(retailer.getProductRoot())) {

			RetailerName domainName = retailer.getName();

			if(domainName == RetailerName.ncix) {
				type = isValidNCIXProduct(doc);
			} else if (domainName == RetailerName.tigerdirect) {
				type = isValidTigerDirectProduct(doc);
			} else if(domainName == RetailerName.bestbuy) {
				type = isValidBestBuyProduct(doc);
			}

			if(type != null) {
				ProductParser productParser = new ProductParser(retailer, doc);
				product = productParser.parseProductOfType(type, url);
			}
		} 

		addProductToDatabase(product);
	}

	private ProductType isValidNCIXProduct(Document doc) {
		Element span = doc.select("ul.bread-crumbs").first().select("li.crumb2").select("div").select("a").select("span").first();

		if(span != null) {
			if(span.text().equalsIgnoreCase("Desktop PC")) {
				return ProductType.desktop;
			} else if(span.text().equalsIgnoreCase("Notebooks")) {
				return ProductType.laptop;
			}
		}

		return null;
	}

	private ProductType isValidTigerDirectProduct(Document doc) {
		Element span = doc.select("ul.breadcrumb").first();

		if(span != null) {
			if(span.html().equalsIgnoreCase("Desktop Computers")) {
				return ProductType.desktop;
			} else if(span.html().equalsIgnoreCase("Laptops & Notebooks")) {
				return ProductType.laptop;
			}
		}
		return null;
	}

	private ProductType isValidBestBuyProduct(Document doc) {
		Element span = doc.select("span[id=ctl00_CP_ctl00_Breadcrumb_SiteMapPath]").first();
		Element iMac = span.select("a[title=Apple iMac]").first();
		Element mini = span.select("a[title=Apple Mac Mini]").first();
		Element pro = span.select("a[title=Apple Mac Pro]").first();
		Element air = span.select("a[title=Apple MacBook Air]").first();
		Element bookPro = span.select("a[title=Apple MacBook Pro]").first();
		
		if(iMac != null || mini != null || pro != null) {
			return ProductType.desktop;
		}
		
		if(air != null || bookPro != null) {
			return ProductType.laptop;
		}
		
		//TODO: Check other desktop and lap top types
		
		return null;
	}

	private void addProductToDatabase(Product product) {
		if(product != null) {
			DatabaseManager.getInstance().findAndUpdateProductByTitle(product);
		}
	}

}
