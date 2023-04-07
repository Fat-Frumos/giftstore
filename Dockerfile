# Build stage
FROM maven:3.8.2-jdk-8 AS build
COPY . .
RUN mvn clean package -DskipTests -Dmaven

# Run stage
FROM openjdk:8-jdk-slim
COPY --from=build ./api/target/api-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]