package com.entities;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Personnality {
	
	@PrimaryKey
	private String nom; 
	
	@Persistent
	private String theme;
	
	@Persistent 
	private String realName; 
	
	
	// TODO : uncomment this params to update with account twitter informations 
	//private String name; 
	
	public Personnality(String nom, String theme, String realName) {
		this.nom = nom;
		this.theme = theme;
		this.realName = realName;
	}

	public String getNom() {
		return nom;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {
		return "Personnality [nom=" + nom + ", theme=" + theme + "realName=" + realName + "]";
	}
	
	
	
}
