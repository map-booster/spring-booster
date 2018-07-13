package com.coo.gis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.coo.gis.domain.GeoJsonFeature;
import com.coo.gis.domain.GeoJsonFeatureCollection;
import com.coo.gis.repository.SpatialRepository;

public class SpatialServiceTest {
	
	@MockBean
	private SpatialRepository spatialRepository;
	
	private SpatialService spatialService;

	private GeoJsonFeatureCollection features;
	private  String searchText = "435 KANATA";
	
	
    @Before
    public void setUp() {
   
	    GeoJsonFeature feature = new GeoJsonFeature();
	    
	    feature.setId("1267095");
	    feature.setGeometry(new GeoJsonPoint(-180, -140));
	    
	    Map<String, Object> properties = new HashMap<String, Object>();
	    properties.put("OBJECTID", 1267095);
	    properties.put("ADDRESS_NUMBER_ROAD_NAME", "435 KANATA");
	    properties.put("APPLICATION_NUMBER", "D07-12-15-0115");
	    feature.setProperties(properties);

	    List<GeoJsonFeature> features = new ArrayList<>();
	    features.add(feature);
	    
	    PageRequest request = PageRequest.of(0, 1);
	    
        //Mockito.when(spatialRepository.findAllByText(searchText,request))
          //.thenReturn(features);
        
        spatialService = new SpatialService(spatialRepository);
    }
    
	@Ignore
    @Test
    public void testFindSpatialbyText() {
    	
    	//GeoJsonFeatureCollection foundCollection = spatialService.findSpatialbyText(searchText);
    	
    	//assertThat(foundCollection).isEqualTo(features);
    }

}
