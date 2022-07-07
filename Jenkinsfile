#!groovy

@Library('pipelib')
import org.veupathdb.lib.Builder

node('centos8') {

  def builder = new Builder(this)

  builder.gitClone()
  builder.buildContainers([
    [ name: 'osi-generator-database', dockerfile: 'docker/DB.Dockerfile' ],
    [ name: 'osi-generator-service',  dockerfile: 'docker/SVC.Dockerfile' ]
  ])
}
