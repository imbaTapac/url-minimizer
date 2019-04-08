package com.url.minimizer.logging;

public enum EventType {
	URL_REDIRECT("REDIRECT");

	private String type;

	EventType (String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
