## Overview

Spring Boot REST APIs for managing GeoJSON details.

## How do I use it?

### Prerequisites

Java 8

Apache Maven

MongoDB

## Setup MongoDB locally

Please follow the instructions in the links below based on your OS

  Windows: https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/

  Linux based OS: https://docs.mongodb.com/manual/administration/install-on-linux/

Modify /etc/hosts file for Linux based OS to add mongodb as an alias for 127.0.0.1

The updated file should look like as follows

cat /etc/hosts
127.0.0.1		localhost.localdomain localhost mongodb
::1		localhost6.localdomain6 localhost6

For Windows, please refer to the Windows documentation to change the hosts file.

### Load sample GIS data into mongodb

#### Load education news geojson
curl -X POST -H "Content-Type:application/json" -d ./sample-data/educationnews.json http://localhost:8080/gis/

#### Load populated places geojson
curl -X POST -H "Content-Type:application/json" -d ./sample-data/populatedplaces.json http://localhost:8080/gis/

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
