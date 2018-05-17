package com.coo.touchpoint.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.coo.touchpoint.domain.TouchpointEvent;
import com.coo.touchpoint.domain.TouchpointEventType;
import com.coo.touchpoint.repository.TouchpointRepository;

@Component
public class TouchpointService {

	private static final String DEFAULT_USER_ID = "all";

	private TouchpointRepository repository;

	@Autowired
	public TouchpointService(TouchpointRepository repository) {
		this.repository = repository;
	}

	/**
	 * Creates and persists a {@link TouchpointEvent} into the configured data
	 * store.
	 * 
	 * @param loginInfo
	 * @return
	 */
	public TouchpointEvent createTouchpointEvent(TouchpointEvent touchpointEvent) {
		return repository.insert(touchpointEvent);
	}

	/**
	 * Returns a {@link List} of {@link TouchpointEvent} containing the
	 * {@link TouchpointEvent} with type 'login' with the most recent date for the
	 * given user. An empty {@link List} is returned if no {@link TouchpointEvent}
	 * found for the given user. If userId is set to 'all', the
	 * {@link TouchpointEvent} most recent date with the type 'login' will be
	 * returned.
	 * 
	 * @param userId
	 * @return
	 */
	public List<TouchpointEvent> getMostRecentLoginTouchpointEvent(String userId) {

		List<TouchpointEvent> touchpointEventList = new ArrayList<>();

		PageRequest request = PageRequest.of(0, 1, new Sort(Sort.Direction.DESC, "eventStartTimestamp"));
		Page<TouchpointEvent> page = null;

		if (DEFAULT_USER_ID.equals(userId)) {
			page = repository.findMostRecentTouchpointByType(TouchpointEventType.LOGIN.getDescription(), request);
		} else {
			page = repository.findMostRecentTouchpointByUserIdAndType(userId,
					TouchpointEventType.LOGIN.getDescription(), request);
		}

		if (null != page.getContent() && page.getContent().size() > 0) {
			touchpointEventList.add(page.getContent().get(0));
		}

		return touchpointEventList;

	}

	/*
	 * Dangerously removes all {@link TouchpointEvent} objects from the data store.
	 * TO BE REMOVED.
	 */
	public void deleteAllTouchpoints() {
		repository.deleteAll();
	}

}
