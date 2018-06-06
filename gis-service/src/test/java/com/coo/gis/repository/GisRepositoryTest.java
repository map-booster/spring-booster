package com.coo.gis.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.coo.gis.domain.GisInfo;

@RunWith(SpringRunner.class)
@DataMongoTest
public class GisRepositoryTest {

	@Autowired
	private GisRepository gisRepository;
	
	@Test
	public void testFindByType() {
	    // given
	    GisInfo gisInfo = new GisInfo();
	    gisInfo.setType(GisInfo.EDUCATION_NEWS);
	    Feature feature = new Feature();
	    feature.setGeometry(new Point(-180, -140));
	    FeatureCollection featureCollection = new FeatureCollection();
	    featureCollection.add(feature);
	    gisInfo.setFeatureCollection(featureCollection);
	    gisRepository.insert(gisInfo);
	    
	    // when
	    PageRequest request = PageRequest.of(0, 1);
	    List<GisInfo> list = gisRepository.findGisInfo(gisInfo.getType(), request);
	 
	    GisInfo found = null;
	    if (list != null && list.size() > 0) {
			found = list.get(0);
		}
	    
	    // then
	    assertThat(found.getFeatureCollection().getFeatures().get(0).getGeometry())
	      .isEqualTo(gisInfo.getFeatureCollection().getFeatures().get(0).getGeometry());
	    
	    assertThat(found.getType())
	      .isEqualTo(gisInfo.getType());
	}
}
