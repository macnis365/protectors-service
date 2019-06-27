FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} protectors-service.jar
ENTRYPOINT ["java","-jar","/protectors-service.jar"]