Jenkins Slack Build Trigger
===============================

This standalone Java application receives [slash commands](https://api.slack.com/slash-commands) from [Slack](https://slack.com/) via HTTP POST and triggers jobs on Jenkins by invoking its API.

Two types of Slash command are supported:

1. `/jenkins <Job Name>` - Triggers the specified Jenkins job to build immediately
2. `/jenkins release <Job Name> <Release Version> <Next Development Version>` - Triggers a build of a parameterised Jenkins job that runs the [maven-release-plugin](http://maven.apache.org/maven-release/maven-release-plugin/) passing in the release version and development version.

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
    jenkins.buildtoken=oiewjoifwjo
    
    # Configured when you set-up your slash command integration https://my.slack.com/services/new/slash-commands/
    slack.token=iWSff54fF5f4


# TODO Items

 - The endpoint should be using SSL because private tokens are being exchanged.
 - It would be nice if it was possible to configure which Slack users had permission to trigger jobs. For example, a rule like only allow members of the #product-dev channel trigger releases.


