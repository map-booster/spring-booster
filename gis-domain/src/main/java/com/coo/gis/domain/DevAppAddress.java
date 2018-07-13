package com.coo.gis.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "devapp_address")
public class DevAppAddress {
	
	@Id
	private String addressReferenceId;
	
	private Integer addressNumber;
	
	private String addressQualifier;
	
	private String legalUnit;
	
	@TextIndexed
	private String roadName;
	
	private String cardinalDirection;
	
	private String roadType;
	
	private String municipality;
	
	private String addressType;
	
	private Double addressLatitude;
	
	private Double addressLongitude;
	
	private String addressNumberRoadName;
	
	private Integer parcelPinNumber;
	
	

}
