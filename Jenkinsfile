pipeline{
    agent any
    tools{
        maven Maven
    }
    stages{
        stage("build"){
            steps{
                def route = findFiles(glob: 'backend/*/pom.xml')
                def parallelSteps = [:]
                route.each {
                    parallelSteps["build-${it.path}"] = {
                        dir(it.parent) {
                            sh "mvn clean install -DskipTests"
                        }
                    }
                }
                parallel parallelSteps
            }
        }
        stage("test"){
            steps{
                def route = findFiles(glob: 'backend/*/pom.xml')
                def parallelSteps = [:]
                route.each {
                    parallelSteps["test-${it.path}"] = {
                        dir(it.parent) {
                            sh "mvn test"
                        }
                    }
                }
                parallel parallelSteps
            }
        }
    }
}