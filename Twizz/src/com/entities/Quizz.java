package com.entities;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Quizz {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private long id; 
	@Persistent
	private String relatedTheme; 
	@Persistent
	private Timestamp creationTime;
	@Persistent
	private ArrayList<Question> questions;
	

	public Quizz(String relatedTheme) {
		creationTime = new Timestamp(System.currentTimeMillis());
		this.relatedTheme = relatedTheme;
		questions = new ArrayList<Question>(); 
	}
	public String getRelatedTheme() {
		return relatedTheme;
	}
	public void setRelatedTheme(String relatedTheme) {
		this.relatedTheme = relatedTheme;
	}
	public Timestamp getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	} 
	
}
