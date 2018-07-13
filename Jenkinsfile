pipeline {
    agent { label 'jenkins-slave-mvn' }
    
    environment {
        CI_CD_PROJECT = "coo-ci-cd"
        DEV_PROJECT = "coo-dev"
        TEST_PROJECT = "coo-test"
        SOURCE_CONTEXT_DIR = ""
        UBER_JAR_CONTEXT_DIR = "gis-service/target/"
        APPLY_PLAYBOOK_NAME = "apply.yml"
        MVN_COMMAND = "clean deploy"
        MVN_SNAPSHOT_DEPLOYMENT_REPOSITORY = "nexus::default::http://nexus:8081/repository/maven-snapshots"
        MVN_RELEASE_DEPLOYMENT_REPOSITORY = "nexus::default::http://nexus:8081/repository/maven-releases"
        APP_NAME = "gis-service"
        OCP_API_SERVER = "${OPENSHIFT_API_URL}"
        OCP_TOKEN = readFile('/var/run/secrets/kubernetes.io/serviceaccount/token').trim()
    
    }

    stages {
        stage('Build'){
            steps{
                slackSend "${APP_NAME} Job Started - ${JOB_NAME} ${BUILD_NUMBER} (<${BUILD_URL}|Open>)"

                // checkout scm is not needed anymore, it's automatic
                sh 'mvn clean deploy -DaltDeploymentRepository=${MVN_SNAPSHOT_DEPLOYMENT_REPOSITORY} -q'
            }
            post {
                always {
                    //generate cucumber reports
                    cucumber '**/*.json'
                }
            }
        }

        stage('Bake'){
            steps{
                script{
                    def helper = load 'shared-library.groovy'
                    helper.patchBuildConfigOutputLabels(env)

                    openshift.withCluster () {
                        def buildSelector = openshift.startBuild( "${APP_NAME} --from-dir=${UBER_JAR_CONTEXT_DIR}" )
                        buildSelector.logs('-f')
                    }
                }
            }
        }

        stage('Deploy: Dev'){
            agent { label 'jenkins-slave-ansible'}
            steps {
                script{
                    def helper = load 'shared-library.groovy'
                    helper.applyAnsibleInventory( "${APPLY_PLAYBOOK_NAME}", 'dev' )
                    timeout(5) { // in minutes
                        openshift.loglevel(3)
                        helper.promoteImageWithinCluster( "${APP_NAME}", "${CI_CD_PROJECT}", "${DEV_PROJECT}" )
                        helper.verifyDeployment("${APP_NAME}", "${DEV_PROJECT}")
                    }
                }
            }
        }

        stage('Deploy: Test'){
            agent { label 'jenkins-slave-ansible'}
            options {
                timeout(time: 1, unit: 'HOURS')
            }
            steps {
                script {
                    slackSend "${env.APP_NAME} Input requested - ${JOB_NAME} ${BUILD_NUMBER} (<${BUILD_URL}|Open>)"
                    input message: 'Deploy to Test?'

                }
                script{
                    def helper = load 'shared-library.groovy'
                    helper.applyAnsibleInventory( "${APPLY_PLAYBOOK_NAME}", 'test' )
                    timeout(10) { // in minutes
                        helper.promoteImageWithinCluster( "${APP_NAME}", "${DEV_PROJECT}", "${TEST_PROJECT}" )
                        // the new client is having random failures
                        helper.verifyDeployment("${APP_NAME}", "${TEST_PROJECT}")
                    }
                }

                slackSend color: "good", message: ":success: ${APP_NAME} Build Completed - ${JOB_NAME} ${BUILD_NUMBER} (<${BUILD_URL}|Open>)"

            }
        }
    }
}
