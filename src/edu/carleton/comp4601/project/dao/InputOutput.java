package edu.carleton.comp4601.project.dao;

public class InputOutput {

	private boolean hasWebCam;
	private String keyboard;
	private String mouse;
	private String remote;
	
	private String usb;
	private String firewire;
	private String cardreader;
	private boolean hasVGA;
	private boolean hasDVI;
	private boolean hasHDMI;
	
	private String other;
	
	public InputOutput() {
		
	}
	
	public boolean isHasWebCam() {
		return hasWebCam;
	}

	public void setHasWebCam(boolean hasWebCam) {
		this.hasWebCam = hasWebCam;
	}

	public String getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(String keyboard) {
		this.keyboard = keyboard;
	}

	public String getMouse() {
		return mouse;
	}

	public void setMouse(String mouse) {
		this.mouse = mouse;
	}

	public String getRemote() {
		return remote;
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public String getUsb() {
		return usb;
	}

	public void setUsb(String usb) {
		this.usb = usb;
	}

	public String getFirewire() {
		return firewire;
	}

	public void setFirewire(String firewire) {
		this.firewire = firewire;
	}

	public String getCardreader() {
		return cardreader;
	}

	public void setCardreader(String cardreader) {
		this.cardreader = cardreader;
	}

	public boolean isHasVGA() {
		return hasVGA;
	}

	public void setHasVGA(boolean hasVGA) {
		this.hasVGA = hasVGA;
	}

	public boolean isHasDVI() {
		return hasDVI;
	}

	public void setHasDVI(boolean hasDVI) {
		this.hasDVI = hasDVI;
	}

	public boolean isHasHDMI() {
		return hasHDMI;
	}

	public void setHasHDMI(boolean hasHDMI) {
		this.hasHDMI = hasHDMI;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}
