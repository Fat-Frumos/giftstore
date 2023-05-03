# Build stage
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -X

# Run stage
FROM openjdk:17-jdk-slim-buster
# VOLUME /tmp
# ADD api-1.0.0.jar app.jar

COPY --from=build ./api/target/api-1.0.0.jar certificate.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","certificate.jar"]