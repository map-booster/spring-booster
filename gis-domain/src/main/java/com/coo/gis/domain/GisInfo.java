package com.coo.gis.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "gis")
public class GisInfo {
	
	public static final String EDUCATION_NEWS = "educationnews";
	public static final String POPULATED_PLACES = "populatedplaces";

	private String type;
	
	private GeoJsonFeatureCollection featureCollection;
	
}
