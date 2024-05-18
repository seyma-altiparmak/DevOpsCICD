pipeline {
    agent any

    environment {
        PATH = "/mnt/c/Program Files/Gradle/gradle-8.7/bin:${env.PATH}"
        registry = "mertcihanbayir/devopscicd"
        registryCredentials = 'credentialid'
        dockerImage = 'devopscicd:latest'
    }

    stages {
        stage('Checkout gradle and java') {
            steps {
                sh 'gradle --version'
                sh 'java --version'
            }
        }
        stage('Check my project from github') {
            steps {
                git 'https://github.com/seyma-altiparmak/DevOpsCICD.git'
            }
        }
        stage('Building jar file') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }
        stage('Build Docker image') {
            steps {
                sh 'docker build -t mertcihanbayir/devopscicd:latest .'
            }
        }
        stage('Tag Docker Image') {
            steps {
                sh 'docker tag mertcihanbayir/devopscicd:latest mertcihanbayir/devopscicd:v1'
            }
        }
        stage('Login to Docker Hub') {
            steps {
                sh 'echo *** | docker login --username mertcihanbayir --password-stdin'
            }
        }
        stage('Push Docker Image to the hub') {
            steps {
                sh 'docker push mertcihanbayir/devopscicd:v1'
            }
        }
    }

    post {
        success {
            echo 'İşlem başarıyla tamamlandı. Mert & Şeyma'
        }
    }
}