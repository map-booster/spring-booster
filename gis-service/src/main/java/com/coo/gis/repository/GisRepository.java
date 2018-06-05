package com.coo.gis.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.coo.gis.domain.GisInfo;

public interface GisRepository extends MongoRepository<GisInfo, String> {

	@Query("{'type' : ?0 }")
	public List<GisInfo> findGisInfo(String type, Pageable pageable);

}
