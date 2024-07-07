FROM openjdk:22
LABEL authors="michelevascellari"

ENTRYPOINT ["top", "-b"]

WORKDIR /app

COPY src/main/resources/application.properties /app/
COPY build/libs/betfair-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "betfair-0.0.1-SNAPSHOT.jar"]