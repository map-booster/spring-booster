package com.coo.gis.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class ObjectStatus {
	
	@Id
	private String objectStatusTypeId;
	
	private Map<String, String> objectCurrentStatus;
	
	private Long objectCurrentStatusDate;

}
