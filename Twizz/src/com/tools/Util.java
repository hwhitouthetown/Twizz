package com.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import com.entities.Personnality;
import com.entities.Theme;
import com.exceptions.InvalidListThemeException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultIterable;

import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Util {

	
	public static int random(int min, int max) {
		Random rn = new Random();
		int randomNum = rn.nextInt(max - min + 1) + min;
		return randomNum;
	}
	
	public static Personnality convertToPersonnality(Entity e){
		 
		String nom = e.getKey().getName();
		String theme = e.getProperty("Theme").toString();
		String realName = e.getProperty("RealName").toString();
		
		return new Personnality(nom,theme,realName);
	}
	
	public static Theme convertToTheme(Entity e){
		
		Theme p = null; 
		String nom = e.getKey().toString();
		
		
		return new Theme(nom);
	}
	
	
	public static List<Entity> getThemes(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Theme");
		PreparedQuery prepared = datastore.prepare(q);
		List<Entity> list =  datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return list;
	}
	
	
	public static void updatePersonnalities(List <Entity> personnalities){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				
		Query q = new Query("Personnality");
	
		datastore.put(personnalities); 
	}
	
	
	
	public static List<Entity> getPersonnalities(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				
		Query q = new Query("Personnality");
		List<Entity> list =  datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return list;
	}
	
	
	public static void deletePersonnality(String key){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
					
		Entity e = new Entity("Personnality",key);
		Key employeeKey = e.getKey();
		
		datastore.delete(employeeKey);
		
		
	}
	
	public static List<Entity> getPersonnalities(String themeName){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Filter propertyFilter = new FilterPredicate("Theme", FilterOperator.EQUAL, themeName);
		
		Query q = new Query("Personnality").setFilter(propertyFilter);
		List<Entity> list =  datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return list;
	}
	
	public static List<Entity> getPersonnalitiesWithOut(String themeName){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Filter propertyFilter = new FilterPredicate("Theme", FilterOperator.NOT_EQUAL, themeName);
		
		Query q = new Query("Personnality").setFilter(propertyFilter);
		List<Entity> list =  datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return list;
	}
	
	
	public static List<Personnality> convertListEntityToListPersonnalities(List<Entity> entities){
		
		List<Personnality> result = new ArrayList<Personnality>(); 
		for(Entity e: entities){
			result.add(convertToPersonnality(e));
		}
		
		return result; 
	}
	
	public static List<Theme> convertListEntityToListThemes(List<Entity> entities){
		
		List<Theme> result = new ArrayList<Theme>(); 
		for(Entity e: entities){
			result.add(convertToTheme(e));
		}
		
		return result; 
	}
	
	// TO GET LAST QUIZZ THEME : UPDATE & TIME in DATABASE // 
	
	public static String themeToGenerate(List<Theme> lists) throws InvalidListThemeException{
		
		String res = null; 
		
		
		if(lists!=null && lists.size() >0){
			
			int indice = random(0,lists.size());
			
			res = lists.get(indice).getNom();
			
		} else {
			throw new InvalidListThemeException("Input list is null or empty");
		}
		
		return res; 
	}
	

	// NEED TO HAVE QUOTA 2 KNOW IF WE CAN GENERATE A QUIZZ // 
	public static int getQuotas(Twitter twitter){
		
		Logger logger = Logger.getLogger("Logger");
		
		int remainingRequests = 0; 
		
		Map<String, RateLimitStatus> rateLimitStatus = null;
		try {
			rateLimitStatus = twitter.getRateLimitStatus();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		for (String endpoint : rateLimitStatus.keySet()) {
		    RateLimitStatus status = rateLimitStatus.get(endpoint);
		    logger.info("Endpoint: " + endpoint);
		    logger.info(" Limit: " + status.getLimit());
		    logger.info(" Remaining: " + status.getRemaining());
		    logger.info(" ResetTimeInSeconds: " + status.getResetTimeInSeconds());
		    logger.info(" SecondsUntilReset: " + status.getSecondsUntilReset());
		}
		
		return remainingRequests; 
	}
	

}
