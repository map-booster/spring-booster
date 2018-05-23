package com.coo.gis.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

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
	public String getEducationNews() {

		String json = null;

		try {
			json = new String ( Files.readAllBytes(new ClassPathResource("gis/educationnews.json").getFile().toPath()) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * Returns a {@link List} of populated places in GEOJSON format.
	 * 
	 * @return
	 */
	public String getPopulatedPlaces() {
		
		String json = null;
		
		try {
			json = new String ( Files.readAllBytes(new ClassPathResource("gis/populatedplaces.json").getFile().toPath()) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;
	}
	
	/*
	 * Dangerously removes all {@link GisEvent} objects from the data store.
	 * TO BE REMOVED.
	 */
	public void deleteAllGis() {
		repository.deleteAll();
	}

}
