package com.coo.gis.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "devapp")
public class DevApp {

	@Id
	private String devAppId;
	
	@Indexed
	private String applicationNumber;
	
	private Long applicationDate;
	
	private Map<String, String> applicationType;
	
	private Map<String, String> applicationBriefDesc;
	
	@Indexed
	private Map<String, String> applicationStatus;
	
	//@DBRef
	//@CascadeSave
	private List<DevAppAddress> devAppAddress;
	
	private ObjectStatus objectStatus;
	
	private DevAppWard devAppWard;
	
	private String plannerFirstName;
	
	private String plannerLastName;
	
	private String plannerPhone;
	
	private String plannerEmail;
	
	private String searchableText;

	public void addDevAppAddress(DevAppAddress address) {
		if(this.devAppAddress == null)
			this.devAppAddress = new ArrayList<>();
		this.devAppAddress.add(address);
		
	}
	
}
