package com.coo.gis.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
			InputStream inputStream = getClass().getResourceAsStream("/gis/educationnews.json");
			json = readFromInputStream(inputStream);
		} catch (Exception e) {
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
			InputStream inputStream = getClass().getResourceAsStream("/gis/populatedplaces.json");
			json = readFromInputStream(inputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;
	}

	/*
	 * Dangerously removes all {@link GisEvent} objects from the data store. TO BE
	 * REMOVED.
	 */
	public void deleteAllGis() {
		repository.deleteAll();
	}

	private String readFromInputStream(InputStream inputStream) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

}
