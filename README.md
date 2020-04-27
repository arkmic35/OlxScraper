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

# Endpoint
`/offers/olx/:keyword`

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
