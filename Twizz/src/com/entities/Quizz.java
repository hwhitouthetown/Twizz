package com.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;
import javax.persistence.Embedded;


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
	private @Embedded List<Question> questions;
	
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
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
	
	public void addQuestion(Question q){
		questions.add(q);
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Quizz [id=" + id + ", relatedTheme=" + relatedTheme + ", creationTime=" + creationTime + ", questions="
				+ questions + "]";
	} 
	
	
	
}
