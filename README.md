# protectors-service
REST application to manage Super hero's

http://localhost:8081/h2-console

http://localhost:8081/swagger-ui.html


docker commands

list running docker instances
    docker ps

stop docker instance by containerid
    docker stop e9894391b0e1

docker cmd to run the image(expose-port:internal-port)
    docker run -p 8083:8081 -t watchdog/protectors-service:latest


build docker image via maven cmd
./mvnw install dockerfile:build
