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
                sh '''
                cd main
                mvn clean package spring-boot:repackage -B -DskipTests
                '''
            }
        }
        stage ('Test') {
            steps {
                sh '''
                cd main
                mvn test
                '''
            }
        }
    }
}
