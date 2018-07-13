package com.coo.gis.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.coo.gis.domain.DevApp;
import com.coo.gis.domain.DevAppAddress;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@DataMongoTest
public class DevAppRepositoryTest {

	@Autowired
	private DevAppRepository repository;
	
	@Test
	public void testFindByType() {
	    // given
	    DevApp devApp = new DevApp();
	    devApp.setDevAppId("__01KQKS");
	    devApp.setApplicationDate(new Date().getTime());
	    devApp.setApplicationNumber("D02-02-16-0052");
	    Map<String, String> valueMap = new HashMap<>();
	    valueMap.put("en", "Zoning By-law Amendment");
	    valueMap.put("fr", "Modification au R�glement de zonage");
	    devApp.setApplicationType(valueMap);
	    DevAppAddress devAppAddress = new DevAppAddress();
	    devAppAddress.setAddressReferenceId("__000CCB");
	    devAppAddress.setAddressNumber(100);
	    devAppAddress.setRoadName("NORTEL");
	    devAppAddress.setRoadType("Drive");
	    List<DevAppAddress> list = new ArrayList<>();
	    list.add(devAppAddress);
	    devApp.setDevAppAddress(list);
	    
	    ObjectMapper mapper = new ObjectMapper();
	    
	    try {
			System.out.println("Value is: " + mapper.writeValueAsString(devApp));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    repository.insert(devApp);
	    
	    // when
	    List<DevApp> devApps = repository.findByApplicationNumber(devApp.getApplicationNumber());
	 
	    DevApp found = null;
	    if (devApps != null && devApps.size() > 0) {
			found = devApps.get(0);
		}
	    
	    // then
	    assertThat(found.getApplicationNumber())
	      .isEqualTo(devApp.getApplicationNumber());
	}
}
