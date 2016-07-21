package org.testobject.rest.api;

import com.google.common.base.Optional;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.Closeable;
import java.net.URI;

public class RestClient implements Closeable {

	public static final String REST_APPIUM_PATH = "/rest/appium/v1/";
	public static final String REST_DEVICES_PATH = "/rest/devices/v1/";
	private final Client client;
	private final WebTarget target;

	RestClient(Client client, WebTarget target) {
		this.client = client;
		this.target = target;
	}

	public WebTarget path(String path) {
		return target.path(path);
	}

	@Override
	public void close() {
		client.close();
	}

	public static final class Builder {

		private Client client;
		private String baseUrl;
		private String path = "";
		private String token = "";

		public static Builder createClient() {
			return new Builder();
		}

		private static void addProxyConfiguration(ClientConfig config, String baseUrl) {
			String protocol = URI.create(baseUrl).getScheme().toLowerCase();

			Optional<String> proxyHost = Optional.fromNullable(System.getProperty(protocol + ".proxyHost"));
			if (!proxyHost.isPresent()) {
				return;
			}

			String host = proxyHost.get();
			String port = Optional.fromNullable(System.getProperty(protocol + ".proxyPort")).or("8080");
			String proxyProtocol = Optional.fromNullable(System.getProperty(protocol + ".proxyProtocol")).or("http");
			String url = proxyProtocol + "://" + host + ":" + port;
			config.property(ClientProperties.PROXY_URI, url);

			Optional<String> username = Optional.fromNullable(System.getProperty(protocol + ".proxyUser"));
			Optional<String> password = Optional.fromNullable(System.getProperty(protocol + ".proxyPassword"));
			if (username.isPresent() && password.isPresent()) {
				UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username.get(), password.get());
				CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				credentialsProvider.setCredentials(AuthScope.ANY, credentials);
			}
		}

		public Builder withUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this;
		}

		public Builder withToken(String token) {
			this.token = token;
			return this;
		}

		public Builder path(String path) {
			this.path = path;
			return this;
		}

		public RestClient build() {

			ClientConfig config = new ClientConfig();
			config.property(ApacheClientProperties.DISABLE_COOKIES, false);

			addProxyConfiguration(config, baseUrl);

			client = ClientBuilder.newClient(config);

			client.register(new LoggingFeature());
			client.register(HttpAuthenticationFeature.basic(token, ""));

			WebTarget target = client.target(baseUrl + path);
			return new RestClient(client, target);
		}

	}

}