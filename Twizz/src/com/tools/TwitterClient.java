package com.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import twitter4j.auth.RequestToken;

public class TwitterClient {

	private String consumerKey; 
	private String consumerSecret;
	private String accessToken; 
	private String accessTokenSecret;
	private RequestToken requestToken; 
	private String callBackURL; 
	
	public TwitterClient(){
		
		Properties prop = new Properties();
		InputStream input = null;
		requestToken = null;

		try {

			input = new FileInputStream("WEB-INF/ressources/twitter.properties");

			// load a properties file
			prop.load(input);

			consumerKey = prop.getProperty("twitter.consumerKey"); 
			consumerSecret= prop.getProperty("twitter.consumerSecret"); 
			accessToken= prop.getProperty("twitter.accessToken"); 
			accessTokenSecret = prop.getProperty("twitter.accessTokenSecret"); 
			callBackURL = prop.getProperty("twitter.callBackURL");
		
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public RequestToken getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(RequestToken requestToken) {
		this.requestToken = requestToken;
	}
	
	
	
	

	
}
