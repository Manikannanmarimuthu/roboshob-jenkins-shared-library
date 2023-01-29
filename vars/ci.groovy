def call(){

    pipeline {

        agent {
            label 'Workstation'
        }

        stages {
            stage('Compile/Bild') {
                steps {
                    script{
                       common.compile
                    }
                }
            }

            stage('Unit Tests') {
                steps {
                    echo 'Unit Tests'
                }
            }

            stage('qulqity Control') {
                steps {
                    echo 'Quqlity Control'
                }
            }

            stage('Upload code to centralized Place'){
                steps {
                    echo 'upload'
                }
            }

        }

    }

}