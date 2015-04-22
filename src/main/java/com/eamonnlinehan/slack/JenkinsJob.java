package com.eamonnlinehan.slack;

/**
 * The parameters required to trigger the a Jenkins job from a script
 * 
 * @author <a href="mailto:eamonnlinehan@gmail.com">Eamonn Linehan</a>
 */
public class JenkinsJob {

	private final String jobName;

	private final String buildToken;

	private String cause;

	protected JenkinsJob(String jobName, String buildToken) {
		super();
		this.jobName = jobName;
		this.buildToken = buildToken;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @return the buildToken
	 */
	public String getBuildToken() {
		return buildToken;
	}

	/**
	 * @return the cause
	 */
	public String getCause() {
		return cause;
	}

	/**
	 * @param cause
	 *            the cause to set
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}

	/**
	 * Indicates whether this Jenkins job require parameters
	 * 
	 * @return true if parameters need to be passed
	 */
	public boolean isParameterized() {
		return false;
	}

	public String getQueryString() {
		if (this.cause == null)
			return "token=" + buildToken + "&delay=0sec";
		else
			return "token=" + buildToken + "&cause=" + cause + "&delay=0sec";
	}

	@Override
	public String toString() {
		return getJobName() + "?" + getQueryString();
	}

}