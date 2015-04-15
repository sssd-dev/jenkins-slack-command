Jenkins Slack Build Trigger
===============================

This standalone Java application receives [slash commands](https://api.slack.com/slash-commands) from [Slack](https://slack.com/) via HTTP POST and triggers jobs on Jenkins by invoking its API.

The application is packaged an executable jar via Spring Boot and listens for HTTP POSTS on port 8085 by default.

To execute the application:
    $ java -jar jenkins-slack-0.0.1-SNAPSHOT.jar
    
It will print its logging to stdout. To run it as a *nix process wrap it with *nohup*. For example: 
    $ nohup java -jar jenkins-slack-0.0.1-SNAPSHOT.jar &

An application.properties file should be provided in the same directory as the executable jar. The following properties may be configured:

    # The port the http server will listen on. Configure Slack to send its slash command to this port.
    server.port=8085
    
    # The URL to the Jenkins instance that we want to trigger jobs on
    jenkins.url=https://master.build-cluster.britebill.com/
    
    # See https://wiki.jenkins-ci.org/display/JENKINS/Authenticating+scripted+clients
    jenkins.username=eamonnlinehan
    jenkins.apitoken=fdoihg905ud6ac5f81
    
    # Configured when you set-up your slash command integration https://my.slack.com/services/new/slash-commands/
    slack.token=iWSff54fF5f4


# TODO Items

 - Add support for passing parameters to parameterized Jenkins jobs. This will be requires to do releases.
 - Authentication of the jobs that may be triggered and the build tokens to be allowed trigger them needs to be externalised.
 - It would be nice if it was possible to configure which Slack users had permission to trigger jobs. For example, a rule like only allow members of the #product-dev channel trigger releases.


