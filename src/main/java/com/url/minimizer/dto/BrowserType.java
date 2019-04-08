package com.url.minimizer.dto;

public enum BrowserType {
	FIREFOX("Firefox"),
	CHROME("Chrome"),
	OPERA("Opera"),
	IE("Internet Explorer"),
	EDGE("Microsoft Edge");

	private final String type;

	public String getType() {
		return type;
	}

	BrowserType(String type) {
		this.type = type;
	}
}
