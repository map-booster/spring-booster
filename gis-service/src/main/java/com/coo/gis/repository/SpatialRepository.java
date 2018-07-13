package com.coo.gis.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.coo.gis.domain.GeoJsonFeature;

public interface SpatialRepository extends MongoRepository<GeoJsonFeature, String> {
	
	@Query("{ 'geometry' :{ $geoWithin: { $centerSphere: [ [?0 , ?1 ], ?2 ] } } }")
	public List<GeoJsonFeature> findWithinDistance(Double longitude, Double latitude, Double radius);

	@Query("{'properties.ADDRESS_NUMBER_ROAD_NAME' : ?0 }")
	public List<GeoJsonFeature> findAllByText(String searchText);
	
	@Query("{'properties.devAppAddress.roadName' : {$regex: ?0, $options: 'i'} }")
	public List<GeoJsonFeature> findByRoadName(String roadName);
	
	@Query("{$text : {$search: ?0} }")
	public List<GeoJsonFeature> findBySearchableText(String searchableText);

}
