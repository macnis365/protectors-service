pipeline {
    agent any
    tools {
        maven 'M3'
    }
    stages {
        stage('Checkout'){
            steps{
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}