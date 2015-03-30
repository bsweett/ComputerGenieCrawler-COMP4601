package edu.carleton.comp4601.project.dao;

public class Screen {

	private String screenSize;
	private String screenRes;
	private boolean isTouchScreen;
	
	public Screen() {
		
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
	public boolean isTouchScreen() {
		return isTouchScreen;
	}
	public void setTouchScreen(boolean isTouchScreen) {
		this.isTouchScreen = isTouchScreen;
	}
	
}
