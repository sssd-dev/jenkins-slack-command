package com.eamonnlinehan.slack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Application entry point. The main logic can be found in the SlashController
 * 
 * @author <a href="mailto:eamonnlinehan@gmail.com">Eamonn Linehan</a>
 */
@SpringBootApplication
@ImportResource({ "/jenkins-job-buildtokens.xml" })
public class Application {

	private static Log log = LogFactory.getLog(Application.class);

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

		log.info("Slack/Jenkins proxy running.");
	}

}
