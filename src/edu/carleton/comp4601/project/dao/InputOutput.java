package edu.carleton.comp4601.project.dao;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class InputOutput {

	private String hasWebCam;
	private String keyboard;
	private String mouse;
	private String remote;
	
	private String firewire;
	private String usbPorts;
	private String hasVGA;
	private String hasDVI;
	private String hasHDMI;
	
	private String speakers;
	
	private String other;
	
	public InputOutput() {
		this.hasDVI = "";
		this.hasHDMI = "";
		this.hasVGA = "";
		this.speakers = "";
		this.mouse = "";
		this.keyboard = "";
		this.firewire = "";
		this.remote = "";
		this.usbPorts = "";
		this.hasWebCam = "";
		this.other = "";
	}
	
	public String isHasWebCam() {
		return hasWebCam.toLowerCase();
	}

	public void setHasWebCam(String hasWebCam) {
		this.hasWebCam = hasWebCam;
	}

	public String getKeyboard() {
		return keyboard.toLowerCase();
	}

	public void setKeyboard(String keyboard) {
		this.keyboard = keyboard;
	}

	public String getMouse() {
		return mouse.toLowerCase();
	}

	public void setMouse(String mouse) {
		this.mouse = mouse;
	}

	public String getRemote() {
		return remote.toLowerCase();
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public String getFirewire() {
		return firewire.toLowerCase();
	}

	public void setFirewire(String firewire) {
		this.firewire = firewire;
	}

	public String getUSBPorts() {
		return usbPorts.toLowerCase();
	}

	public void setUSBPorts(String usbPorts) {
		this.usbPorts = usbPorts;
	}

	public String isHasVGA() {
		return hasVGA.toLowerCase();
	}

	public void setHasVGA(String hasVGA) {
		this.hasVGA = hasVGA;
	}

	public String isHasDVI() {
		return hasDVI.toLowerCase();
	}

	public void setHasDVI(String hasDVI) {
		this.hasDVI = hasDVI;
	}

	public String isHasHDMI() {
		return hasHDMI.toLowerCase();
	}

	public void setHasHDMI(String hasHDMI) {
		this.hasHDMI = hasHDMI;
	}

	public String getOther() {
		return other.toLowerCase();
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getSpeakers() {
		return speakers.toLowerCase();
	}

	public void setSpeakers(String speakers) {
		this.speakers = speakers;
	}

}
