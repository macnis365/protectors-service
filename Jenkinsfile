pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Staging') {
            sh 'pid=\$(lsof -i:8081 -t); kill -TERM \$pid || kill -KILL \$pid'
            withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                sh 'nohup ./mvnw spring-boot:run -Dserver.port=8081 &'
            }
        }
    }
}