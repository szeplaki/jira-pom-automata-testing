pipeline{
  agent any
  
  stages {
        stage("build"){
            steps{
                sh(script: "mvn compile")
            }
        }
        stage("run"){
            parallel{
                stage("chrome run"){
                    steps{
                        sh(script: "mvn clean test -Dusername=$username -$password=$password -DbaseUrl=$baseurl -Ddisplayname=$displayname -DBROWSER=CHROME")
                    }
                }
                stage("firefox run"){
                    steps{
                        sh(script: "mvn clean test -DUSER_NAME=$USER_NAME -DPASSWORD=$PASSWORD -DBASE_URL=$BASE_URL -Ddisplayname=$displayname -DBROWSER=FIREFOX")
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
