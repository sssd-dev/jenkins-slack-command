package com.eamonnlinehan.slack;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Tests the REST Controller is receiving Slack slash commands
 * 
 * @author <a href="mailto:eamonnlinehan@gmail.com">Eamonn Linehan</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
public class SlashControllerTest {

	@Value("${local.server.port}")
	private int port;
	
	@Value("${slack.token}")
	private String slackToken;

	private URL base;
	private RestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
	}

	@Test
	public void getHello() throws Exception {

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("token", slackToken);
		map.add("team_id", "T0001");
		map.add("team_domain", "example");
		map.add("channel_id", "C2147483705");
		map.add("channel_name", "test");
		map.add("user_id", "U2147483697");
		map.add("user_name", "Steve");
		map.add("command", "/weather");
		map.add("text", "94070");

		String response = template.postForObject(base.toString(), map, String.class);

		assertThat(response, equalTo("Jenkins building 94070"));
	}

}
