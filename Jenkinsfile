#!groovy

node('centos8') {
  def tag       = "latest"
  def gitUrl    = 'https://github.com/VEuPathDB/service-osi-generator.git'
  def imageBase = 'osi-generator'
  def dbImage   = imageBase + '-database'
  def svcImage  = imageBase + '-service'

  stage('checkout') {
    checkout([
      $class: 'GitSCM',
      branches: [[name: env.BRANCH_NAME ]],
      doGenerateSubmoduleConfigurations: false,
      extensions: [[
                     $class: 'SubmoduleOption',
                     disableSubModules: true
                   ]],
      userRemoteConfigs:[[url: gitUrl]]
    ])
  }

  stage('build') {
    withCredentials([
      usernameColonPassword(
        credentialsId: '0f11d4d1-6557-423c-b5ae-693cc87f7b4b',
        variable: 'HUB_LOGIN'
      )
    ]) {

      // set tag to branch if it isn't master
      if (env.BRANCH_NAME != 'master') {
        tag = "${env.BRANCH_NAME}"
      }

      // build database image
      sh "podman build --format=docker -f docker/DB.Dockerfile -t ${dbImage} ."

      // push to dockerhub (for now)
      sh "podman push --creds \"$HUB_LOGIN\" ${dbImage} docker://docker.io/veupathdb/${dbImage}:${tag}"

      // build service image
      sh "podman build --format=docker -f docker/SVC.prod.Dockerfile -t ${svcImage} ."

      // push to dockerhub (for now)
      sh "podman push --creds \"$HUB_LOGIN\" ${svcImage} docker://docker.io/veupathdb/${svcImage}:${tag}"
    }
  }
}
