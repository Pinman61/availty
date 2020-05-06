package com.availty.exercises;

public class Person {
	
    int version; 
    String usrId, name, firstName, lastName, company;
  
    public Person(){ 
    }
    
    public void setUsrId(String usrId) {
    	this.usrId = usrId;
    }
    
  
    public String getUsrId() {
    	return usrId;
    }
    
    public void setVersion(int version) {
    	this.version = version;
    }
    
    public int getVersion() {
    	return version;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
    	return name;
    }
    
    
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }
    
    public String getFirstName() {
    	return firstName;
    }
    
    
    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }
    
    public String getLastName() {
    	return lastName;
    }
    
    public void setCompany(String company) {
    	this.company = company;
    }
    
    public String getCompany() {
    	return company;
    }
  
    public String toString() {  
        return this.company + " " + this.usrId + " " + this.version + " " + this.lastName + " " + this.firstName; 
    } 
}
