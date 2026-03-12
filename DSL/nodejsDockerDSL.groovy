job('Aplicacion Node.js Docker DSL') {
    description('Aplicación Node JS Docker DSL')
    scm {
        git('https://github.com/robcg1102/nodejsapp.git', 'master') { node ->
            node / gitConfigName('robcg1102')
            node / gitConfigEmail('robcg1102@gmail.com')
        }
    }
    triggers {
        scm('H/7 * * * *')
    }
    wrappers {
        nodejs('nodejs')
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('robcg1102/nodejsapp')
            tag('${GIT_REVISION,length=7}')
            registryCredentials('dockerID')
            forcePull(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
    publishers {
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
        }
    }
}
