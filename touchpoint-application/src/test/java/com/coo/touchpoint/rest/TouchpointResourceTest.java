package com.coo.touchpoint.rest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.coo.touchpoint.domain.ExtensionDataElement;
import com.coo.touchpoint.domain.LoginRequest;
import com.coo.touchpoint.domain.PartyIdentification;
import com.coo.touchpoint.domain.TouchpointEvent;
import com.coo.touchpoint.domain.TouchpointEventType;
import com.coo.touchpoint.repository.TouchpointRepository;
import com.coo.touchpoint.service.TouchpointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@RunWith(MockitoJUnitRunner.class)
public class TouchpointResourceTest {

	private static final String USER1 = "user1";
	private static final String USER2 = "user2";
	private static final String BASE_PATH = "/touchpointevents";
	private static final String LATEST_LOGIN_EVENT_PATH = BASE_PATH + "/login/latest";

	private MockMvc mvc;

	private TouchpointResource resource;
	private TouchpointService service;

	@Mock
	private TouchpointRepository repository;

	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() {

		service = new TouchpointService(repository);
		resource = new TouchpointResource(service);
		// Initializes the JacksonTester
		JacksonTester.initFields(this, mapper);
		// MockMvc standalone approach
		mvc = MockMvcBuilders.standaloneSetup(resource).build();

	}

	@After
	public void tearDown() {
		Mockito.reset(repository);
	}

	/*
	 * LOGIN Event Tests
	 */

	// Create Touchpoint Event for login

	@Test
	public void testCreateTouchpointEvent() throws Exception {

		// given
		TouchpointEvent actual = mockTouchpointEvent(USER1, TouchpointEventType.LOGIN, "1234");
		given(repository.insert(Mockito.any(TouchpointEvent.class))).willReturn(actual);

		TouchpointEvent toSave = mockTouchpointEvent(USER1, TouchpointEventType.LOGIN, null);
		String body = mapper.writeValueAsString(toSave);

		// when
		MockHttpServletResponse response = mvc
				.perform(post(BASE_PATH).contentType(MediaType.APPLICATION_JSON).content(body)).andReturn()
				.getResponse();

		// then
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatus());

