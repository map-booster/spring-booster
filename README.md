## Overview

Spring Boot REST APIs for managing GeoJSON details.

## How do I use it?

### Prerequisites

- Java 8
- Apache Maven
- MongoDB

### Set up MongoDB locally

Please follow the instructions in the links below based on your OS

  - [Windows Setup](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/)
  - [Linux Setup](https://docs.mongodb.com/manual/administration/install-on-linux/)
  - [macOS Setup](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/)

Modify /etc/hosts file for Linux/macOS to add mongodb as an alias for 127.0.0.1

The updated file should look like as follows:

```
$ cat /etc/hosts

127.0.0.1		localhost.localdomain localhost mongodb
::1		localhost6.localdomain6 localhost6
```

For Windows, please refer to the Windows documentation to change the hosts file.

### Create mongo user

Connect to mongo:

```
mongo --host 127.0.0.1:27017
```

Create a user:

```
use devapps
db.createUser(
 {
   "user": "mongouser",
   "pwd": "mongopass",
   "roles": [
      { "role": "readWrite", "db": "devapps" }
   ]
 }
)
```

### Add a text index to spatial collection in devapps db

Run the following command to create a text index to search by searchable text:

```
use devapps
db.spatial.createIndex( { "properties.searchableText" : "text" } )
```
[Related documentation](https://docs.mongodb.com/manual/text-search/)

### Load sample devapps & geojson data into devapps db 

```
mongoimport --jsonArray --db devapps --collection spatial --drop --file sample-data/devAppsGeoJsonData.json

mongoimport --jsonArray --db devapps --collection devapp --drop --file sample-data/devAppsData.json
```

### Build the application using Maven

`mvn clean install`

### Run the application

Navigate to the `gis-service` directory, then run:

`java -jar target/gis-service-1.0.0-SNAPSHOT.jar`

#### Alternative

`mvn spring-boot:run`

### Running the Tests

Unit tests will be executed during the `test` lifecycle phase and will run as part of any maven goal after `test`.

`mvn package`

### Access the application

To access the application, open the following link in your browser:

`http://localhost:8080`

### Access the Springboot Actuator endpoints

To access the Springboot actuator endpoints, open the following link in your browser:

health 
`http://localhost:8081/actuator/health`

info
`http://localhost:8081/actuator/info`
