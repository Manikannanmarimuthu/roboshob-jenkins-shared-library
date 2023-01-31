def compile(){
    if(app_lang == "nodejs"){
      sh 'npm install'
    }

    if(app_lang=="maven"){
        sh 'mvn package'
    }
}

def unittests(){
    if(app_lang == "nodejs"){
        //sh 'npm test'
        sh 'echo test cases'
    }

    if(app_lang=="maven"){
        sh 'mvn package'
    }
}