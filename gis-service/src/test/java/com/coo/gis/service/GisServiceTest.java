package com.coo.gis.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringRunner;

import com.coo.gis.domain.GeoJsonFeature;
import com.coo.gis.domain.GeoJsonFeatureCollection;
import com.coo.gis.repository.SpatialRepository;

@RunWith(SpringRunner.class)
public class GisServiceTest {

	@MockBean
    private SpatialRepository repository;
	
    private SpatialService service;
    
    private List<GeoJsonFeature> features;
    
    @Before
    public void setUp() {
	    GeoJsonFeature feature = new GeoJsonFeature();
	    feature.setGeometry(new GeoJsonPoint(-180, -140));
	    features = new ArrayList<>();
	    features.add(feature);
	    
	    Mockito.when(repository.findAll())
          .thenReturn(features);
        
        service = new SpatialService(repository);
    }
 
    @Test
	public void testGetEducationNews() {
    	
    	GeoJsonFeatureCollection found = service.findAllSpatial();
    	
    	assertThat(found.getFeatures())
        .isEqualTo(features);
    }
}
