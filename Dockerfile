FROM maven:3.9.0-amazoncorretto-19 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM amazoncorretto:19
COPY --from=build /usr/src/app/target/word-crash-0.0.1-SNAPSHOT.jar /usr/app/run.jar
ENV SERVER_PORT=80
EXPOSE 80
ENTRYPOINT ["java","--enable-preview","-jar","/usr/app/run.jar"]