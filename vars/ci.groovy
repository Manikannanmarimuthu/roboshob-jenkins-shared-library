def call(){
    try{
        node('Workstation') {

            stage('Checkout') {
                cleanWs()
                git branch: 'main', url: "https://github.com/Manikannanmarimuthu/cart"
            }

            stage('Compile/Bild') {
                common.compile()
            }

            stage('Unit Tests') {
                common.unittests()
            }

            stage('Quality Control') {
                SONAR_PASS = sh (script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                SONAR_USER = sh (script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]) {
                    sh "sonar-scanner -Dsonar.host.url=http://54.160.14.249:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${SONAR_EXTRA_OPTS}"
                }
            }

            stage('Upload code to centralized Place') {
                echo 'upload'
            }
        }
    }catch (Exception e){
        common.email("Failed")
    }
}
