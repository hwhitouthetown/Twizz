package com.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.exceptions.InvalidEntityException;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.apphosting.client.datastoreservice.proto.DatastoreService;

public class CSVReader {

	private String path;
	private String entityName;

	public CSVReader(String path, String entityName) throws InvalidEntityException {
		this.path = path;
		this.entityName = entityName;

		boolean entityNameExist = false;

		switch (entityName) {

		case "Theme":
			entityNameExist = true;
			readThemes();
			break;

		case "Personnality":
			entityNameExist = true;
			readPersonnalities();
			break;

		default:
			entityNameExist = false;
			break;
		}

		if (!entityNameExist) {
			throw new InvalidEntityException("Entity name :" + entityName + "doesn't exist");
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
	
	public void readThemes() {

		Logger logger = Logger.getLogger("Logger"); // Logger is java.util.logging.Logger 

		String EntityName = "Theme";
		List<Entity> themes = new ArrayList<Entity>();;
		
		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {

				
				// use comma as separator
				String[] object = line.split(cvsSplitBy);

				Entity e = new Entity(EntityName,object[0]);
				
				themes.add(e);
				logger.info("Object [Col1 = " + object[0] + "]");
			}
			
			if(themes.size()>0){
			datastore.put(themes);
			} 
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void readPersonnalities() {

		Logger logger = Logger.getLogger("Logger"); // Logger is java.util.logging.Logger 

		String EntityName = "Personnality";
		List<Entity> themes = new ArrayList<Entity>();;
		
		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {

				
				// use comma as separator
				String[] object = line.split(cvsSplitBy);

				Entity e = new Entity(EntityName,object[1]);
				e.setIndexedProperty("Theme", object[0]);
				
				themes.add(e);
				logger.info("Object [Col1 = " + object[0] + "]" + "[Col2 = " + object[0] + "]");
			}
			
			if(themes.size()>0){
			datastore.put(themes);
			} 
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
