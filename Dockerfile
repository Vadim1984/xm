# build
FROM maven as build
WORKDIR /usr/src/app
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:11
WORKDIR /usr/app
COPY --from=build /usr/src/app/target/recommendation-service-0.0.1-SNAPSHOT.jar ./recommendation-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "recommendation-service-0.0.1-SNAPSHOT.jar"]