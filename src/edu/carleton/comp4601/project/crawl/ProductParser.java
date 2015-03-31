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

	public Product parseProductOfType(ProductType type) {

		Product product = null;

		if(retailer.getName() == RetailerName.NCIX) {
			product = parseNCIXProduct(type);
		}

		return product;
	}

	// NCIX

	//TODO: Two functions 1 for main specifications and another for the backup
	private Product parseNCIXProduct(ProductType type) {

		Element img = doc.select("img[id=productpicture]").first();
		Elements specs = doc.select("div[id=pdiv_3]").first().select("table.normal").first().select("tbody").first().select("tr");
		//Elements priceColumns = doc.select("div[id=div_price]").first().select("table").select("tbody").first().select("tr").first().select("td");
		Element price = doc.select("div[id=div_price]").select("span[itemprop=price]").first();

		Product product = new Product();
		Dimensions dimensions = new Dimensions();
		Processor processor = new Processor();
		RAM ram = new RAM();
		Screen screen = new Screen();
		InputOutput io = new InputOutput();
		GraphicsCard graphics = new GraphicsCard();
		Harddrive harddrive = new Harddrive();

		product.setTitle(doc.title());
		product.setImageSrc(img.attr("src"));
		product.setType(type);
		product.setRetailer(RetailerName.NCIX);
		product.setFetchDate(new Date().getTime());

		//<span itemprop="price">$1,449.99</span></b>

		if(price != null) {
			product.setPrice(price.text());
		} else {
			logger.warn("Could not fetch price for a product");
		}

		String batteryString = "";
		String audioInOutString = "";

		if(specs != null && !specs.isEmpty() && specs.get(0).children().size() >= 2) {
			for(Element spec : specs) {
				List<Element> childern = spec.children();
				if(childern.size() >= 2) {
					String title = childern.get(0).text();
					String value = childern.get(1).text();

					//General
					if(title.contains("Product Model")) {
						product.setModel(value);
					} else if(title.contains("Operating System")) {
						product.setOs(value);
					} else if(title.contains("Battery Capacity") || title.contains("Battery Chemistry") || title.contains("Number of Cells") ) {
						batteryString += " " + value;
					} else if(title.contains("Wireless LAN Standard")) {
						product.setWifi(value);
					} else if(title.contains("Bluetooth Standard")) {
						product.setBluetooth(value);
					} else if(title.contains("Audio Line In") || title.contains("Audio Line Out")) {
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
					} else if(title.contains("Processor Manufacturer")) {
						processor.setBrand(value);
						//RAM
					} else if(title.contains("Memory Technology")) {
						ram.setType(value);
					} else if(title.contains("Standard Memory")) {
						ram.setMemorySize(value);
						//Screen
					} else if(title.contains("Screen Resolution")) {
						screen.setScreenRes(value);
					} else if(title.contains("Screen Size")) {
						screen.setScreenSize(value);
					} else if(title.contains("Touchscreen")) {
						screen.setTouchScreen(value);
						//IO
					} else if(title.contains("Pointing Device Type")) {
						io.setMouse(value);
					} else if(title.contains("Keyboard Type")) {
						io.setKeyboard(value);
					} else if(title.contains("DVI")) {
						io.setHasDVI(value);
					} else if(title.contains("HDMI")) {
						io.setHasHDMI(value);
					} else if(title.contains("VGA")) {
						io.setHasVGA(value);
					} else if(title.contains("Display Port")) {
						io.setFirewire(value);
					} else if(title.contains("Webcam")) {
						io.setHasWebCam(value);
					} else if(title.contains("Total Number of USB Ports")) {
						io.setUSBPorts(value);
					} else if(title.contains("Speakers")) {
						io.setSpeakers(value);
						//Graphics
					} else if(title.contains("Graphics Controller Manufacturer")) {
						graphics.setManufacturer(value);
					} else if(title.contains("Graphics Memory Technology")) {
						graphics.setMemoryType(value);
					} else if(title.contains("Graphics Controller Model")) {
						graphics.setModel(value);
						//Harddrive
					} else if(title.contains("Hard Drive Capacity") || title.contains("Hybrid Hard Drive Capacity") || title.contains("Total Hard Drive Capacity")) {
						harddrive.setCapacity(value);
					} else if(title.contains("Hard Drive RPM")) {
						harddrive.setSpeed(value);
					} else if(title.contains("Hard Drive Interface")) {
						harddrive.setType(value);
					} else if(title.contains("Optical Drive Type")) {
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
			
		} else {
			
			Elements featureTable = doc.select("blockquote[class=normal]").first().select("table").first().select("tbody").first().select("tr");
			
			if(featureTable != null && !featureTable.isEmpty()) {
				for(Element tr : featureTable) {
					Elements tds = tr.children();
					if(tds.size() >= 2) {
						String title = tds.get(0).text();
						//String value = tds.get(1).text();
						
						if(title.contains("CPU")) {
							
						} else if(title.contains("RAM")) {
							
						} else if(title.contains("GPU")) {
							
						} else if(title.contains("HDD")) {
							
						} else if(title.contains("OS")) {
							
						} else if(title.contains("ODD")) {
							
						} else if(title.contains("keyboard&mouse")) {
							
						} else if(title.contains("")) {
							
						} 
					}
				}
			} else {
				// Last resort document couldn't be parsed correctly
			}
		}

		logger.info(product.toString());

		return product;
		/*
		product.setModel(modelSpan.text());
		product.setPrice(priceSpan.text());*/

	}


}

