/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
pipeline {
    agent none

    environment {
        PROJECT_ZONE = "${JENK_INT_IT_ZONE}"
        PROJECT_ID = "${JENK_INT_IT_PROJECT_ID}"
        STAGING_CLUSTER = "${JENK_INT_IT_STAGING}"
        PROD_CLUSTER = "${JENK_INT_IT_PROD}"
        BUILD_CONTEXT_BUCKET = "${JENK_INT_IT_BUCKET}"
        BUILD_CONTEXT = "build-context-${BUILD_ID}.tar.gz"
        APP_NAME = "capsgcpwebpoc"
        GCR_IMAGE = "gcr.io/capsgcppoc/capsgcpwebpoc:${BUILD_ID}"
        APP_JAR = "${APP_NAME}.jar"
    }

    stages {
        stage("Build and test") {
	    agent {
    	    	kubernetes {
      		    cloud 'kubernetes'
      		    label 'maven-pod'
      		    yamlFile 'jenkins/maven-pod.yaml'
		}
	    }
	    steps {
	    	container('maven') {
                     
		        // build
	    	        sh "mvn clean package"

		        // run tests
		        sh "mvn verify"
		        
			// bundle the generated artifact    
		        sh "cp target/${APP_NAME}-*.jar $APP_JAR"

		        // archive the build context for kaniko			    
			sh "tar --exclude='./.git' -zcvf /tmp/$BUILD_CONTEXT ."
		        sh "mv /tmp/$BUILD_CONTEXT ."
		        step([$class: 'ClassicUploadStep', credentialsId: env.JENK_INT_IT_CRED_ID, bucket: "gs://${BUILD_CONTEXT_BUCKET}", pattern: env.BUILD_CONTEXT])
                    
		}
	    }
	}
	stage("Publish Image") {
            agent {
    	    	kubernetes {
      		    cloud 'kubernetes'
      		    label 'kaniko-pod'
      		    yamlFile 'jenkins/kaniko-pod.yaml'
		}
	    }
	    environment {
                PATH = "/busybox:/kaniko:$PATH"
      	    }
	    steps {
	        container(name: 'kaniko', shell: '/busybox/sh') {
		    sh '''#!/busybox/sh
		    /kaniko/executor -f `pwd`/Dockerfile -c `pwd` --context="gs://${BUILD_CONTEXT_BUCKET}/${BUILD_CONTEXT}" --destination="${GCR_IMAGE}" --build-arg JAR_FILE="${APP_JAR}"
		    '''
		}
	    }
	}
	stage("Deploy to staging") {
            agent {
    	        kubernetes {
      		    cloud 'kubernetes'
      		    label 'gke-deploy'
		    yamlFile 'jenkins/gke-deploy-pod.yaml'
		}
            }
	    steps{
		container('gke-deploy') {
		    sh "sed -i s#IMAGE#${GCR_IMAGE}#g kubernetes/manifest.yaml"
                    step([$class: 'KubernetesEngineBuilder', projectId: "capsgcppoc", clusterName: env.STAGING_CLUSTER, location: env.PROJECT_ZONE, manifestPattern: 'kubernetes/manifest.yaml', credentialsId: "capsgcppoc", verifyDeployments: true])
		}
            }
	}
        /**
         * This stage simulates an SRE manual approval process. Should you want to incorporate
         * this into your pipeline you can uncomment this stage. 
        stage('Wait for SRE Approval') {
            steps{
                timeout(time:12, unit:'HOURS') {
                    input message:'Approve deployment?'
                }
            }
        }
	*/
         
	
    }
}
