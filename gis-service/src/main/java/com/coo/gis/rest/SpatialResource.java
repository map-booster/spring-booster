package com.coo.gis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coo.gis.domain.GeoJsonFeatureCollection;
import com.coo.gis.service.SpatialService;

@RestController
@CrossOrigin 
@RequestMapping("/spatial")
public class SpatialResource {

	private SpatialService service;

	@Autowired
	public SpatialResource(SpatialService service) {
		this.service = service;
	}

	@RequestMapping(value = "/features", method = RequestMethod.GET, produces = "application/json")
	public GeoJsonFeatureCollection getAll() {
		
		return service.findAllSpatial();
	}
	
	@RequestMapping(value = "/findByLocation", method = RequestMethod.GET, produces = "application/json")
	public GeoJsonFeatureCollection findByRadius(@RequestParam Double longitude, @RequestParam Double latitude, @RequestParam Double radius) {
		
		return service.findWithinDistance(longitude, latitude, radius);
	}
	
	@RequestMapping(value = "/featuresByText", method = RequestMethod.GET, produces = "application/json")
	public GeoJsonFeatureCollection getAppsbyText(@RequestParam(value="searchText") String searchText) {
		
		return service.findSpatialbyText(searchText);
	}
	
}
