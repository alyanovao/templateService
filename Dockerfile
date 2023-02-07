FROM bellsoft/liberica-openjdk-alpine:17 as build
#Windows
ARG JAR_FILE=templateService-facade/target/*.jar
#Linux
#ARG JAR_FILE=./*.jar
COPY ${JAR_FILE} templateService.jar
EXPOSE 8100
ENTRYPOINT ["java", "-jar", "templateService.jar"]
