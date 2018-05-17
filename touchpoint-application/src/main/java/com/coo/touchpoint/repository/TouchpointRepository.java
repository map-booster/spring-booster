package com.coo.touchpoint.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.coo.touchpoint.domain.TouchpointEvent;

public interface TouchpointRepository extends MongoRepository<TouchpointEvent, String> {

	@Query("{'touchpointTransactionContextCode' : ?0 }")
	public Page<TouchpointEvent> findMostRecentTouchpointByType(String type, Pageable pageable);

//	@Query("{'sort' : ['eventStartTimestamp', 'desc'], 'touchpointTransactionContextCode' : ?0 }")
//	public TouchpointEvent findMostRecentTouchpointByType(String type);

	@Query("{ 'partyIdentification.identityName' : 'userId', 'partyIdentification.identityValue' : ?0, 'touchpointTransactionContextCode' : ?1 }")
	public Page<TouchpointEvent> findMostRecentTouchpointByUserIdAndType(String userId, String type, Pageable pageable);

}
