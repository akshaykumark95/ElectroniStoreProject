package com.neo.beans;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyApp {
	
	public static void main(String[] args) {
		
		Person p1=new Person(28, "Akshaykumar", "Software Engg");
		Person p2=new Person(21, "Suraj", "Engg");
		Person p3=new Person(34, "Bablu", "Dr");
		Person p4=new Person(31, "Rahul", "Collector");
		Person p5=new Person(22, "Pratik", "Cleark");
		
		List<Person> persons= Arrays.asList(p1,p2,p3,p4,p5);
		persons.stream().filter(p -> p.getAge()>20 && p.getJob().equals("Software Engg")).collect(Collectors.toList());
		
		persons.stream().filter(s->s.getAge()>=22).map(s->s.getJob().toUpperCase()+"::"+s.getName()+"::"+s.getJob().length()).forEach(System.out::println);
	}

}
