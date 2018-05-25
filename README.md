## Overview

Spring Boot REST APIs for managing GeoJSON details.

---

## How do I use it?

### Prerequisites

Java 8

Apache Maven

### Build the application using Maven

`mvn clean install`

### Run the application

`java -jar target/gis-service-0.0.1-SNAPSHOT.jar`

### Alternative

`mvn spring-boot:run`

### Running the Tests

Unit tests will be executed during the `test` lifecycle phase and will run as part of any maven goal after `test`.

`mvn package`

### Access the application

To access the application, open the following link in your browser:

`http://localhost:8080`

## Data Sources

This application serves sample data from public sources:

- Populated places from [geojson.xyz](http://geojson.xyz/)
- Education news from [The GDELT Project](https://blog.gdeltproject.org/gdelt-geo-2-0-api-debuts/) ([Live data set](https://api.gdeltproject.org/api/v2/geo/geo?query=theme:education&format=geojson&mode=PointHeatMap))