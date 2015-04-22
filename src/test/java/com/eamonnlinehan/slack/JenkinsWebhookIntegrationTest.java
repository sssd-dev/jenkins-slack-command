package com.eamonnlinehan.slack;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Sends an actual request to kick off a job to Jenkins and checks that a success
 * response is received.
 * 
 * @author <a href="mailto:eamonnlinehan@gmail.com">Eamonn Linehan</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
public class JenkinsWebhookIntegrationTest {

	@Autowired
	private JenkinsWebhook jenkins;

	@Test
	public void testKickOffBuild() throws ClientProtocolException, IOException, URISyntaxException {

		// Get a valid job name and token from configuration
		String jobName = "Platform TRUNK compile check";
		String buildToken = "2aa5ef75-798e-4d91-8704-ced8b58d47c6";

		JenkinsJob job = new JenkinsJob(jobName, buildToken);
		
		HttpStatus status = jenkins.triggerJob(job);

		assertTrue(HttpStatus.OK.equals(status));

	}

}
