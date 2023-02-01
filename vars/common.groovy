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
        sh 'npm test'
       // sh 'echo test cases'
    }

    if(app_lang=="maven"){
        sh 'mvn test'
    }

    if(app_lang=="python"){
        sh 'python3 -m unitest'
    }
}

def email(email_note){
    mail bcc: '', body: 'TEST', cc: '', from: 'manikannanmarimuthu@gmail.com', replyTo: '', subject: 'Test From JENKINS', to: 'manikannanmarimuthu@gmail.com'
}