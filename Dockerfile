# Build stage
FROM maven:3.8.2-jdk-11 AS build
FROM tomcat:7-jre8
FROM openjdk:11-jdk-slim

RUN mvn clean package -DskipTests -Dmaven.compiler.target=11

COPY . .
COPY --from=build /api/target/gift.war certificate.war
COPY ./api/target/gift.war /usr/local/tomcat/webapps/

RUN echo $CLASSPATH

EXPOSE 8080
ENTRYPOINT ["java","-jar","certificate.war"]