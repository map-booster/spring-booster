package com.coo.gis.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.coo.gis.domain.GisInfo;
import com.coo.gis.repository.GisRepository;

@RunWith(SpringRunner.class)
public class GisServiceTest {

	@MockBean
    private GisRepository repository;
	
    private GisService gisService;
    
    private GisInfo gisInfo;
    
    @Before
    public void setUp() {
    	gisInfo = new GisInfo();
	    gisInfo.setType(GisInfo.EDUCATION_NEWS);
	    Feature feature = new Feature();
	    feature.setGeometry(new Point(-180, -140));
	    FeatureCollection featureCollection = new FeatureCollection();
	    featureCollection.add(feature);
	    gisInfo.setFeatureCollection(featureCollection);
	    
	    List<GisInfo> list = new ArrayList<>();
	    list.add(gisInfo);
	    
	    PageRequest request = PageRequest.of(0, 1);
        Mockito.when(repository.findGisInfo(GisInfo.EDUCATION_NEWS, request))
          .thenReturn(list);
        
        gisService = new GisService(repository);
    }
 
    @Test
	public void testGetEducationNews() {
    	
    	FeatureCollection found = gisService.getEducationNews();
    	
    	assertThat(found)
        .isEqualTo(gisInfo.getFeatureCollection());
    }
}
