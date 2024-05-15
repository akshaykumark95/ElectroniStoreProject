package com.neo.beans;

public class User {
	
	private String name;
	private int salary;
	private String country;
	public User(String name, int salary, String country) {
		super();
		this.name = name;
		this.salary = salary;
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", salary=" + salary + ", country=" + country + "]";
	}
	
	

}
