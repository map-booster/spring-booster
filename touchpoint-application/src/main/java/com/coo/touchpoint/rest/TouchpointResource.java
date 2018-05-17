package com.coo.touchpoint.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coo.touchpoint.domain.LoginRequest;
import com.coo.touchpoint.domain.TouchpointEvent;
import com.coo.touchpoint.service.TouchpointService;

@RestController
@RequestMapping("/touchpointevents")
public class TouchpointResource {

	private TouchpointService touchpointService;

	@Autowired
	public TouchpointResource(TouchpointService touchpointService) {
		this.touchpointService = touchpointService;
	}

	@RequestMapping(value = "/login/latest", method = RequestMethod.POST)
	public List<TouchpointEvent> getLatestTouchpoint(@Valid @RequestBody LoginRequest loginRequest) {
		return touchpointService.getMostRecentLoginTouchpointEvent(loginRequest.getUserId());
	}

	@RequestMapping(method = RequestMethod.POST)
	public TouchpointEvent createTouchpointEvent(@Valid @RequestBody TouchpointEvent touchpointEvent) {
		return touchpointService.createTouchpointEvent(touchpointEvent);
	}

	@RequestMapping(value = "/purge/all", method = RequestMethod.DELETE)
	public void purgeAll() {
		touchpointService.deleteAllTouchpoints();
	}

}
