package com.coo.gis.data;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.coo.gis.domain.DevApp;
import com.coo.gis.domain.DevAppAddress;
import com.coo.gis.domain.DevAppWard;
import com.coo.gis.domain.GeoJsonFeature;
import com.coo.gis.domain.GeoJsonFeatureCollection;
import com.coo.gis.domain.ObjectStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DevAppCSVReader {
	private static final String APPDEV_CSV_FILE_PATH = "portal_development_app_v.csv";
	private static final String APPDEV_ADDRESS_CSV_FILE_PATH = "portal_dev_app_address_v.csv";

	public List<DevApp> readDevAppCSVData() throws Exception {

		List<DevApp> devApps = new ArrayList<>();
		
		try (   Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(APPDEV_CSV_FILE_PATH).toURI()));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {

			for (CSVRecord csvRecord : csvParser) {
				// Accessing Values by Column Index
				DevApp devApp = new DevApp();
				devApp.setDevAppId(csvRecord.get(0).trim());
				devApp.setApplicationNumber(csvRecord.get(1).trim());

				// App Date
				DateFormat format = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
				Date date = format.parse(csvRecord.get(2).trim());
				devApp.setApplicationDate(date.getTime());

				// App Type
				Map<String, String> map = new HashMap<>();
				map.put("en", csvRecord.get(4).trim());
				map.put("fr", csvRecord.get(5).trim());
				
				devApp.setApplicationType(map);

				map = new HashMap<>();
				
				// App Desc
				map.put("en", csvRecord.get(6).trim());
				map.put("fr", csvRecord.get(7).trim());
				devApp.setApplicationBriefDesc(map);

				map = new HashMap<>();

				// App Status
				map.put("en", csvRecord.get(8).trim());
				map.put("fr", csvRecord.get(9).trim());
				devApp.setApplicationStatus(map);
				
				
				map = new HashMap<>();

				// Object Status
				ObjectStatus objectStatus = new ObjectStatus();
				objectStatus.setObjectStatusTypeId(csvRecord.get(10).trim());
				map.put("en", csvRecord.get(11).trim());
				map.put("fr", csvRecord.get(12).trim());
				objectStatus.setObjectCurrentStatus(map);
				date = format.parse(csvRecord.get(13).trim());
				objectStatus.setObjectCurrentStatusDate(date.getTime());
				devApp.setObjectStatus(objectStatus);
				
				map = new HashMap<>();

				// Ward
				DevAppWard ward = new DevAppWard();
				ward.setCommunityId(csvRecord.get(16).trim());

				map.put("en", csvRecord.get(17).trim());
				map.put("fr", csvRecord.get(18).trim());
				ward.setWardNumber(map);
				
				map = new HashMap<>();

				map.put("en", csvRecord.get(19).trim());
				map.put("fr", csvRecord.get(20).trim());
				
				ward.setWardName(map);
				
				ward.setCouncillorFirstName(csvRecord.get(21).trim());
				ward.setCouncillorLastName(csvRecord.get(22).trim());
				ward.setWardCouncillorEmail(csvRecord.get(24).trim());

				devApp.setDevAppWard(ward);

				// Planner
				devApp.setPlannerFirstName(csvRecord.get(25).trim());
				devApp.setPlannerLastName(csvRecord.get(26).trim());
				devApp.setPlannerPhone(csvRecord.get(28).trim());
				devApp.setPlannerEmail(csvRecord.get(29).trim());

				devApp.setSearchableText(csvRecord.get(30).trim());

				devApps.add(devApp);
			}
		}

		return devApps;

	}

	public List<DevApp> readAppDataAddress(List<DevApp> devApps) throws Exception {
		try (Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(APPDEV_ADDRESS_CSV_FILE_PATH).toURI()));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {

			for (CSVRecord csvRecord : csvParser) {

				for (DevApp devapp : devApps) {
					if (devapp.getDevAppId().equalsIgnoreCase(csvRecord.get(1).trim())) {
						DevAppAddress address = new DevAppAddress();
						address.setAddressReferenceId(csvRecord.get(0).trim());
						address.setAddressNumber(Integer.valueOf(csvRecord.get(2).trim()));
						address.setAddressQualifier(csvRecord.get(3).trim());
						address.setLegalUnit(csvRecord.get(4).trim());
						address.setRoadName(csvRecord.get(5).trim());
						address.setCardinalDirection(csvRecord.get(6).trim());
						address.setRoadType(csvRecord.get(7).trim());
						address.setMunicipality(csvRecord.get(8).trim());
						address.setAddressType(csvRecord.get(9).trim());
						address.setAddressLatitude(
								(csvRecord.get(10).trim() != null && !csvRecord.get(10).trim().equals(""))
										? Double.valueOf(csvRecord.get(10).trim())
										: new Double("0.0"));
						address.setAddressLongitude(
								(csvRecord.get(10).trim() != null && !csvRecord.get(11).trim().equals(""))
										? Double.valueOf(csvRecord.get(11).trim())
										: new Double("0.0"));
						address.setAddressNumberRoadName(csvRecord.get(12).trim());
						address.setParcelPinNumber(
								(csvRecord.get(13).trim() != null && !csvRecord.get(13).trim().equals(""))
										? Integer.valueOf(csvRecord.get(13).trim())
										: new Integer("0"));
						devapp.addDevAppAddress(address);
					}
				}

			}
		}

		return devApps;
	}

	public GeoJsonFeatureCollection generateGeoJson(List<DevApp> devApps) throws Exception {

		List<GeoJsonFeature> features = new ArrayList<>();

		for (DevApp devApp : devApps) {
			if (devApp.getDevAppAddress() != null) {
				for (DevAppAddress address : devApp.getDevAppAddress()) {
					GeoJsonFeature feature = new GeoJsonFeature();
					feature.setGeometry(new GeoJsonPoint(address.getAddressLongitude(), address.getAddressLatitude()));

					ObjectMapper mapper = new ObjectMapper();

					@SuppressWarnings("unchecked")
					Map<String, Object> devAppMap = mapper.convertValue(devApp, Map.class);
					devAppMap.remove("devAppAddress");

					@SuppressWarnings("unchecked")
					Map<String, Object> addressMap = mapper.convertValue(address, Map.class);
					devAppMap.put("devAppAddress", addressMap);

					feature.setProperties(devAppMap);
					features.add(feature);
					// break;
				}
				// break;
			}
		}

		GeoJsonFeatureCollection collection = new GeoJsonFeatureCollection(features);

		ObjectMapper mapper = new ObjectMapper();
		try (FileWriter fileWriter = new FileWriter(
				"target/devAppsGeoJsonData.json");) {
			fileWriter.write(mapper.writeValueAsString(collection.getFeatures()));
		}
		return collection;
	}

	public void writeToFile(List<DevApp> devApps) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		try (FileWriter fileWriter = new FileWriter(
				"target/devAppsData.json");) {
			fileWriter.write(mapper.writeValueAsString(devApps));
		}

	}

	public static void main(String[] args) throws Exception {
		System.out.println("Initialize data import");
		DevAppCSVReader reader = new DevAppCSVReader();
		List<DevApp> devApps = reader.readDevAppCSVData();
		devApps = reader.readAppDataAddress(devApps);
		reader.writeToFile(devApps);
		reader.generateGeoJson(devApps);
		System.out.println("Data import completed! Look out for output files in the target directory");
		
	}
}