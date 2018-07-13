package com.coo.gis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coo.gis.domain.DevApp;
import com.coo.gis.repository.DevAppRepository;

@Component
public class DevAppService {

	private DevAppRepository repository;

	@Autowired
	public DevAppService(DevAppRepository repository) {
		this.repository = repository;
	}

	/**
	 * Returns a {@link List} of {@link}DevApp}.
	 * 
	 * @return
	 */
	public List<DevApp> findByApplicationNumber(String applicationNumber) {
		
		return repository.findByApplicationNumber(applicationNumber);
		
	}

	/**
	 * Creates and persists a {@link DevApp} into the configured data
	 * store.
	 * 
	 * @param devApp
	 * @return
	 */
	public DevApp createDevApp(DevApp devApp) {
		return repository.insert(devApp);
	}

	/**
	 * Returns a {@link List} of {@link DevApp} 
	 * 
	 * 
	 * @return
	 */
	public List<DevApp> getAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
