pipeline {
    agent any

    tools {
        maven 'Maven 3.6.3'
    }

    stages {
        stage('Checkout') {
            steps {
                // Faz checkout do código fonte do repositório Git
                git checkout master
            }
        }

        stage('Build') {
            steps {
                // Executa o comando Maven para compilar o projeto
                sh './mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                // Executa testes utilizando Maven
                sh './mvn test'
            }
        }
    }

    post {
        always {
            echo 'The pipeline has been completed.'
        }
    }
}
