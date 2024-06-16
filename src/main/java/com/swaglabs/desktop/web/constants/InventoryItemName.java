package com.swaglabs.desktop.web.constants;

public enum InventoryItemName {

	SAUCE_LABS_BACKPACK("Sauce Labs Backpack"),
	SAUCE_LABS_BIKE_LIGHT("Sauce Labs Bike Light");
	
	

	private String name;
	
	public String getName() {
		return name;
	}

	InventoryItemName(String name) {
		this.name = name;

	}
}
