package com.eamonnlinehan.slack;

/**
 * The parameters required to trigger a parameterised Jenkins job that performs a maven release from a script.
 * <p>
 * TODO This could be more generic and be a ParamaterisedJenkinsJob
 * 
 * @author <a href="mailto:eamonnlinehan@gmail.com">Eamonn Linehan</a>
 */
public class JenkinsReleaseJob extends JenkinsJob {

	private final String releaseVersion;

	private final String developmentVersion;

	public JenkinsReleaseJob(String jobName, String buildToken, String releaseVersion,
			String developmentVersion) {
		super(jobName, buildToken);
		this.releaseVersion = releaseVersion;
		this.developmentVersion = developmentVersion;
	}

	/**
	 * @return the releaseVersion
	 */
	public String getReleaseVersion() {
		return releaseVersion;
	}

	/**
	 * @return the developmentVersion
	 */
	public String getDevelopmentVersion() {
		return developmentVersion;
	}

	@Override
	public boolean isParameterized() {
		return true;
	}
	
	@Override
	public String getQueryString() {
		return super.getQueryString() + "&releaseVersion=" + releaseVersion + "&developmentVersion="
				+ developmentVersion;
	}

}