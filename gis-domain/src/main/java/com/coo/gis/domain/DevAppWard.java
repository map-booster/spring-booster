package com.coo.gis.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class DevAppWard {
	
	@Id
	private String communityId;
	
	private Map<String, String> wardNumber;
	
	private Map<String, String> wardName;
	
	private String councillorLastName;
	
	private String councillorFirstName;
	
	private String wardCouncillorEmail;
	
}
