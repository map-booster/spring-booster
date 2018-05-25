package com.coo.gis.rest;

import javax.validation.Valid;

import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coo.gis.domain.GisInfo;
import com.coo.gis.service.GisService;

@RestController
@RequestMapping("/gis")
public class GisResource {

	private GisService gisService;

	@Autowired
	public GisResource(GisService gisService) {
		this.gisService = gisService;
	}

	@CrossOrigin
	@RequestMapping(value = "/populatedplaces", method = RequestMethod.GET, produces = "application/json")
	public FeatureCollection getPopulatedPlaces() {
		return gisService.getPopulatedPlaces();
	}
	
	@CrossOrigin 
	@RequestMapping(value = "/educationnews", method = RequestMethod.GET, produces = "application/json")
	public FeatureCollection getEducationNews() {
		return gisService.getEducationNews();
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public GisInfo createGisInfo(@Valid @RequestBody GisInfo gisInfo) {
		return gisService.createGisInfo(gisInfo);
	}

}
