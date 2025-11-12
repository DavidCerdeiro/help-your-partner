pipeline{
    agent any
    tools{
        maven 'Maven'
    }
    stages{
        stage("build"){
            steps{
                script{
                    def route = findFiles(glob: 'backend/*/pom.xml')
                    def parallelSteps = [:]
                    route.each {
                        parallelSteps["build-${it.path}"] = {
                            dir(it.path.substring(0, it.path.lastIndexOf('/'))){
                                sh "mvn clean install -DskipTests"
                            }
                        }
                    }
                    parallel parallelSteps
                }
            }
        }
        stage("test"){
            steps{
                script{
                    def route = findFiles(glob: 'backend/*/pom.xml')
                    def parallelSteps = [:]
                    route.each {
                        parallelSteps["test-${it.path}"] = {
                            dir(it.path.substring(0, it.path.lastIndexOf('/'))){
                                sh "mvn test"
                            }
                        }
                    }
                    parallel parallelSteps
                }
            }
        }
    }
}