package com.eamonnlinehan.slack;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * This component uses httpclient to authenticate with the Jenkins API and
 * trigger a build
 * 
 * @author <a href="mailto:eamonnlinehan@gmail.com">Eamonn Linehan</a>
 */
@Component
public class JenkinsWebhook {

	private static Log log = LogFactory.getLog(JenkinsWebhook.class);

	@Value("${jenkins.url}")
	private String url;

	@Value("${jenkins.username}")
	private String username;

	@Value("${jenkins.apitoken}")
	private String password;

	private CloseableHttpClient httpClient;

	private HttpClientContext localContext;

	private HttpHost host;

	private URI baseUri;

	@PostConstruct
	public void startClient() {

		baseUri = URI.create(this.url);

		this.host = new HttpHost(baseUri.getHost(), baseUri.getPort(), baseUri.getScheme());
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(baseUri.getHost(), baseUri.getPort()),
				new UsernamePasswordCredentials(username, password));
		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();

		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(host, basicAuth);

		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		connManager.setMaxTotal(20);
		// Increase default max connection per route to 20
		connManager.setDefaultMaxPerRoute(20);

		this.httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider)
				.setConnectionManager(connManager).build();

		// Add AuthCache to the execution context
		this.localContext = HttpClientContext.create();
		localContext.setAuthCache(authCache);

	}

	@PreDestroy
	public void close() {
		try {
			httpClient.close();
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * Sends the POST to Jenkins to trigger the job
	 * 
	 * @param job
	 *            a description of the job and its parameters
	 * @return the response status from the Jenkins server
	 */
	public HttpStatus triggerJob(JenkinsJob job) {

		// Make sure job names get encoded correctly

		try {

			String path = null;
			if (job.isParameterized())
				path = "/job/" + job.getJobName() + "/buildWithParameters";
			else
				path = "/job/" + job.getJobName() + "/build";

			HttpPost httpPost = new HttpPost(new URI(baseUri.getScheme(), null, baseUri.getHost(), baseUri.getPort(),
					path, job.getQueryString(), null));
			log.info(httpPost.getRequestLine());

			HttpResponse response = httpClient.execute(host, httpPost, localContext);
			log.info(response.getStatusLine().toString());

			return HttpStatus.valueOf(response.getStatusLine().getStatusCode());

		} catch (URISyntaxException | IOException e) {
			log.error("Failed to trigger job '" + job.getJobName() + "'", e);
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}

	}

}
