# build
FROM maven as build
WORKDIR /usr/src/app
COPY . .
RUN mvn clean install -DskipTests

FROM adoptopenjdk/openjdk11:latest
WORKDIR /usr/app
COPY --from=build /usr/src/app/target/*.jar ./springbootdemo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "springbootdemo-0.0.1-SNAPSHOT.jar"]