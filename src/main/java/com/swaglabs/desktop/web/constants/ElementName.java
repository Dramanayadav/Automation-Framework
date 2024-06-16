package com.swaglabs.desktop.web.constants;

public enum ElementName {

	ERROR_BUTTON("ErrorButton"),
	SWAGLABSLOGO("SwagLabsLogo"),
	APP_LOGO("app_logo");
	

	private String name;
	
	public String toString() {
		return name;
	}

	ElementName(String name) {
		this.name = name;

	}
}
