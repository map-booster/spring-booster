package com.coo.gis.domain;

public enum GisEventType {

	LOGIN("cooMbrPrtlLgn");
	

	private String description;

	private GisEventType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
