package com.entities;

public class PersonnalityThemeEntity {
	
	private String accountName; 
	private String themeName;
		
	public PersonnalityThemeEntity(String accountName, String themeName) {
		this.accountName = accountName;
		this.themeName = themeName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getThemeName() {
		return themeName;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	} 
	
}
