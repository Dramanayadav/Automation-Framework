package com.swaglabs.desktop.web.config;

public enum FeatureName {

	WEBACTIONSTEST("web actions test"),
	SWAGLABSlOGIN("swaglabslogin"),
	API("API");
	

	private String name;
	
	public String toString() {
		return name;
	}

	FeatureName(String name) {
		this.name = name;

	}
}
