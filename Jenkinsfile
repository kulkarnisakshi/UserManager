pipeline
{

    options
    {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    agent any

    tools
    {
        maven 'maven3'
    }

    stages
    {


        stage('chk Java version')
        {
            steps
            {
                echo 'checking java version'
                sh 'java --version'
				echo 'java version is'
            }
        }

        stage('chk Git version')
        {
            steps
            {
                 echo 'checking git version'
                 sh 'git --version'
        		 echo 'git version is'
            }
        }

        stage('chk Maven version')
        {
            steps
            {
                  echo 'checking maven version'
                  sh 'mvn --version'
          		  echo 'maven version is'
            }
        }

        stage('chk Jenkins version')
        {
            steps
            {
                   echo 'checking jenkins version'
                   sh 'jenkins --version'
             	   echo 'jenkins version is'
            }
        }


        stage('Code Compile')
        {
            steps
            {
                  echo 'code compilation is starting'
                  sh 'mvn clean compile'
				  echo 'code compilation is completed'
             }
        }


        /**stage('Sonarqube scanning')
		{
        		environment
        		{
        			 scannerHome = tool 'sonarqube'
        		}
        			steps
					{
        			    withSonarQubeEnv('sonar-server')
						{
        				   sh "${scannerHome}/bin/sonar-scanner"
                           sh 'mvn sonar:sonar'
                        }
                        timeout(time: 10, unit: 'MINUTES')
						{
                            waitForQualityGate abortPipeline: true
                        }
        			}
        }***/

        stage('Code Package')
        {
            steps
            {
                     echo 'code packing is starting'
                     sh 'mvn clean package'
        			 echo 'code packing is completed'
            }
        }


        stage('Building & Tag Docker Image')
        {
            steps
            {
                      echo 'Starting Building Docker Image'
                      sh 'docker build -t kulkarnisakshi/usermanager .'
                      sh 'docker build -t usermanager .'
                      echo 'Completed  Building Docker Image'
            }
        }


        stage('Docker Image Scanning')
        {
            steps
            {
                       echo 'Docker Image Scanning Started'
                       sh 'java --version'
                       echo 'Docker Image Scanning Completed'
            }
        }


        stage(' Docker push to Docker Hub')
        {
            steps
            {
                    script
                    {
                         withCredentials([string(credentialsId: 'dockerhub-cred', variable: 'dockerhub-cred')])
                         {
                         sh 'docker login docker.io -u kulkarnisakshi -p dockerhub'
                         echo "Push Docker Image to DockerHub : In Progress"
                         sh 'docker push kulkarnisakshi/usermanager:latest'
                         echo "Push Docker Image to DockerHub : In Progress"
                         sh 'whoami'
                         }
                    }
            }
        }

        stage(' Docker Image Push to Amazon ECR')
        {
            steps
            {
                script
                {
                          docker.withRegistry('https://152434801645.dkr.ecr.ap-south-1.amazonaws.com/makemytrip', 'ecr:ap-south-1:ecr-cred')
                          {
                          sh """
                          echo "List the docker images present in local"
                          docker images
                          echo "Tagging the Docker Image: In Progress"
                          docker tag usermanager:latest 152434801645.dkr.ecr.ap-south-1.amazonaws.com/usermanager:latest
                          echo "Tagging the Docker Image: Completed"
                          echo "Push Docker Image to ECR : In Progress"
                          docker push 152434801645.dkr.ecr.ap-south-1.amazonaws.com/usermanager:latest
                          echo "Push Docker Image to ECR : Completed"
                          """
                          }
                }
            }
        }

        stage('Delete docker images from jenkins')
        {
            steps
            {
                      sh 'docker rmi $(docker images -q) -f'

            }
        }


           /***stage('Upload Docker Images to Nexus') { Nexus stage
                      steps {
                          script{
                             withCredentials([usernamePassword(credentialsId: 'nexus-credentials' , usernameVariable: 'USERNAME' , passwordVariable: 'PASSWORD')]){
                             sh 'docker login http://65.2.169.16/:8085/repository/makemytrip/ -u admin -p ${PASSWORD}'
                             echo "Push Docker Image to Nexus : In Progress"
                             sh 'docker tag usermanager 65.2.169.16/:8085/usermanager:latest'
                             sh 'docker push 65.2.169.16/:8085/usermanager'
                             echo " Push Docker Image to Nexus : Completed"
                          }
                      }
                    }
          		}***/

    }
}