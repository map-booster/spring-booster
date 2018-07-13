package com.coo.gis.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.coo.gis.domain.GeoJsonFeature;
import com.coo.gis.domain.GeoJsonFeatureCollection;
import com.coo.gis.domain.GisInfo;
import com.coo.gis.service.SpatialService;

@RunWith(SpringRunner.class)
@WebMvcTest(SpatialResource.class)
public class GisResourceTest {
	
	@Autowired
	private MockMvc mvc;
 
	@MockBean
	private SpatialService service;
 
    @Test
	public void testCreateTouchpointEvent() throws Exception {
    	
    	GeoJsonFeatureCollection featureCollection = mockGisInfo(GisInfo.EDUCATION_NEWS);
    	
    	// given
		given(service.findAllSpatial()).willReturn(featureCollection);
    	
    	// when
		MockHttpServletResponse response = mvc.perform(get("/spatial/features")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		// then
		assertThat(response).isNotNull();
		assertThat(response.getStatus()).isEqualTo(200);
		
	}
    
    private GeoJsonFeatureCollection mockGisInfo(String type) {
 	    GeoJsonFeature feature = new GeoJsonFeature();
	    feature.setGeometry(new GeoJsonPoint(-180, -140));
	    List<GeoJsonFeature> features = new ArrayList<>();
	    features.add(feature);
	    GeoJsonFeatureCollection featureCollection = new GeoJsonFeatureCollection(features);
	    
	    return featureCollection;
    }
 

}
