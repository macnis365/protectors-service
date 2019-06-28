# protectors-service

protectors-service is an REST application to manage super hero's and their missions.

    Technologies used are
    1. REST API is created using Spring Boot framework to develop restful webservice, unit test.
    2. H2 as the embedded database Maven used to build, package and run application.
    3. Jenkins used to make developer life by continuous integration, Jenkinsfile and store it in source control (Github) along with the other code check-in, so that Jenkins can load it directly from SCM and execute the scripted stages. ,
    4. Docker to package up an application with all of the parts it needs, such as libraries and other dependencies, and ship it all out as one package.
    5.Swagger to visualize and interact with the API's resources.


List of useful commands

    build docker image via maven cmd
    ./mvnw install dockerfile:build

    to instantiate the container
    docker run -p 8083:8081 -t watchdog/protectors-service:latest

Prerequisites to run this application

    Java8
    Git
    Maven
    Docker