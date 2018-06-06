package com.coo.gis.rest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Response;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.coo.gis.domain.GisInfo;
import com.coo.gis.service.GisService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(GisResource.class)
public class GisResourceTest {
	
	@Autowired
	private MockMvc mvc;
 
	@MockBean
	private GisService service;
 
    private ObjectMapper mapper = new ObjectMapper();
    
    @Test
	public void testCreateTouchpointEvent() throws Exception {
    	
    	GisInfo gisInfo = mockGisInfo(GisInfo.EDUCATION_NEWS);
    	
    	// given
		given(service.getEducationNews()).willReturn(gisInfo.getFeatureCollection());
    	
    	// when
		MockHttpServletResponse response = mvc.perform(get("/gis/educationnews")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		// then
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(200);
		
		FeatureCollection result = mapper.readValue(response.getContentAsString(), FeatureCollection.class);
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(gisInfo.getFeatureCollection());

	}
    
    private GisInfo mockGisInfo(String type) {
    	GisInfo gisInfo = new GisInfo();
	    gisInfo.setType(type);
	    Feature feature = new Feature();
	    feature.setGeometry(new Point(-180, -140));
	    FeatureCollection featureCollection = new FeatureCollection();
	    featureCollection.add(feature);
	    gisInfo.setFeatureCollection(featureCollection);
	    
	    return gisInfo;
    }
 

}
