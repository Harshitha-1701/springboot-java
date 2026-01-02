pipeline {
    agent any

    environment {
        IMAGE_NAME = "harshitha0117/springboot-java"
        IMAGE_TAG  = "${BUILD_NUMBER}"
        GITOPS_REPO = "https://github.com/Harshitha-1701/springboot-java-gitops.git"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh './mvnw clean package'

            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh '''
                      ./mvnw sonar:sonar \
                      -Dsonar.projectKey=springboot-java
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t $IMAGE_NAME:$IMAGE_TAG ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                      echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                      docker push $IMAGE_NAME:$IMAGE_TAG
                    '''
                }
            }
        }
        stage('Update GitOps Repo') {
            steps {
                withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN')]) {
                    sh '''
                      rm -rf gitops
                      git clone https://${GITHUB_TOKEN}@github.com/Harshitha-1701/springboot-java-gitops.git gitops
                      cd gitops

                      git config user.email "ci-bot@example.com"
                      git config user.name "jenkins-ci"

                      sed -i "s|image: .*|image: ${IMAGE_NAME}:${IMAGE_TAG}|" k8s/deployment.yaml

                      git add k8s/deployment.yaml
                      git commit -m "Update image tag to ${IMAGE_TAG}"
                      git push
                    '''
                }
            }
        }
    }
}
