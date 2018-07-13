package com.coo.gis.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.context.junit4.SpringRunner;

import com.coo.gis.domain.GeoJsonFeature;

@RunWith(SpringRunner.class)
@DataMongoTest
public class SpatialRepositoryTest {
	
	@Autowired
	private SpatialRepository repository;
	
	
	public void testFindAllByText() {
	}
	
	public void testFindWithinDistance() {
		
	}
	
	@Test
	public void testFindAll() {
	    // given
	    GeoJsonFeature feature = new GeoJsonFeature();
	    feature.setGeometry(new GeoJsonPoint(-75, 45));
	    List<GeoJsonFeature> features = new ArrayList<>();
	    features.add(feature);
	    repository.insert(feature);
	    
	    // when
	    List<GeoJsonFeature> list = repository.findAll();
	 
	    GeoJsonFeature found = null;
	    if (list != null && list.size() > 0) {
			found = list.get(0);
		}
	    
	    // then
	    assertThat(found.getGeometry().getCoordinates())
	      .isEqualTo(feature.getGeometry().getCoordinates());
	    
	}
	

}
