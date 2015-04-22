package com.eamonnlinehan.slack;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Parses a Slash command into a Jenkins job trigger
 * 
 * @author <a href="mailto:eamonnlinehan@gmail.com">Eamonn Linehan</a>
 */
@Component
public class SlashCommandParser {

	/** All jobs must be configured with this token */
	@Value("${jenkins.buildtoken}")
	private String buildToken;

	public JenkinsJob parse(String text) {

		String[] jobParams = StringUtils.split(text);

		if (jobParams != null && jobParams.length > 0 && "release".equals(jobParams[0])) {

			// Parse out a release command (last two are versions and first is
			// release)
			
			if (jobParams.length < 4)
				return null;

			String devVersion = jobParams[jobParams.length - 1];

			String relVersion = jobParams[jobParams.length - 2];

			String jobName = StringUtils.join(Arrays.copyOfRange(jobParams, 1, jobParams.length - 2), ' ').replaceAll(
					"\"", "");

			return new JenkinsReleaseJob(jobName, buildToken, relVersion, devVersion);

		} else {

			// compile job - should be just job name as parameter

			String jobName = text.trim().replaceAll("\"", "");

			return new JenkinsJob(jobName, buildToken);
		}

	}

}
