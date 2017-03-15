package com.entities;

public class PersonnalityEntity {
	
	private String accountName; 
	private String name;
	
	public PersonnalityEntity(String accountName, String name) {
		this.accountName = accountName;
		this.name = name;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
}
