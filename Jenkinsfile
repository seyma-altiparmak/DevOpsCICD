pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('docker-hub-credentials')
        GITHUB_CREDENTIALS = credentials('github-credentials')
        IMAGE_NAME = 'mertcihanbayir/dockercicd:latest'
        DB_IMAGE = 'postgres:latest'
        GITHUB_REPO = 'github.com/seyma-altiparmak/DevOpsCICD.git'
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'GITHUB_CREDENTIALS', url: "https://${GITHUB_REPO}"
            }
        }

        stage('Create JAR') {
            steps {
                script {
                    sh './gradlew clean build'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${IMAGE_NAME}")
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'DOCKERHUB_CREDENTIALS') {
                        docker.image("${IMAGE_NAME}").push()
                    }
                }
            }
        }

        stage('Pull from DockerHub') {
            steps {
                script {
                    sh "docker pull ${IMAGE_NAME}"
                }
            }
        }

        stage('Run on Minikube') {
            steps {
                script {
                    sh 'minikube status || minikube start'
                    sh 'eval $(minikube -p minikube docker-env)'
                    sh """
                    kubectl create deployment webapp --image=${IMAGE_NAME} --dry-run=client -o yaml > webapp-deployment.yaml
                    kubectl create deployment db --image=${DB_IMAGE} --dry-run=client -o yaml > db-deployment.yaml
                    """
                    sh """
                    kubectl apply -f webapp-deployment.yaml
                    kubectl apply -f db-deployment.yaml
                    """
                    sh """
                    kubectl expose deployment webapp --type=NodePort --port=8080
                    kubectl expose deployment db --type=NodePort --port=5432
                    """
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
