FROM maven:3.8.2-jdk-11 AS build

COPY zero/Module#03 /usr/src/app

RUN mvn clean package -DskipTests -X

#COPY --from=build ./web-app/target/web-app-1.0.0.jar certificate.jar

EXPOSE 8080

CMD ["java", "-jar", "web-app/target/web-app-1.0.0.war"]

#HEALTHCHECK --interval=30s --timeout=10s CMD curl --fail http://localhost:8080/health || exit 1
