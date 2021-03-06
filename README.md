# OlxScraper
Basic OLX scraping web application in Java &amp; Vert.x

# Technology stack
* Java
* Gradle
* Eclipse Vert.x
* Guice
* Lombok
* Log4j 2
* Jsoup
* JUnit

# How to run
Run `com.arkadiusz.michalak.olxscraper.ServerRunner.main` with JDK. Tested with Java 11.

Default port is 8080.

# How to run in container
`./gradlew clean build`

`docker build --tag olxscraper:latest .`

`docker run -d  -p 8080:8080 olxscraper:latest`


# Endpoint
`GET /offers/olx/:keyword`

Example response:
```json
{
    "offers": [
        {
            "id": "597687780",
            "name": "Very good item, high quality",
            "price": "49 900 zł"
        }
    ]
}
```
