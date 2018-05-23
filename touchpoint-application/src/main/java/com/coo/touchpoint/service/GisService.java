package com.coo.touchpoint.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class GisService {

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

}
