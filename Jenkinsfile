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
                            dir(new File(it.path).parent) {
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
                            // SOLUCIÓN: Aplicar también aquí
                            dir(new File(it.path).parent) {
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