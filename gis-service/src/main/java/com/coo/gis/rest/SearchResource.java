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
@RequestMapping("/search")
public class SearchResource {

	private SpatialService spatialService;

	@Autowired
	public SearchResource(SpatialService service) {
		this.spatialService = service;
	}
	
	@RequestMapping(value = "/findDevAppsByRoadName", method = RequestMethod.GET, produces = "application/json")
	public GeoJsonFeatureCollection findDevAppsByRoadName(@RequestParam(value="roadName") String roadName) {
		
		return spatialService.findDevAppsByRoadName(roadName);
	}
	
	@RequestMapping(value = "/findDevAppsBySearchableText", method = RequestMethod.GET, produces = "application/json")
	public GeoJsonFeatureCollection findDevAppsBySearchableText(@RequestParam(value="searchableText") String searchableText) {
		
		return spatialService.findDevAppsBySearchableText(searchableText);
	}

}
