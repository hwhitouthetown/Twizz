package com.tools;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.entities.Personnality;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultIterable;

public class Util {

	public static int random(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	public static Personnality convert(Entity e){
		
		Personnality p = null; 

		String nom = e.getKey().toString();
		String theme = e.getProperty("Theme").toString();
		
		return new Personnality(nom,theme);
	}
	
	
	public static List<Entity> getThemes(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Theme");
		PreparedQuery prepared = datastore.prepare(q);
		List<Entity> list =  datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return list;
	}
	
	public static List<Entity> getPersonnalities(String themeName){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Filter propertyFilter = new FilterPredicate("Theme", FilterOperator.EQUAL, themeName);
		
		Query q = new Query("Personnality").setFilter(propertyFilter);
		List<Entity> list =  datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		return list;
	}

}
