package edu.carleton.comp4601.project.crawl;

import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.carleton.comp4601.project.dao.Dimensions;
import edu.carleton.comp4601.project.dao.GraphicsCard;
import edu.carleton.comp4601.project.dao.Harddrive;
import edu.carleton.comp4601.project.dao.InputOutput;
import edu.carleton.comp4601.project.dao.Processor;
import edu.carleton.comp4601.project.dao.Product;
import edu.carleton.comp4601.project.dao.ProductType;
import edu.carleton.comp4601.project.dao.RAM;
import edu.carleton.comp4601.project.dao.Retailer;
import edu.carleton.comp4601.project.dao.RetailerName;
import edu.carleton.comp4601.project.dao.Screen;

public class ProductParser {

	private static final Logger logger = LoggerFactory.getLogger(ProductParser.class);

	private Document doc;
	private Retailer retailer;

	public ProductParser(Retailer retailer, Document doc) {
		setRetailer(retailer);
		setDoc(doc);
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public Product parseProductOfType(ProductType type, String url) {

		Product product = null;

		if(retailer.getName() == RetailerName.ncix) {
			product = parseNCIXProduct(type, url);
		}

		return product;
	}

	// NCIX
	private Product parseNCIXProduct(ProductType type, String url) {

		Product result = new Product();
		
		Element img = doc.select("img[id=productpicture]").first();
		Elements specs = doc.select("div[id=pdiv_3]").first().select("table.normal").first().select("tbody").first().select("tr");
		Elements featureTable = doc.select("blockquote[class=normal]").first().select("table").first().select("tbody").first().select("tr");
		//Elements priceColumns = doc.select("div[id=div_price]").first().select("table").select("tbody").first().select("tr").first().select("td");
		
		Element priceDiv = doc.select("div[id=div_price]").first();
		Element price1 = priceDiv.select("span[itemprop=price]").first();
		Element price2 = priceDiv.select("font[color=#CC0000]").first().select("b").first();

		Product product = new Product();
		product.setTitle(doc.title());
		product.setImageSrc(img.attr("src"));
		product.setType(type);
		product.setRetailer(RetailerName.ncix);
		product.setFetchDate(new Date().getTime());
		product.setUrl(url);

		//<span itemprop="price">$1,449.99</span></b>

		String price = "";
		if(price1 != null && price1.hasText()) {
			price = price1.text();
			product.setPrice(price.replace("$", "").replace(",", ""));
		} else if(price2 != null && price2.hasText()) {
			price = price2.text();
			product.setPrice(price.replace("$", "".replace(",", "")));
		} else {
			logger.warn("Could not fetch price for a product: " + url);
		}

		if(specs != null && !specs.isEmpty() && specs.get(0).children().size() >= 2) {
			result = buildDetailedProduct(product, specs);
			logger.info(result.toString());
		} else if(featureTable != null && !featureTable.isEmpty()) {
			result = buildBasicProduct(product, featureTable);
			logger.info(result.toString());
		} else {
			logger.warn("Could no parse any detail on product: " + url);
		}
		
		return result;
	}
	
	private Product buildDetailedProduct(Product product, Elements specs) {
		
		Dimensions dimensions = new Dimensions();
		Processor processor = new Processor();
		RAM ram = new RAM();
		Screen screen = new Screen();
		InputOutput io = new InputOutput();
		GraphicsCard graphics = new GraphicsCard();
		Harddrive harddrive = new Harddrive();
		
		String batteryString = "";
		String audioInOutString = "";
		
		for(Element spec : specs) {
			List<Element> childern = spec.children();
			if(childern.size() >= 2) {
				String title = childern.get(0).text();
				String value = childern.get(1).text();

				//General
				if(title.contains("Product Model")) {
					product.setModel(value);
				} else if(title.contains("Operating System") || title.contains("OS")) {
					product.setOs(value);
				} else if(title.contains("Battery Capacity") || title.contains("Battery Chemistry") || title.contains("Number of Cells") || title.contains("Battery")) {
					batteryString += " " + value;
				} else if(title.contains("Wireless LAN Standard") || title.contains("Wireless LAN")) {
					product.setWifi(value);
				} else if(title.contains("Bluetooth")) {
					product.setBluetooth(value);
				} else if(title.contains("Audio Line In") || title.contains("Audio Line Out") || title.contains("Audio")) {
					audioInOutString += " " + value;
					//Dimensions
				} else if(title.contains("Depth")) {
					dimensions.setDepth(value);
				} else if(title.contains("Width")) {
					dimensions.setWidth(value);
				} else if(title.contains("Height")) {
					dimensions.setHeight(value);
				} else if(title.contains("Weight")) {
					dimensions.setWeight(value);
					//Processor
				} else if(title.contains("Processor Core")) {
					processor.setNumberOfCores(value);
				} else if(title.contains("Processor Speed")) {
					processor.setSpeed(value);
				} else if(title.contains("Processor Type")) {
					processor.setProcessorType(value);
				} else if(title.contains("Processor Manufacturer") || title.contains("CPU")) {
					processor.setBrand(value);
					//RAM
				} else if(title.contains("Memory Technology")) {
					ram.setType(value);
				} else if(title.contains("Standard Memory") || title.contains("Memory")) {
					ram.setMemorySize(value);
					//Screen
				} else if(title.contains("Screen Resolution")) {
					screen.setScreenRes(value);
				} else if(title.contains("Screen Size") || title.contains("LCD Size")) {
					screen.setScreenSize(value);
				} else if(title.contains("Touchscreen")) {
					screen.setTouchScreen(value);
					//IO
				} else if(title.contains("Pointing Device Type")) {
					io.setMouse(value);
				} else if(title.contains("Keyboard Type") || title.contains("Keyboard")) {
					io.setKeyboard(value);
				} else if(title.contains("DVI")) {
					io.setHasDVI(value);
				} else if(title.contains("HDMI")) {
					io.setHasHDMI(value);
				} else if(title.contains("VGA")) {
					io.setHasVGA(value);
				} else if(title.contains("Display Port") || title.contains("Mini-DisplayPort")) {
					io.setFirewire(value);
				} else if(title.contains("Webcam")) {
					io.setHasWebCam(value);
				} else if(title.contains("Total Number of USB Ports") || title.contains("USB 3.0 port")) {
					io.setUSBPorts(value);
				} else if(title.contains("Speakers")) {
					io.setSpeakers(value);
					//Graphics
				} else if(title.contains("Graphics Controller Manufacturer")) {
					graphics.setManufacturer(value);
				} else if(title.contains("Graphics Memory Technology")) {
					graphics.setMemoryType(value);
				} else if(title.contains("Graphics Controller Model") || title.equals("Graphics")) {
					graphics.setModel(value);
				} else if(title.contains("Graphics Memory Capacity") || title.contains("Graphics VRAM")) {
					graphics.setMemoryCap(value);
					//Harddrive
				} else if(title.contains("Hard Drive Capacity") || title.contains("Hybrid Hard Drive Capacity") || 
						title.contains("Total Hard Drive Capacity") || title.contains("HDD") || title.contains("Solid State Drive Capacity")) {
					harddrive.setCapacity(value);
				} else if(title.contains("Hard Drive RPM")) {
					harddrive.setSpeed(value);
				} else if(title.contains("Hard Drive Interface") || title.contains("Solid State Drive Capacity")) {
					harddrive.setType(value);
				} else if(title.contains("Optical Drive")) {
					harddrive.setDriveType(value);
				}
					
				//TODO: Other formats?
				//http://www.ncix.com/detail/msi-gt60-dominator-i7-4710mq-92-102704-1202.htm
			}	
		}

		product.setBatteryLife(batteryString);
		product.setAudioDescription(audioInOutString);
		product.setScreen(screen);
		product.setIo(io);
		product.setGraphics(graphics);
		product.setRam(ram);
		product.setHarddrive(harddrive);
		product.setProcessor(processor);
		product.setDimensions(dimensions);
		
		return product;
	}

	private Product buildBasicProduct(Product product, Elements featureTable) {
		
		Processor processor = new Processor();
		RAM ram = new RAM();
		InputOutput io = new InputOutput();
		GraphicsCard graphics = new GraphicsCard();
		Harddrive harddrive = new Harddrive();
		
		for(Element tr : featureTable) {
			Elements tds = tr.children();
			if(tds.size() >= 2) {
				String title = tds.get(0).text();
				String value = tds.get(1).text();
				
				if(title.contains("CPU")) {
					processor.setBrand(value);
				} else if(title.contains("RAM")) {
					ram.setMemorySize(value);
				} else if(title.contains("GPU")) {
					graphics.setModel(value);
				} else if(title.contains("HDD")) {
					harddrive.setCapacity(value);
				} else if(title.contains("OS")) {
					product.setOs(value);
				} else if(title.contains("ODD")) {
					harddrive.setDriveType(value);
				} else if(title.contains("keyboard&mouse") || title.contains("Keyborad & Mouse")) {
					io.setMouse(value);
					io.setKeyboard(value);
				}
			}
		}
		
		return product;
	}
}

