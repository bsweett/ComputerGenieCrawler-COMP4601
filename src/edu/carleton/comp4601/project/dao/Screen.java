package edu.carleton.comp4601.project.dao;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Screen {

	private String screenSize;
	private String screenRes;
	private String isTouchScreen;
	
	public Screen() {
		this.screenSize = "";
		this.screenRes = "";
		this.isTouchScreen = "";
	}
	
	public String getScreenSize() {
		return screenSize;
	}
	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}
	public String getScreenRes() {
		return screenRes;
	}
	public void setScreenRes(String screenRes) {
		this.screenRes = screenRes;
	}
	public String isTouchScreen() {
		return isTouchScreen;
	}
	public void setTouchScreen(String isTouchScreen) {
		this.isTouchScreen = isTouchScreen;
	}
	
}
