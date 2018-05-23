package com.coo.gis.cukes.util;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.coo.gis.domain.LoginRequest;
import com.coo.gis.domain.GisEvent;
import com.coo.gis.domain.GisEventType;

import lombok.Data;

@Data
@Component
public class TestContext {

	private GisEvent event;
	private GisEventType eventType;
	private ResponseEntity<GisEvent> responseEntity;
	private ResponseEntity<GisEvent[]> responseEntityList;
	private List<GisEvent> eventList = null;
	private LoginRequest loginRequest = null;

}
