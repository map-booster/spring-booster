package com.coo.gis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coo.gis.domain.DevApp;
import com.coo.gis.domain.GeoJsonFeature;
import com.coo.gis.domain.GeoJsonFeatureCollection;
import com.coo.gis.repository.SpatialRepository;

@Component
public class SpatialService {

	private SpatialRepository repository;
	
	public static final Double RADIUS_OF_EARTH_IN_KM = 6378.1;

	@Autowired
	public SpatialService(SpatialRepository repository) {
		this.repository = repository;
	}

	/**
	 * Returns a {@link GeoJsonFeatureCollection} of spatial data in GEOJSON format.
	 * 
	 * @return
	 */
	public GeoJsonFeatureCollection findAllSpatial() {
		
		List<GeoJsonFeature> features = repository.findAll();
		
		return new GeoJsonFeatureCollection(features);
	}
	
	/**
	 * Returns a {@link GeoJsonFeatureCollection} of spatial data in GEOJSON format.
	 * 
	 * @return
	 */
	public GeoJsonFeatureCollection findWithinDistance(Double longitude, Double latitude, Double radius) {
		
		List<GeoJsonFeature> features = repository.findWithinDistance(longitude, latitude, (radius / RADIUS_OF_EARTH_IN_KM));
		
		return new GeoJsonFeatureCollection(features);
	}

	/**
	 * Returns a {@link GeoJsonFeatureCollection} of spatial & {@link DevApp} data in GEOJSON format.
	 * 
	 * @return
	 */
	public GeoJsonFeatureCollection findSpatialbyText(String searchText) {
		
		List<GeoJsonFeature> features = repository.findAllByText(searchText);
		
		return new GeoJsonFeatureCollection(features);
	}

	/**
	 * Returns a {@link GeoJsonFeatureCollection} of spatial & {@link DevApp} data in GEOJSON format.
	 * 
	 * @return
	 */
	public GeoJsonFeatureCollection findDevAppsBySearchableText(String searchableText) {
		
		List<GeoJsonFeature> features = repository.findBySearchableText(searchableText);
		
		return new GeoJsonFeatureCollection(features);
	}
	
	/**
	 * Returns a {@link GeoJsonFeatureCollection} of spatial & {@link DevApp} data in GEOJSON format.
	 * 
	 * @return
	 */
	public GeoJsonFeatureCollection findDevAppsByRoadName(String roadName) {
		
		List<GeoJsonFeature> features = repository.findByRoadName(roadName);
		
		return new GeoJsonFeatureCollection(features);
	}
	
	/**
	 * Creates and persists a {@link GeoJsonFeature} into the configured data
	 * store.
	 * 
	 * @param gisInfo
	 * @return
	 */
	public GeoJsonFeature createSpatial(GeoJsonFeature feature) {
		return repository.insert(feature);
	}

	/*
	 * Dangerously removes all {@link GeoJsonFeature} objects from the data store. TO BE
	 * REMOVED.
	 */
	public void deleteAllSpatial() {
		repository.deleteAll();
	}

}
