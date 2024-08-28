FROM maven:3-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY src src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/cms.jar .

CMD ["java", "-jar", "cms.jar"]
