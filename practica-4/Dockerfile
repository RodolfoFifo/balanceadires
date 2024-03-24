FROM gradle:8.3-jdk17 AS BUILD
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:17-alpine

MAINTAINER Gesbien Nunez <20190416@ce.pucmm.edu.do>

ENV NOMBRE_APP = "mockup_web"
ENV NAME_DB =  practica2
ENV spring.datasource.username = 'sa'
ENV spring.datasource.password = 'sa'

VOLUME /tmp

EXPOSE 8080

COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]