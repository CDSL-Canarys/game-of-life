pipeline {
        agent any
        triggers {
                cron('0 * * * *')
        }
        stages {
                stage ('SourceCode') {
                        steps {
                                git url: 'https://github.com/niranjanakoni/spring-petclinic.git', branch: 'main'
                        }
                }
                stage ('Build the code') {
                        steps {
                                sh script: '/opt/maven/bin/mvn clean package'
                        }
                }
                stage ('Send Artifacts to Tomcat') {
                        steps {
                                sh script: 'sudo cp /home/user1/jenkins_root/workspace/Pet-Clinic-Pipeline/target/*.jar /opt/tomcat/webapps'
                        }
                }
                stage ('Reporting & Archiving') {
                        steps {
                                 junit testResults: 'target/surefire-reports/*.xml'
                        }
                }
        }
        post {
                success {
                        echo "Success"
                }
                unsuccessful {
                        echo "Failure"
                }
        }
}

