package com.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

import com.entities.Personnality;
import com.google.appengine.api.datastore.Entity;
import com.tools.TwitterClient;
import com.tools.Util;

import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@SuppressWarnings("serial")
public class connectTwitterServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		
		TwitterClient cl = new TwitterClient();

		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(cl.getConsumerKey());
		builder.setOAuthConsumerSecret(cl.getConsumerSecret());
		builder.setOAuthAccessToken(cl.getAccessToken());
		builder.setOAuthAccessTokenSecret(cl.getAccessTokenSecret());
		Configuration configuration = builder.build();

		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		
		resp.getWriter().println("Hello, world");
		resp.getWriter().println(twitter.toString());
		try {
			resp.getWriter().println(twitter.getAccountSettings().toString());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		resp.getWriter().println(twitter.getAuthorization().toString());
		
		List<Entity> list = Util.getPersonnalities("Politiques");
		
	
		
		Personnality p = Util.convert(list.get(0));
		resp.getWriter().println(p);
		
		
	}
	

		
	
	
	
	
	public Status selectTweet(List<Status> result){
		
		int size = result.size(); 
		Status res = null; 
		if(size>0&&size<2){
			res = result.get(0); 
		} else {
			int indice = Util.random(0,size); 
			res = result.get(indice);
		}
		return res; 
	}
	
	
	public List<Status> getTweets(String username,Twitter twitter){
		
		Query q = new Query();
		
		q.setResultType(ResultType.recent);
	
		q.setQuery("from:@"+username);
		
		QueryResult result = null;
		try {
			result = twitter.search(q);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return result.getTweets();		
	}

}
