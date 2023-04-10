# Build stage
FROM maven:3.8.2-jdk-8 AS build
COPY . .
RUN mvn clean package -DskipTests -Dmaven -X

# Run stage
# FROM openjdk:8-jre-alpine
FROM openjdk:8-jdk-slim
COPY --from=build ./api/target/api-1.0.0.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]

# FROM maven:3.8.2-jdk-11 AS build
# COPY . .
# RUN mvn clean package -DskipTests -Dmaven

# FROM openjdk:11-jdk-slim
# COPY --from=build ./api/target/api-1.0.0.jar demo.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","demo.jar", "&"]