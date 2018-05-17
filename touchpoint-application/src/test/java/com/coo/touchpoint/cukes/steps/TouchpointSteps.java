package com.coo.touchpoint.cukes.steps;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import com.coo.touchpoint.cukes.CucumberRoot;
import com.coo.touchpoint.domain.ExtensionDataElement;
import com.coo.touchpoint.domain.LoginRequest;
import com.coo.touchpoint.domain.PartyIdentification;
import com.coo.touchpoint.domain.TouchpointEvent;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TouchpointSteps extends CucumberRoot {

	private static final String USER_1 = "user1";
	private static final String POST_TOUCHPOINT_EVENT_CREATE_URL = "/touchpointevents";
	private static final String POST_TOUCHPOINT_EVENT_LOGIN_LATEST_URL = "/touchpointevents/login/latest";
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSSZ")
	private Date LatestDateTimestamp;
	
	@Before
	public void setUp() {
		 LoginRequest loginRequest = new LoginRequest();
		   loginRequest.setUserId("BATU102791299");
		context.setLoginRequest(loginRequest);
	}
	
	@After
	public void tearDown() {
		context.setEvent(null);
		context.setResponseEntity(null);
		context.setEventList(null);
	}
	

@Given("^a valid touchpoint login event request$")
public void a_valid_touchpoint_login_event_request() {
   context.setEvent(mockTouchpointEventData());
   LoginRequest loginRequest = new LoginRequest();
   loginRequest.setUserId("BATU102791299");
	//queryRequest.setScenario("login");
	context.setLoginRequest(loginRequest);
   
}

@When("^the event is POSTed to the endpoint /touchpointevents$")
public void the_event_is_POSTed_to_the_endpoint_touchpointevents() {
	// post the touchpoint
			ResponseEntity<TouchpointEvent> responseEntity = template.postForEntity(POST_TOUCHPOINT_EVENT_CREATE_URL,
					context.getEvent(), TouchpointEvent.class);

			// validate call successful
			Assert.assertEquals(200, responseEntity.getStatusCodeValue());
			// save response
			context.setResponseEntity(responseEntity);
  
}

@Then("^a touch point event will be persisted with login event details$")
public void a_touch_point_event_will_be_persisted_with_login_event_details() {
	TouchpointEvent touchpointEvent = context.getResponseEntity().getBody();
	Assert.assertNotNull(touchpointEvent);
	Assert.assertNotNull(touchpointEvent.getId());
	context.setResponseEntity(null);
}


	@Given("^(no|one|multiple|) previously saved login touchpoint event$")
	public void no_previously_saved_login_touchpoint_event(String scenario) throws URISyntaxException {

		if ("no".equals(scenario)) {
			context.setEventList(mockTouchpointEventDataList(0));
			Assert.assertTrue(context.getEventList().isEmpty());
		}
		if ("one".equals(scenario)) {
			context.setEventList(mockTouchpointEventDataList(1));
			createMockEvents(context.getEventList());
		}
		if ("multiple".equals(scenario)) {
			context.setEventList(mockTouchpointEventDataList(5));
			createMockEvents(context.getEventList());
		
		}

	}

	@When("^the userId is posted to the endpoint /touchpointevents/login$")
	public void the_userId_is_posted_to_the_endpoint_touchpointevents_login() throws URISyntaxException {
		//did not work at the @Before, so keeping it here
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserId("BATU102791299");
		context.setLoginRequest(loginRequest);
		ResponseEntity<TouchpointEvent[]> responseEntity = template.postForEntity(
				POST_TOUCHPOINT_EVENT_LOGIN_LATEST_URL, context.getLoginRequest(), TouchpointEvent[].class);
		// validate call successful
		Assert.assertEquals(200, responseEntity.getStatusCodeValue());
		// save response
		context.setResponseEntityList(responseEntity);
	}

	@Then("^no latest login touchpoint event is returned$")
	public void no_latest_login_touchpoint_event_is_returned() {

		ResponseEntity<TouchpointEvent[]> responseEntity = template.postForEntity(
				POST_TOUCHPOINT_EVENT_LOGIN_LATEST_URL, context.getLoginRequest(), TouchpointEvent[].class);
		
		// Assert ID was set on response
		TouchpointEvent[] responseEvent = responseEntity.getBody();
		Assert.assertTrue(responseEvent.length == 0);
	}

	@Then("^the latest login touchpoint event is returned$")
	public void the_latest_login_touchpoint_event_is_returned() throws URISyntaxException {
		ResponseEntity<TouchpointEvent[]> responseEntity = template.postForEntity(
				POST_TOUCHPOINT_EVENT_LOGIN_LATEST_URL, context.getLoginRequest(), TouchpointEvent[].class);
		// validate call successful
		Assert.assertEquals(200, responseEntity.getStatusCodeValue());
		// validate latest event is returned by comparing the lastest Date that was
		// stored while creating
		TouchpointEvent[] responseEvent = context.getResponseEntityList().getBody();
		Assert.assertEquals(LatestDateTimestamp, responseEvent[0].getEventStartTimestamp());

	}

	private void createMockEvents(List<TouchpointEvent> eventList) throws URISyntaxException {

		Iterator<TouchpointEvent> itr = eventList.iterator();
		while (itr.hasNext()) {

			TouchpointEvent touchpointEvent =(TouchpointEvent) itr.next();
			ResponseEntity<TouchpointEvent> responseEntity = template.postForEntity(POST_TOUCHPOINT_EVENT_CREATE_URL,
					touchpointEvent, TouchpointEvent.class);

			// validate call successful

			Assert.assertEquals(200, responseEntity.getStatusCodeValue());
		}

	}

	private List<TouchpointEvent> mockTouchpointEventDataList(int ii) {

		List<TouchpointEvent> mockDataList = new ArrayList<>();

		if (ii == 0)
			return mockDataList;

		for (int i = 0; i < ii; i++) {
			mockDataList.add(mockTouchpointEventData());
		}
		// set the latest event date for assertion
		LatestDateTimestamp = mockDataList.get(mockDataList.size() - 1).getEventStartTimestamp();
		return mockDataList;
	}

	private TouchpointEvent mockTouchpointEventData() {

		TouchpointEvent touchpointevent = new TouchpointEvent();
		touchpointevent.setEventEndTimestamp(getDate());
		touchpointevent.setEventStartTimestamp(getDate());
		touchpointevent.setSourceSystemCode("BCMBRSVC");
		touchpointevent.setTouchpointTransactionContextCode("cooMbrPrtlLgn");
		touchpointevent.setTransactionSuccessIndicator("Y");

		List<PartyIdentification> partIdList = new ArrayList<PartyIdentification>();

		List<ExtensionDataElement> extnDataElementList = new ArrayList<ExtensionDataElement>();

		PartyIdentification partyIdentification = new PartyIdentification();

		partyIdentification.setIdentityName("userId");
		partyIdentification.setIdentityValue("BATU102791299");

		partIdList.add(partyIdentification);
		partyIdentification = new PartyIdentification();
		partyIdentification.setIdentityName("ruid");
		partyIdentification.setIdentityValue("FCsMrsfTtxJP2iisl8qdwdOc0r4=");

		partIdList.add(partyIdentification);

		touchpointevent.setPartyIdentification(partIdList);

		ExtensionDataElement extDataElement = new ExtensionDataElement();
		extDataElement.setName("impersonateIndicator");
		extDataElement.setValue("N");
		extnDataElementList.add(extDataElement);

		touchpointevent.setExtensionDataElement(extnDataElementList);

		return touchpointevent;
	}

	private Date getDate() {
		//Introduce 1 sec delay before inserting next record
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime();

	}

}
