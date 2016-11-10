## Synopsis

RESTful ticketing service to support a generic stadium and venue.

## Code Example

The service provides end points for the required functionality.
["http://localhost:8090/findOpenSeatCount"
,"http://localhost:8090/showSeatStatus/1"
,"http://localhost:8090/holdBestSeats/25/zorrion2000@yahoo.com"
,"http://localhost:8090/reserveHeldSeats/0/zorrion2000@yahoo.com"]

GET the root url for this same list.

## Motivation

The project was created to support a generic stadium ticketing system to match seats to transactions during
a sales process.

## Installation

The project is build entirely with Maven and Spring Boot blank archetypes.
cd to the pom.xml root location of the project
In the case of the IDE this was built with. It was at:
C:\Users\Jason\Documents\NetBeansProjects\ticketing-system\spring-boot-blank>
Execute the following from the command line:
java -jar target/spring-boot-ticket-system-1.0.7-RELEASE.jar

## Tests

junit tests are provided.
cd to the pom.xml root location of the project
Execute mvn test from the command line or IDE to verify.

## Contributors
https://github.com/zorrion

