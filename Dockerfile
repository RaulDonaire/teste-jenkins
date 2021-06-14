#
# Build stage
#
# FROM maven:3.6.0-jdk-11-slim AS build
# COPY src /home/app/src
# COPY pom.xml /home/app
# RUN mvn -f /home/app/pom.xml clean package

FROM adoptopenjdk/openjdk11:alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#
# Package stage
#
# FROM openjdk:11-jre-slim
# COPY --from=build /home/app/target/*.jar /usr/local/lib/demo.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]