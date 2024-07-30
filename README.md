# GitHub Repository Scoring Service
This README describes the GitHub Repository Scoring Service, which provides functionality for GitHub's Repositories scoring.
The objective of this service is to implement the scoring of the GitHub repositories using the <a href="https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-repositories">GitHub repositories search API</a>.

This service provides an API to search the GitHub's **popular** repositories.

## Popularity Scoring
This service provides the API to search **popular** GitHub repositories. 
The **popularity** scoring is assigned based on the factors of **stars** count, **forks** count and **updated** date time of the GitHub repository.

In order to return the popular repositories, the GitHub repositories are ordered by **stars** count, **forks** count and **updated** date time.
 
# Getting Started

### Requirements
For building and running the application you need:
* [JDK 21 or above](https://www.oracle.com/de/java/technologies/downloads/#java21)
* [Maven 3.9.8](https://maven.apache.org/download.cgi)

### Building the application
To build the application switch to project directory and use the following maven command.

```
cd github-repository-scoring-service
mvn clean install
```
#### Using maven wrapper to build
```
cd github-repository-scoring-service
./mvnw clean install
```

### Tests and Code Coverage Reports
The application contains Unit and Integration tests. The JaCoCo code coverage is used to generate the test coverage report.
After building the application using `mvn clean install` you can see the aggregated coverage report in the project target folder at `target/site/jacoco-aggregate/index.html`.

### Running the application locally
There are several ways to run a Spring Boot application on your local machine. 
One way is to execute the main method in the `com.redcare.pharmacy.github_repository_scoring_service.GithubRepositoryScoringServiceApplication` class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:
```
mvn spring-boot:run
```

#### Using maven wrapper to run
```
./mvnw spring-boot:run
```

### Authentication
Simple Basic authentication was configured with default `USER` role. 
When the API is accessed the Basic authentication form will appear use username as `redcare` and `passwrod` as `pharmacy` to login and access the API.

### Resources and Links 
The following references help you to know the service API resources and links:

* [Swagger-UI](http://localhost:8080/api/github-repository-scoring-service/swagger-ui/index.html)
* [Actuator](http://localhost:8080/api/github-repository-scoring-service/actuator)

