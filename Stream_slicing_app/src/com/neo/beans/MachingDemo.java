package com.neo.beans;

import java.util.Arrays;
import java.util.List;

public class MachingDemo {
	
	public static void main(String[] args) {
		
		Person p1 =new Person(1, "Akshay", "India");
		Person p2= new Person(2, "Sidheshwar", "India");
		Person p3= new Person(3, "Andrue", "UK");
		Person p4= new Person(4, "Bravo", "Afrika");
		
		List<Person> p = Arrays.asList(p1,p2,p3,p4);
		
		boolean isIndianAvailable = p.stream()
		 .anyMatch(a -> a.getCountry().equals("India"));
		
		System.out.println("Is Indain Available: "+isIndianAvailable);
		
		
	}

}

class Person {
	private int id;
	private String name;
	private String country;
	public Person(int id, String name, String country) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", country=" + country + "]";
	}
	
	
}
