package com.coo.gis.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spatial")
public class GeoJsonFeature extends Feature {
	
	private static final long serialVersionUID = 7230952817608255795L;

	private static final String TYPE = "Feature";


	public String getType() {

		return TYPE;
	}


}
