package edu.carleton.comp4601.project.dao;

import java.util.Map;

public class Product {
	
	// Info
	private String id;
	private String title;
	private String model;
	private long releaseDate;
	private ProductType type;
	private Brand brand;
	private Dimensions dimensions;
	private String color;
	private Integer batteryLife;
	
	// Screen
	private Screen screen;
	
	// Processor
	private Processor processor;
	
	// RAM
	private RAM ram;
	
	//Hard drive
	private Harddrive harddrive;
	
	//Audio
	private String audioDescription;
	
	//IO
	private InputOutput io;
	
	//Networking
	private String bluetooth;
	private String wifi;
	
	public Product() {
		
	}
	
	public Product(String title, ProductType type, Brand brand, Screen screen, Processor processor, RAM ram, Harddrive drive, InputOutput io, Dimensions dim) {
		setTitle(title);
		setType(type);
		setBrand(brand);
		setScreen(screen);
		setProcessor(processor);
		setRam(ram);
		setHarddrive(drive);
		setIo(io);
		setDimensions(dim);
	}
	
	public Product(Map<?, ?> map) {
		this();
		this.setId((String) map.get("id"));
		this.setTitle((String) map.get("title"));
		this.setModel((String) map.get("model"));
		this.setReleaseDate((long) map.get("releasedate"));
		this.setType((ProductType) map.get("type"));
		this.setBrand((Brand) map.get("brand"));
		this.setDimensions((Dimensions) map.get("dimensions"));
		
		//TODO: Object mapping 
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public long getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(long releaseDate) {
		this.releaseDate = releaseDate;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Dimensions getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getBatteryLife() {
		return batteryLife;
	}

	public void setBatteryLife(Integer batteryLife) {
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
}

