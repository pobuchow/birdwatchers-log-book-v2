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
        stage ('Docker Build') {
            steps {
                sh '''
                docker images -a
                docker build -t pobuchow/birdwatchers-log-book-v2 .
                docker images -a
                '''
            }
        }
        stage ('Deploy'){
            steps {
                sh '''
                docker run -d -p 8080:8080 pobuchow/birdwatchers-log-book-v2
                hostname -I | cut -d' ' -f1
                '''
            }
        }
    }
}
