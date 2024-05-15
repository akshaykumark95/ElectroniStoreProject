package com.neo.beans;

public class Person {
	
	private int age;
	private String name;
	private String job;
	public Person(int age, String name, String job) {
		super();
		this.age = age;
		this.name = name;
		this.job = job;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	@Override
	public String toString() {
		return "Person [age=" + age + ", name=" + name + ", job=" + job + "]";
	}
	
	

}
