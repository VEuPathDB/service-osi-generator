#!groovy

@Library('pipelib')
import org.veupathdb.lib.Builder;

node('centos8') {

  def build = new Builder(this);

  checkout scm
  builder.buildContainers(
    [ name: 'osi-generator-database', dockerfile: 'docker/DB.Dockerfile'       ],
    [ name: 'osi-generator-service',  dockerfile: 'docker/SVC.prod.Dockerfile' ]
  )
}
