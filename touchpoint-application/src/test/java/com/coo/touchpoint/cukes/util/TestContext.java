package com.coo.touchpoint.cukes.util;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.coo.touchpoint.domain.LoginRequest;
import com.coo.touchpoint.domain.TouchpointEvent;
import com.coo.touchpoint.domain.TouchpointEventType;

import lombok.Data;

@Data
@Component
public class TestContext {

	private TouchpointEvent event;
	private TouchpointEventType eventType;
	private ResponseEntity<TouchpointEvent> responseEntity;
	private ResponseEntity<TouchpointEvent[]> responseEntityList;
	private List<TouchpointEvent> eventList = null;
	private LoginRequest loginRequest = null;

}
