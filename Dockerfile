# Build stage
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests -Dmaven.compiler.target=11

# Run stage
FROM openjdk:11-jdk-slim
COPY --from=build ./api/target/api-0.0.1-SNAPSHOT.jar.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar", "&"]