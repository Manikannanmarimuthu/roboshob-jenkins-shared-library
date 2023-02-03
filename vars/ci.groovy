def call(){
    try{
    pipeline {

        agent {
            label 'Workstation'
        }

        stages {
            stage('Compile/Bild') {
                steps {
                    script {
                        common.compile()
                    }
                }
            }

            stage('Unit Tests') {
                steps {
                    script {
                        common.unittests()
                    }
                }
            }

            stage('quality Control') {
                environment{
                    SONAR_PASS = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                    SONAR_USER = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                }
                steps {
                   sh 'sonar-scanner -Dsonar.host.url=http://54.160.14.249:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=cart'
                }
            }

            stage('Upload code to centralized Place') {
                steps {
                    echo 'upload'
                }
            }

        }
    }
    }catch (Exception e){
        common.email("Failed")
    }
}
