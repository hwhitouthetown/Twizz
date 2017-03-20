package com.entities;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Theme {

    @PrimaryKey
	private String nom;

	public String getNom() {
		return nom;
	}

	public void setName(String nom) {
		this.nom = nom;
	} 
	
	
	
}
