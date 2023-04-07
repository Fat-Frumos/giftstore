# Build stage
FROM maven:3.8.3-jdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests -Dmaven.compiler.source=11 -Dmaven.compiler.target=11

# Run stage
FROM openjdk:11-jdk-slim
COPY --from=build ./api/target/api-0.0.1-SNAPSHOT.jar certificate.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","certificate.jar"]