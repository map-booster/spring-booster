package com.coo.gis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.coo.gis.domain.GisEvent;

public interface GisRepository extends MongoRepository<GisEvent, String> {

	@Query("{'touchpointTransactionContextCode' : ?0 }")
	public Page<GisEvent> findMostRecentTouchpointByType(String type, Pageable pageable);

//	@Query("{'sort' : ['eventStartTimestamp', 'desc'], 'touchpointTransactionContextCode' : ?0 }")
//	public GisEvent findMostRecentTouchpointByType(String type);

	@Query("{ 'partyIdentification.identityName' : 'userId', 'partyIdentification.identityValue' : ?0, 'touchpointTransactionContextCode' : ?1 }")
	public Page<GisEvent> findMostRecentTouchpointByUserIdAndType(String userId, String type, Pageable pageable);

}
