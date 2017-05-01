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

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@SuppressWarnings("serial")
public class connectTwitterServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");

		
		String theme = "Politiques"; 
		
		Quizz quizz = new Quizz(theme); 
		
		Logger logger = Logger.getLogger("Logger");
		
		int nbQuestions = 5;
		
		int nbChoices  = 4; 
		// String add theme in request header
		
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
		
		List<Entity> listPersonnalityTheme = Util.getPersonnalities(theme);
		
		List<Entity> listPersonnalityOther = Util.getPersonnalitiesWithOut(theme); 
		
		List<Entity> listTheme = Util.getThemes(); 
		
		
		for(int i=0;i<nbQuestions;i++){
			
			
			int index = Util.random(0, listPersonnalityTheme.size()-1);
			Personnality p = Util.convertToPersonnality(listPersonnalityTheme.get(index)); 
					
			Status s = selectTweet(getTweets(p.getRealName(),twitter));
			
			
			Question question = new Question(s.getText(),p.getNom()); 
					
			Personnality other = new Personnality(null,null,null); 
			
			// On choisit deux autres personne du même thème 
			for(int j = 0; j < nbChoices -2; j++){
				
				index = Util.random(0, listPersonnalityTheme.size()-1);
				other = Util.convertToPersonnality(listPersonnalityTheme.get(index)); 
				
				while(other.getNom().equals(p.getNom())||question.getOthersChoices().contains(other.getNom())){
					index = Util.random(0, listPersonnalityTheme.size()-1);
					other = Util.convertToPersonnality(listPersonnalityTheme.get(index)); 
				} 
			
				question.addChoice(other.getRealName());
	
			}
			
			// Et une autre d'un autre thèmes //  
			
			index = Util.random(0, listPersonnalityOther.size()-1);
			other = Util.convertToPersonnality(listPersonnalityOther.get(index)); 
				
			
			question.addChoice(other.getRealName());	
			quizz.addQuestion(question);
			
			resp.getWriter().println(question);
				
		}
		
	
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		
		quizz.setCreationTime(ts);		
		Util.getQuotas(twitter);
		
		
	}
	
	
	
	
	

		
	
	
	
	
	public Status selectTweet(List<Status> result){
		
		int size = result.size(); 
		Status res = null; 
		if(size ==1){
			res = result.get(0); 
		} else {
			int indice = Util.random(0,size-1); 
			res = result.get(indice);
		}
		return res; 
	}
	
	
	public List<Status> getTweets(String username,Twitter twitter){
		
		Query q = new Query();
			
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
