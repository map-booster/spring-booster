package com.coo.gis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coo.gis.service.GisService;

@RestController
@RequestMapping("/gis")
public class GisResource {

	private GisService gisService;

	@Autowired
	public GisResource(GisService gisService) {
		this.gisService = gisService;
	}

	@RequestMapping(value = "/populatedplaces", method = RequestMethod.GET)
	public String getPopulatedPlaces() {
		return gisService.getPopulatedPlaces();
	}
	
	@RequestMapping(value = "/educationnews", method = RequestMethod.GET)
	public String getEducationNews() {
		return gisService.getEducationNews();
	}

}
