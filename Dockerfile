FROM amazoncorretto:11.0.7
COPY /build/libs/olxscraper-1.0-SNAPSHOT-all.jar /app.jar
ENV LANG en_US.UTF-8
CMD ["/usr/bin/java", "-jar", "/app.jar"]
