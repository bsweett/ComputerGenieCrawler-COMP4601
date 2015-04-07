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
		} else if(retailer.getName() == RetailerName.bestbuy) {
			product = parseBestbuyProduct(type, url);
		}

		return product;
	}

	private Product parseBestbuyProduct(ProductType type, String url) {

		Product product = new Product();

		Element img = doc.select("div[id=pdp-gallery]").first().select("img").first();
		Element priceSpan = doc.select("span.amount").first();
		Element modelSpan = doc.select("span[id=ctl00_CP_ctl00_PD_lblModelNumber]").first();
		Element specsSpan = doc.select("ul.std-tablist").first();

		product.setTitle(doc.title());
		product.setImageSrc("http://www.bestbuy.ca/" + img.attr("src"));
		product.setType(type);
		product.setRetailer(RetailerName.bestbuy);
		product.setFetchDate(new Date().getTime());
		product.setUrl(url);
		product.setPrice((priceSpan.text().replace("$", "").replace(",", "")));
		product.setModel(modelSpan.text());

		logger.info(product.toString());

		Dimensions dimensions = new Dimensions();
		Processor processor = new Processor();
		RAM ram = new RAM();
		Screen screen = new Screen();
		InputOutput io = new InputOutput();
		GraphicsCard graphics = new GraphicsCard();
		Harddrive harddrive = new Harddrive();

		String batteryString = "";
		String audioInOutString = "";

		if(specsSpan != null) {

			Elements lis = specsSpan.select("li");
			for(Element e : lis) {
				Element span = e.select("span").first();
				Element div = e.select("div").select("span").first();

				Element link = span.select("a").first();

				String title = "";
				if(link == null) {
					title = span.select("span").first().text();
				} else {
					title = link.text();
				}

				switch(title) {
				case "Screen Size":						//Screen
					screen.setScreenSize(div.text());
					break;
				case "Native Screen Resolution":
					screen.setScreenRes(div.text());
					break;
				case "Touchscreen Display":
					screen.setTouchScreen(div.text());
					break;
				case "Processor Type":					//Processor
					processor.setProcessorType(div.text());
					processor.setBrand(div.text());
					break;
				case "Processor Speed":
					processor.setSpeed(div.text());
					break;
				case "Processor Cores":
					processor.setNumberOfCores(div.text());
					break;
				case "Hard Drive Capacity": 			//Hard drive
					harddrive.setCapacity(div.text());
					break;
				case "RAM Size":						//RAM
					ram.setMemorySize(div.text());
					break;
				case "RAM Type":
					ram.setType(div.text());
					break;
				case "RAM":
					ram.setMemorySize(div.text());
					ram.setType(div.text());
					break;
				case "Graphics Chipset":				//Graphics
					graphics.setManufacturer(div.text());
					graphics.setModel(div.text());
					break;
				case "Graphics Card":
					graphics.setManufacturer(div.text());
					graphics.setModel(div.text());
					break;
				case "Webcam":							//IO
					io.setHasWebCam(div.text());
					break;
				case "USB Ports":
					io.setUSBPorts(div.text());
					break;
				case "USB 3.0 Ports":
					io.setUSBPorts(div.text());
					break;
				case "VGA Output":
					io.setHasVGA(div.text());
					break;
				case "DVI Output":
					io.setHasDVI(div.text());
					break;
				case "HDMI Output":
					io.setHasHDMI(div.text());
					break;
				case "Other Input or Output Ports":
					io.setFirewire(div.text());
					break;
				case "Keyboard":
					io.setKeyboard(div.text());
					break;
				case "Backlit Keyboard":
					io.setKeyboard(div.text());
					break;
				case "Type of Pointing Device":
					io.setMouse(div.text());
					break;
				case "Mouse":
					io.setMouse(div.text());
					break;
				case "Remote":
					io.setRemote(div.text());
					break;
				case "Speakers":
					io.setSpeakers(div.text());
					break;
				case "Depth":							//Dimensions
					dimensions.setDepth(div.text());
					break;
				case "Width":
					dimensions.setWidth(div.text());
					break;
				case "Height":
					dimensions.setHeight(div.text());
					break;
				case "Weight":
					dimensions.setWeight(div.text());
					break;
				case "Power Supply":					//General
					batteryString += div.text() + " ";
					break;
				case "Approximate Battery Life":
					batteryString += div.text() + " ";
					break;
				case "Integrated Microphone": 
					audioInOutString += (title + ": " + div.text() + ", ");
					break;
				case "Microphone Input": 
					audioInOutString += (title + ": " + div.text() + ", ");
					break;
				case "Digital Audio Output": 
					audioInOutString += (title + ": " + div.text() + ", ");
					break;
				case "Hardware Volume Control": 
					audioInOutString += (title + ": " + div.text()+ ",");
					break;
				case "Line-In Input": 
					audioInOutString += (title + ": " + div.text()+ ",");
					break;
				case "Line Out": 
					audioInOutString += (title + ": " + div.text()+ ",");
					break;
				case "Digital Input": 
					audioInOutString += (title + ": " + div.text()+ ",");
					break;
				case "Digital Output": 
					audioInOutString += (title + ": " + div.text()+ ",");
					break;
				case "Pre-loaded Operating System":
					product.setOs(div.text());
					break;
				case "Integrated Bluetooth":
					product.setBluetooth(div.text());
					break;
				case "Integrated Wi-Fi":
					product.setWifi(div.text());
					break;
				default:
					break;
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
		}

		return product;
	}

	// NCIX
	private Product parseNCIXProduct(ProductType type, String url) {

		Product result = new Product();

		Element img = doc.select("img[id=productpicture]").first();
		Elements specs = doc.select("div[id=pdiv_3]").first().select("table.normal").first().select("tbody").first().select("tr");
		Elements featureTable = doc.select("blockquote[class=normal]").first().select("table").first().select("tbody").first().select("tr");

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
			result = buildDetailedNCIXProduct(product, specs);
			logger.info(result.toString());
		} else if(featureTable != null && !featureTable.isEmpty()) {
			result = buildBasicNCIXProduct(product, featureTable);
			logger.info(result.toString());
		} else {
			logger.warn("Could no parse any detail on product: " + url);
		}

		return result;
	}

	private Product buildDetailedNCIXProduct(Product product, Elements specs) {

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

	private Product buildBasicNCIXProduct(Product product, Elements featureTable) {

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

