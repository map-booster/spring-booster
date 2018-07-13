package com.coo.gis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.coo.gis.repository.DevAppRepository;


@Component
public class SearchService {
	
	private DevAppRepository repository;
		
	@Autowired
	public SearchService(DevAppRepository repository) {
		this.repository = repository;
	}

	
}
