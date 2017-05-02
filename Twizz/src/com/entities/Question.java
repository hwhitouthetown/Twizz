package com.entities;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Question {
	
	public static int DEFAULT_NB_CHOICE = 4; 
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private long id; 
	@Persistent
	private int nbChoices;
	@Persistent
	private String content;
	@Persistent
	private String reponse; 
	@Persistent
	private List<String> othersChoices;
	
	
	
	public Question() {
		this.nbChoices = DEFAULT_NB_CHOICE;
		this.content = "";
		this.reponse = "";
		this.othersChoices = new ArrayList<String>();
	}
	
	public Question( String content, String reponse) {
		this.nbChoices = DEFAULT_NB_CHOICE;
		this.content = content;
		this.reponse = reponse;
		this.othersChoices = new ArrayList<String>();
	}
	
	
	public Question(int nbChoices, String content, String reponse) {
		this.nbChoices = nbChoices;
		this.content = content;
		this.reponse = reponse;
		this.othersChoices = new ArrayList<String>();
	}
	
	public Question(int nbChoices, String content, String reponse, ArrayList<String> othersChoices) {
		super();
		this.nbChoices = nbChoices;
		this.content = content;
		this.reponse = reponse;
		this.othersChoices = othersChoices;
	}

	public void addChoice(String choice){
		othersChoices.add(choice);
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public List<String> getOthersChoices() {
		return othersChoices;
	}

	public void setOthersChoices(ArrayList<String> othersChoices) {
		this.othersChoices = othersChoices;
	}


	@Override
	public String toString() {
		
		String other = ""; 
		
		for(int i = 0 ; i< othersChoices.size();i++){
			
			other += ":" + othersChoices.get(i); 
			
		}
		
		return "Question [id=" + id + ", nbChoices=" + nbChoices + ", content=" + content + ", reponse=" + reponse
				+ ", othersChoices=" + other + "]";
	} 
}

