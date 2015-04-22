package com.eamonnlinehan.slack;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
public class SlashCommandParserTest {

	@Autowired
	private SlashCommandParser commandParser;

	@Test
	public void testCompileJob() {

		JenkinsJob job = commandParser.parse("Platform TRUNK Compile Check");

		assertTrue(job instanceof JenkinsJob);
		assertNotNull(job.getJobName());

		System.out.println(job);
		
	}

	@Test
	public void testCompileJobQuoted() {

		JenkinsJob job = commandParser.parse("  \"Platform TRUNK Compile Check\"");

		assertTrue(job instanceof JenkinsJob);
		assertNotNull(job.getJobName());

		System.out.println(job);
		
	}

	@Test
	public void testReleaseJob() {

		JenkinsJob job = commandParser.parse(" release Platform 3.1.10 3.1.11-SNAPSHOT");

		assertTrue(job instanceof JenkinsReleaseJob);
		assertNotNull(job.getJobName());
		assertNotNull(((JenkinsReleaseJob) job).getDevelopmentVersion());
		
		System.out.println(job);

	}
	
	@Test
	public void testReleaseQuotedJob() {

		JenkinsJob job = commandParser.parse(" release   \"Platform TRUNK Release\" 3.1.10   3.1.11-SNAPSHOT");

		assertTrue(job instanceof JenkinsReleaseJob);
		assertNotNull(job.getJobName());
		
		assertNotNull(((JenkinsReleaseJob) job).getDevelopmentVersion());
		
		System.out.println(job);

	}

}