		TouchpointEvent result = mapper.readValue(response.getContentAsString(), TouchpointEvent.class);
		Assert.assertNotNull(result);
		Assert.assertEquals("1234", result.getId());

	}

	// UserId set to all -

	@Test
	public void testGetLatestTouchpointUserAllNoExistingEventsFromSingleUser() throws Exception {

		// given
		List<TouchpointEvent> touchpointEventList = mockTouchpointList(0, TouchpointEventType.LOGIN, true);
		Page<TouchpointEvent> page = mockPage(touchpointEventList);
		given(repository.findMostRecentTouchpointByType(Mockito.eq(TouchpointEventType.LOGIN.getDescription()),
				Mockito.any())).willReturn(page);

		LoginRequest request = new LoginRequest();
		request.setUserId("all");
		String body = mapper.writeValueAsString(request);

		// when
		MockHttpServletResponse response = mvc
				.perform(post(LATEST_LOGIN_EVENT_PATH).contentType(MediaType.APPLICATION_JSON).content(body))
				.andReturn().getResponse();

		// then
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatus());

		List<TouchpointEvent> results = jsonToTouchpointEventList(response.getContentAsString());
		Assert.assertNotNull(results);
		Assert.assertEquals(0, results.size());

	}

	@Test
	public void testGetLatestTouchpointUserAllOneExistingEventsFromSingleUser() throws Exception {

		// given
		List<TouchpointEvent> touchpointEventList = mockTouchpointList(1, TouchpointEventType.LOGIN, true);
		Page<TouchpointEvent> page = mockPage(touchpointEventList);
		given(repository.findMostRecentTouchpointByType(Mockito.eq(TouchpointEventType.LOGIN.getDescription()),
				Mockito.any())).willReturn(page);

		LoginRequest request = new LoginRequest();
		request.setUserId("all");
		String body = mapper.writeValueAsString(request);

		// when
		MockHttpServletResponse response = mvc
				.perform(post(LATEST_LOGIN_EVENT_PATH).contentType(MediaType.APPLICATION_JSON).content(body))
				.andReturn().getResponse();

		// then
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatus());

		List<TouchpointEvent> results = jsonToTouchpointEventList(response.getContentAsString());
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());

		Assert.assertEquals("1", results.get(0).getId());

	}

	@Test
	public void testGetLatestTouchpointUserAllMultipleExistingEventsFromSingleUser() throws Exception {

		// given
		List<TouchpointEvent> touchpointEventList = mockTouchpointList(2, TouchpointEventType.LOGIN, true);
		Page<TouchpointEvent> page = mockPage(touchpointEventList);
		given(repository.findMostRecentTouchpointByType(Mockito.eq(TouchpointEventType.LOGIN.getDescription()),
				Mockito.any())).willReturn(page);

		LoginRequest request = new LoginRequest();
		request.setUserId("all");
		String body = mapper.writeValueAsString(request);

		// when
		MockHttpServletResponse response = mvc
				.perform(post(LATEST_LOGIN_EVENT_PATH).contentType(MediaType.APPLICATION_JSON).content(body))
				.andReturn().getResponse();

		// then
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatus());

		List<TouchpointEvent> results = jsonToTouchpointEventList(response.getContentAsString());
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());

		Assert.assertEquals("1", results.get(0).getId());

	}

	// UserId set to user1

	@Test
	public void testGetLatestTouchpointUser1NoExistingEventsFromSingleUser() throws Exception {

		// given
		List<TouchpointEvent> touchpointEventList = mockTouchpointList(0, TouchpointEventType.LOGIN, true);
		Page<TouchpointEvent> page = mockPage(touchpointEventList);
		given(repository.findMostRecentTouchpointByUserIdAndType(Mockito.eq(USER1),
				Mockito.eq(TouchpointEventType.LOGIN.getDescription()), Mockito.any())).willReturn(page);

		LoginRequest request = new LoginRequest();
		request.setUserId(USER1);
		String body = mapper.writeValueAsString(request);

		// when
		MockHttpServletResponse response = mvc
				.perform(post(LATEST_LOGIN_EVENT_PATH).contentType(MediaType.APPLICATION_JSON).content(body))
				.andReturn().getResponse();

		// then
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatus());

		List<TouchpointEvent> results = jsonToTouchpointEventList(response.getContentAsString());
		Assert.assertNotNull(results);
		Assert.assertEquals(0, results.size());

	}

	@Test
	public void testGetLatestTouchpointUser1OneExistingEventsFromSingleUser() throws Exception {

		// given
		List<TouchpointEvent> touchpointEventList = mockTouchpointList(1, TouchpointEventType.LOGIN, true);
		Page<TouchpointEvent> page = mockPage(touchpointEventList);
		given(repository.findMostRecentTouchpointByUserIdAndType(Mockito.eq(USER1),
				Mockito.eq(TouchpointEventType.LOGIN.getDescription()), Mockito.any())).willReturn(page);

		LoginRequest request = new LoginRequest();
		request.setUserId(USER1);
		String body = mapper.writeValueAsString(request);

		// when
		MockHttpServletResponse response = mvc
				.perform(post(LATEST_LOGIN_EVENT_PATH).contentType(MediaType.APPLICATION_JSON).content(body))
				.andReturn().getResponse();

		// then
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatus());

		List<TouchpointEvent> results = jsonToTouchpointEventList(response.getContentAsString());
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());

		Assert.assertEquals("1", results.get(0).getId());

	}

	@Test
	public void testGetLatestTouchpointUser1MultipleExistingEventsFromSingleUser() throws Exception {

		// given
		List<TouchpointEvent> touchpointEventList = mockTouchpointList(2, TouchpointEventType.LOGIN, true);
		Page<TouchpointEvent> page = mockPage(touchpointEventList);
		given(repository.findMostRecentTouchpointByUserIdAndType(Mockito.eq(USER1),
				Mockito.eq(TouchpointEventType.LOGIN.getDescription()), Mockito.any())).willReturn(page);

		LoginRequest request = new LoginRequest();
		request.setUserId(USER1);
		String body = mapper.writeValueAsString(request);

		// when
		MockHttpServletResponse response = mvc
				.perform(post(LATEST_LOGIN_EVENT_PATH).contentType(MediaType.APPLICATION_JSON).content(body))
				.andReturn().getResponse();

		// then
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatus());

		List<TouchpointEvent> results = jsonToTouchpointEventList(response.getContentAsString());
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());

		Assert.assertEquals("1", results.get(0).getId());

	}

	@Test
	public void testGetLatestTouchpointUser1MultipleExistingEventsFromMultipleUser() throws Exception {

		// given
		List<TouchpointEvent> touchpointEventList = mockTouchpointList(2, TouchpointEventType.LOGIN, false);
		Page<TouchpointEvent> page = mockPage(touchpointEventList);
		given(repository.findMostRecentTouchpointByUserIdAndType(Mockito.eq(USER1),
				Mockito.eq(TouchpointEventType.LOGIN.getDescription()), Mockito.any())).willReturn(page);

		LoginRequest request = new LoginRequest();
		request.setUserId(USER1);
		String body = mapper.writeValueAsString(request);

		// when
		MockHttpServletResponse response = mvc
				.perform(post(LATEST_LOGIN_EVENT_PATH).contentType(MediaType.APPLICATION_JSON).content(body))
				.andReturn().getResponse();

		// then
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatus());

		List<TouchpointEvent> results = jsonToTouchpointEventList(response.getContentAsString());
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());

		Assert.assertEquals("1", results.get(0).getId());

	}

	@Test
	public void testGetLatestTouchpointUser2MultipleExistingEventsFromMultipleUser() throws Exception {

		// given
		List<TouchpointEvent> touchpointEventList = mockTouchpointList(3, TouchpointEventType.LOGIN, true);
		Page<TouchpointEvent> page = mockPage(touchpointEventList);
		given(repository.findMostRecentTouchpointByUserIdAndType(Mockito.eq(USER2),
				Mockito.eq(TouchpointEventType.LOGIN.getDescription()), Mockito.any())).willReturn(page);

		LoginRequest request = new LoginRequest();
		request.setUserId(USER2);
		String body = mapper.writeValueAsString(request);

		// when
		MockHttpServletResponse response = mvc
				.perform(post(LATEST_LOGIN_EVENT_PATH).contentType(MediaType.APPLICATION_JSON).content(body))
				.andReturn().getResponse();

		// then
		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatus());

		List<TouchpointEvent> results = jsonToTouchpointEventList(response.getContentAsString());
		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());

		Assert.assertEquals("1", results.get(0).getId());

	}

	private List<TouchpointEvent> jsonToTouchpointEventList(String json) throws Exception {

		TypeFactory typeFactory = mapper.getTypeFactory();
		return mapper.readValue(json, typeFactory.constructCollectionType(List.class, TouchpointEvent.class));

	}

	private Page<TouchpointEvent> mockPage(List<TouchpointEvent> touchpointList) {

		return new Page<TouchpointEvent>() {

			@Override
			public List<TouchpointEvent> getContent() {
				return touchpointList;
			}

			@Override
			public int getNumber() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getNumberOfElements() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean hasContent() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFirst() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isLast() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Pageable nextPageable() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Pageable previousPageable() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Iterator<TouchpointEvent> iterator() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getTotalElements() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getTotalPages() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public <U> Page<U> map(Function<? super TouchpointEvent, ? extends U> arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};

	}

	private List<TouchpointEvent> mockTouchpointList(int scenario, TouchpointEventType type, boolean singleId) {

		List<TouchpointEvent> touchpointList = new ArrayList<>();

		switch (scenario) {
		case 1:
			touchpointList.add(mockTouchpointEvent("user1", type, "1"));
			break;
		case 2:
			touchpointList.add(mockTouchpointEvent("user1", type, "1"));
			touchpointList.add(mockTouchpointEvent((singleId) ? "user1" : "user2", type, "2"));
			break;
		case 3:
			touchpointList.add(mockTouchpointEvent("user2", type, "1"));
			break;
		default:
			break;
		}

		return touchpointList;

	}

	private TouchpointEvent mockTouchpointEvent(String userId, TouchpointEventType type, String id) {

		TouchpointEvent touchpointevent = new TouchpointEvent();

		Date date = Calendar.getInstance().getTime();

		touchpointevent.setId(id);

		touchpointevent.setEventEndTimestamp(date);
		touchpointevent.setEventStartTimestamp(date);

		touchpointevent.setSourceSystemCode("BCMBRSVC");
		touchpointevent.setTouchpointTransactionContextCode(type.getDescription());
		touchpointevent.setTransactionSuccessIndicator("Y");

		List<PartyIdentification> partIdList = new ArrayList<PartyIdentification>();

		List<ExtensionDataElement> extnDataElementList = new ArrayList<ExtensionDataElement>();

		PartyIdentification partyIdentification = new PartyIdentification();

		partyIdentification.setIdentityName("userId");
		partyIdentification.setIdentityValue(userId);

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

}

// * @Test public void
// * testGetAllTouchpointEventsByMemberIdMultipleEventsAndUsers() throws
// Exception
// * {
// *
// * // given
// * given(repository.findByType("LOGIN")).willReturn(mockTouchpointList(2,
// * TouchpointEventType.LOGIN, false));
// *
// * // when MockHttpServletResponse response =
// * mvc.perform(get("/touchpoint/events/login")).andReturn().getResponse();
// *
// * // then Assert.assertNotNull(response); Assert.assertEquals(200,
// * response.getStatus());
// *
// * TypeFactory typeFactory = mapper.getTypeFactory(); List<TouchpointEvent>
// * eventList = mapper.readValue(response.getContentAsString(),
// * typeFactory.constructCollectionType(List.class, TouchpointEvent.class));
// *
// * Assert.assertNotNull(eventList); Assert.assertEquals(2, eventList.size());
// *
// * Assert.assertEquals("1", eventList.get(0).getId());
// * Assert.assertEquals("user1", eventList.get(0).getMemberId());
// *
// * Assert.assertEquals("2", eventList.get(1).getId());
// * Assert.assertEquals("user2", eventList.get(1).getMemberId());
// *
// *
// * }
// *
// * @Test public void
// * testGetAllTouchpointEventsByMemberIdMultipleEventsAndSingleUser() throws
// * Exception {
// *
// * // given
// * given(repository.findByType("LOGIN")).willReturn(mockTouchpointList(2,
// * TouchpointEventType.LOGIN, true));
// *
// * // when MockHttpServletResponse response =
// * mvc.perform(get("/touchpoint/events/login")).andReturn().getResponse();
// *
// * // then Assert.assertNotNull(response); Assert.assertEquals(200,
// * response.getStatus());
// *
// * TypeFactory typeFactory = mapper.getTypeFactory(); List<TouchpointEvent>
// * eventList = mapper.readValue(response.getContentAsString(),
// * typeFactory.constructCollectionType(List.class, TouchpointEvent.class));
// *
// * Assert.assertNotNull(eventList); Assert.assertEquals(2, eventList.size());
// *
// * Assert.assertEquals("1", eventList.get(0).getId());
// * Assert.assertEquals("user1", eventList.get(0).getMemberId());
// *
// * Assert.assertEquals("2", eventList.get(1).getId());
// * Assert.assertEquals("user1", eventList.get(1).getMemberId());
// *
// *
// * }
// *
// * @Test public void
// *
// testGetAllTouchpointEventsByMemberIdMultipleEventsAndSingleUserQueryForUser()
// * throws Exception {
// *
// * // given given(repository.findByMemberIdAndType("user1",
// * "LOGIN")).willReturn(mockTouchpointList(2, TouchpointEventType.LOGIN,
// true));
// *
// * // when MockHttpServletResponse response =
// * mvc.perform(get("/touchpoint/events/login").param("id",
// * "user1")).andReturn().getResponse();
// *
// * // then Assert.assertNotNull(response); Assert.assertEquals(200,
// * response.getStatus());
// *
// * TypeFactory typeFactory = mapper.getTypeFactory(); List<TouchpointEvent>
// * eventList = mapper.readValue(response.getContentAsString(),
// * typeFactory.constructCollectionType(List.class, TouchpointEvent.class));
// *
// * Assert.assertNotNull(eventList); Assert.assertEquals(2, eventList.size());
// *
// * Assert.assertEquals("1", eventList.get(0).getId());
// * Assert.assertEquals("user1", eventList.get(0).getMemberId());
// *
// * Assert.assertEquals("2", eventList.get(1).getId());
// * Assert.assertEquals("user1", eventList.get(1).getMemberId());
// *
// *
// * }
// *
// * @Test public void
// * testGetAllTouchpointEventsByMemberIdSingleEventAndSingleUserQueryForUser()
// * throws Exception {
// *
// * // given given(repository.findByMemberIdAndType("user1",
// * "LOGIN")).willReturn(mockTouchpointList(1, TouchpointEventType.LOGIN,
// true));
// *
// * // when MockHttpServletResponse response =
// * mvc.perform(get("/touchpoint/events/login").param("id",
// * "user1")).andReturn().getResponse();
// *
// * // then Assert.assertNotNull(response); Assert.assertEquals(200,
// * response.getStatus());
// *
// * TypeFactory typeFactory = mapper.getTypeFactory(); List<TouchpointEvent>
// * eventList = mapper.readValue(response.getContentAsString(),
// * typeFactory.constructCollectionType(List.class, TouchpointEvent.class));
// *
// * Assert.assertNotNull(eventList); Assert.assertEquals(1, eventList.size());
// *
// * Assert.assertEquals("1", eventList.get(0).getId());
// * Assert.assertEquals("user1", eventList.get(0).getMemberId());
// *
// * }
// *
// * @Test public void
// * testGetAllTouchpointEventsByMemberIdNoEventAndSingleUserQueryForUser()
// throws
// * Exception {
// *
// * // given given(repository.findByMemberIdAndType("user1",
// * "LOGIN")).willReturn(mockTouchpointList(1, TouchpointEventType.LOGIN,
// true));
// *
// * // when MockHttpServletResponse response =
// * mvc.perform(get("/touchpoint/events/login").param("id",
// * "user2")).andReturn().getResponse();
// *
// * // then Assert.assertNotNull(response); Assert.assertEquals(200,
// * response.getStatus());
// *
// * TypeFactory typeFactory = mapper.getTypeFactory(); List<TouchpointEvent>
// * eventList = mapper.readValue(response.getContentAsString(),
// * typeFactory.constructCollectionType(List.class, TouchpointEvent.class));
// *
// * Assert.assertNotNull(eventList); Assert.assertEquals(0, eventList.size());
// *
// * }
// *
// *
// * }
