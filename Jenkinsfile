pipeline {
    agent any

    tools {
        maven 'Maven 3.6.0'
        jdk 'jdk11'
    }

    stages {
        stage('Verify Branch') {
            steps {
                echo "$GIT_BRANCH"
            }
        }
        stage ('Build') {
            steps {
                sh 'mvn clean package spring-boot:repackage'
            }
        }
    }
}
