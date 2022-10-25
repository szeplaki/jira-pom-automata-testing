pipeline{
  agent any
  
  stages {
        stage("Build"){
            steps{
                sh(script: "mvn compile")
            }
        }
        stage("Run tests"){
            parallel{
                stage("With Chrome"){
                    steps{
                        sh(script: "mvn clean test -Dbrowsertype=chrome")
                    }
                }
                stage("With Firefox"){
                    steps{
                        sh(script: "mvn clean test -Dbrowsertype=firefox")
                    }
                }
            }

            post {
                always {
                    junit testResults: '**/target/surefire-reports/TEST-*.xml', skipPublishingChecks: true
                    cleanWs()
                }
            }
        }
    }
}
