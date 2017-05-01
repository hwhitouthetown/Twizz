package com.project;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.entities.Personnality;
import com.entities.Question;
import com.entities.Quizz;
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
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@SuppressWarnings("serial")
public class UpdatePersonnalitiesNameServlet extends HttpServlet {

	Logger logger = Logger.getLogger("Logger");

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

		try {
			resp.getWriter().println(twitter.getAccountSettings().toString());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		resp.getWriter().println(twitter.getAuthorization().toString());

		List<Entity> listPersonnalityTheme = Util.getPersonnalities();

		for (Entity e : listPersonnalityTheme) {

			Personnality p = Util.convertToPersonnality(e);
			String realName = "";

			if (p.getRealName().equals(null) || p.getRealName().isEmpty() || p.getRealName().equals("")) {

				try {
					realName = getRealName(p.getNom(), twitter);
				} catch (TwitterException e1) {
					System.err.println("Account :" + p.getNom() + "not found");
					Util.deletePersonnality(p.getNom());
				}
				
				
				e.setIndexedProperty("RealName", realName);
			}
		}

		Util.updatePersonnalities(listPersonnalityTheme);

	}

	public String getRealName(String username, Twitter twitter) throws TwitterException {

		User user = null;


			logger.info("\n getRealName :" + username);
			user = twitter.showUser(username);
	
			if (!user.equals(null)) {
				return user.getName();
			} else {
				return "";
			}
		

	}

}
