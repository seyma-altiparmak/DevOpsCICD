pipeline {
    agent any

    environment {
        PATH = "/mnt/c/Program Files/Gradle/gradle-8.7/bin:${env.PATH}"
        registry = "mertcihanbayir/devopscicd"
        registryCredentials = 'credentialid'
        dockerImage = 'devopscicd:latest'
        version = "v${BUILD_NUMBER}"
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
                sh 'docker build -t ${registry}:latest .'
            }
        }
        stage('Tag Docker Image') {
            steps {
                sh 'docker tag ${registry}:latest ${registry}:${version}'
            }
        }
        stage('Login to Docker Hub') {
            steps {
                sh 'echo 74An855855. | docker login --username mertcihanbayir --password-stdin'
            }
        }
        stage('Push Docker Image to the hub') {
            steps {
                sh 'docker push ${registry}:${version}'
            }
        }
        stage('Update Kubernetes Deployment') {
            steps {
                script {
                    sh """
                    kubectl create configmap app-config --from-literal=VERSION=${version} --dry-run=client -o yaml | kubectl apply -f -
                    kubectl apply -f k8s/db-depl.yml
                    kubectl apply -f k8s/webapp.depl.yml
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'İşlem başarıyla tamamlandı. Mert & Şeyma'
        }
    }
}
