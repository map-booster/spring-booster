package com.coo.touchpoint.domain;

public enum TouchpointEventType {

	LOGIN("cooMbrPrtlLgn");
	

	private String description;

	private TouchpointEventType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
