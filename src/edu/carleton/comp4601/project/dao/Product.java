package edu.carleton.comp4601.project.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("products")
public class Product {
	
	//Identifiers 
	@Id ObjectId id;
	private RetailerName retailer;
	
	// Info
	private String title;
	private String imageSrc;
	private String model;
	private String url;
	private long fetchedDate;
	private ProductType type;
	
	@Embedded
	private Dimensions dimensions;
	
	private String batteryLife;
	
	// Screen
	@Embedded
	private Screen screen;
	
	// Processor
	@Embedded
	private Processor processor;
	
	// RAM
	@Embedded
	private RAM ram;
	
	//Hard drive
	@Embedded
	private Harddrive harddrive;
	
	//Audio
	private String audioDescription;
	
	//IO
	@Embedded
	private InputOutput io;
	
	//Networking
	private String bluetooth;
	private String wifi;
	
	//Graphics
	@Embedded
	private GraphicsCard graphics;
	
	//Price
	private String price;
	
	//Operating System
	private String os;
	
	public Product() {
		this.model = "";
		this.imageSrc = "";
		this.os = "";
		this.wifi = "";
		this.bluetooth = "";
		this.audioDescription = "";
		this.batteryLife = "";
		
		this.processor = new Processor();
		this.graphics = new GraphicsCard();
		this.harddrive = new Harddrive();
		this.io = new InputOutput();
		this.dimensions = new Dimensions();
		this.ram = new RAM();
		this.screen = new Screen();
	}
	
	public Product(String title, ProductType type, Screen screen, Processor processor, RAM ram, Harddrive drive, InputOutput io, Dimensions dim) {
		setTitle(title);
		setType(type);
		setScreen(screen);
		setProcessor(processor);
		setRam(ram);
		setHarddrive(drive);
		setIo(io);
		setDimensions(dim);
	}
	
	public ObjectId getId() {
		return this.id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getFetchDate() {
		return fetchedDate;
	}

	public void setFetchDate(long fetchDate) {
		this.fetchedDate = fetchDate;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public Dimensions getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}

	public String getBatteryLife() {
		return batteryLife;
	}

	public void setBatteryLife(String batteryLife) {
		this.batteryLife = batteryLife;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public Processor getProcessor() {
		return processor;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public RAM getRam() {
		return ram;
	}

	public void setRam(RAM ram) {
		this.ram = ram;
	}

	public Harddrive getHarddrive() {
		return harddrive;
	}

	public void setHarddrive(Harddrive harddrive) {
		this.harddrive = harddrive;
	}

	public String getAudioDescription() {
		return audioDescription;
	}

	public void setAudioDescription(String audioDescription) {
		this.audioDescription = audioDescription;
	}
	
	public String getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}

	public String getWifi() {
		return wifi;
	}

	public void setWifi(String wifi) {
		this.wifi = wifi;
	}

	public InputOutput getIo() {
		return io;
	}

	public void setIo(InputOutput io) {
		this.io = io;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public GraphicsCard getGraphics() {
		return graphics;
	}

	public void setGraphics(GraphicsCard graphics) {
		this.graphics = graphics;
	}

	public RetailerName getRetailer() {
		return retailer;
	}

	public void setRetailer(RetailerName retailer) {
		this.retailer = retailer;
	}

	@Override
	public String toString() {
		return this.title + " " + this.price + " " + this.model;
	} 
}

