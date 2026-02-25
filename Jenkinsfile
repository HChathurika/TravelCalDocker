pipeline {
  agent any

  tools {
    maven 'maven3'
  }

  environment {
    // Docker CLI path (Windows Jenkins)
    PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"

    DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
    DOCKERHUB_REPO = 'hathadura/travelcal'
    DOCKER_IMAGE_TAG = 'latest'
  }

  stages {

    stage('Checkout') {
      steps {
        // Always checkout main
        checkout([$class: 'GitSCM',
          branches: [[name: '*/main']],
          userRemoteConfigs: [[url: 'https://github.com/HChathurika/TravelCalDocker.git']]
        ])
      }
    }

    stage('Build') {
      steps {
        bat 'mvn -B clean package'
      }
    }

    stage('Test') {
      steps {
        bat 'mvn -B test'
      }
    }

    stage('JaCoCo Report') {
      steps {
        bat 'mvn -B jacoco:report'
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          // Build Docker image from Dockerfile in workspace
          docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}", ".")
        }
      }
    }

    stage('Push Docker Image to Docker Hub') {
      steps {
        script {
          docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
            docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
          }
        }
      }
    }
  }

  post {
    always {
      // Publish JUnit results even if tests fail
      junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

      // Publish JaCoCo coverage (needs JaCoCo Jenkins plugin installed)
      jacoco execPattern: '**/target/jacoco.exec',
             classPattern: '**/target/classes',
             sourcePattern: '**/src/main/java',
             inclusionPattern: '**/*.class'
    }
  }
}