package com.coo.gis.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.coo.gis.domain.DevApp;

public interface DevAppRepository extends MongoRepository<DevApp, String>{
	
	@Query(value = "{'applicationType' : ?0}")
	public List<DevApp> findByAppType(String type, Pageable pageable);
	
	@Query("{'devAppAddress.roadName' : ?0 }")
	public List<DevApp> findAppsByRoadName(String roadname, Pageable pageable);

	@Query(value = "{'applicationNumber' : ?0}")
	public List<DevApp> findByApplicationNumber(String applicationNumber);
}
