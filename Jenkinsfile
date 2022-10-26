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
                    stage("With Chrome"){
                        steps{
                            sh(script: "mvn clean test -Dusername=$USERNAME -Dpassword=$PASSWORD -DbaseUrl='$BASEURL' -Ddisplayname='$DISPLAYNAME' -Dbrowser=chrome")
                        }
                    }
                    stage("With Firefox"){
                        steps{
                            sh(script: "mvn clean test -Dusername=$USERNAME -Dpassword=$PASSWORD -DbaseUrl='$BASEURL' -Ddisplayname='$DISPLAYNAME' -Dbrowser=firefox")
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