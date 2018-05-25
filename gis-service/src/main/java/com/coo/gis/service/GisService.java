package com.coo.gis.service;

import java.util.List;

import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.coo.gis.domain.GisInfo;
import com.coo.gis.repository.GisRepository;

@Component
public class GisService {

	private GisRepository repository;

	@Autowired
	public GisService(GisRepository repository) {
		this.repository = repository;
	}

	/**
	 * Returns a {@link List} of Education news in GEOJSON format.
	 * 
	 * @return
	 */
	public FeatureCollection getEducationNews() {
		GisInfo gisInfo = null;
		Page<GisInfo> page = null;
		PageRequest request = PageRequest.of(0, 1);
		
		page = repository.findGisInfo(GisInfo.EDUCATION_NEWS, request);
		

		if (null != page.getContent() && page.getContent().size() > 0) {
			gisInfo = page.getContent().get(0);
		}
		
		if(gisInfo != null)
			return gisInfo.getFeatureCollection();

		return null;
	}
	
	
	/**
	 * Returns a {@link List} of Populated places in GEOJSON format.
	 * 
	 * @return
	 */
	public FeatureCollection getPopulatedPlaces() {
		GisInfo gisInfo = null;
		Page<GisInfo> page = null;
		PageRequest request = PageRequest.of(0, 1);
		
		page = repository.findGisInfo(GisInfo.POPULATED_PLACES, request);
		

		if (null != page.getContent() && page.getContent().size() > 0) {
			gisInfo = page.getContent().get(0);
		}
		
		if(gisInfo != null)
			return gisInfo.getFeatureCollection();

		return null;
	}

	
	/**
	 * Creates and persists a {@link GisInfo} into the configured data
	 * store.
	 * 
	 * @param gisInfo
	 * @return
	 */
	public GisInfo createGisInfo(GisInfo gisInfo) {
		return repository.insert(gisInfo);
	}

	/*
	 * Dangerously removes all {@link GisEvent} objects from the data store. TO BE
	 * REMOVED.
	 */
	public void deleteAllGis() {
		repository.deleteAll();
	}

}
