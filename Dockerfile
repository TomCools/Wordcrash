FROM maven:3.6.3-adoptopenjdk-14 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:14.0.1-slim
COPY --from=build /usr/src/app/target/word-crash-0.0.1-SNAPSHOT.jar /usr/app/run.jar
ENV SERVER_PORT=80
EXPOSE 80
ENTRYPOINT ["java","-jar","/usr/app/run.jar"]