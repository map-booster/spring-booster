package com.coo.gis.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import lombok.Data;

@Data
public class GeoJsonFeatureCollection implements Iterable<GeoJsonFeature> {

	private static final String TYPE = "FeatureCollection";
	
	private final List<GeoJsonFeature> features = new ArrayList<GeoJsonFeature>();

	/**
	 * Creates a new {@link GeoJsonFeatureCollection} for the given {@link GeoJson} instances.
	 * 
	 * @param features
	 */
	public GeoJsonFeatureCollection(List<GeoJsonFeature> features) {

		Assert.notNull(features, "Geometries must not be null!");

		this.features.addAll(features);
	}
	
	
	public GeoJsonFeatureCollection() {

	}

	public String getType() {
		return TYPE;
	}

	public Iterable<GeoJsonFeature> getFeatures() {
		return Collections.unmodifiableList(this.features);
	}

	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.features);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof GeoJsonFeatureCollection)) {
			return false;
		}

		GeoJsonFeatureCollection other = (GeoJsonFeatureCollection) obj;

		return ObjectUtils.nullSafeEquals(this.features, other.features);
	}

	@Override
	public Iterator<GeoJsonFeature> iterator() {
		// TODO Auto-generated method stub
		return features.iterator();
	}

}
