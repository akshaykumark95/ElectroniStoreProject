package com.neo.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingApproch {
	
	public static void main(String[] args) {
		
		Person p1= new Person(1,"Akshay", "akshay@gmail.com");
		Person p2= new Person(2,"Sumit", "sumit@gmail.com");
		Person p3= new Person(3,"Rahul", "rahuk@gmail.com");
		Person p4= new Person(4,"Vaibhav", "vaibhav@gmail.com");
		
		List<Person> persons = Arrays.asList(p1,p2,p3,p4);
		
		for(Person p : persons) {
			System.out.println(p);
		}
		//sorting logic
//		Collections.sort(persons, new Comparator<Person>() {
//
//			@Override
//			public int compare(Person p1, Person p2) {
//				// TODO Auto-generated method stub
//				return p1.getPname().compareTo(p2.getPname());
//			}
//		});
		
		Collections.sort(persons, (o1,o2) -> o1.getPname().compareTo(o2.getPname()));
		System.out.println("After sort");
		for(Person p : persons) {
			System.out.println(p);
		}
		
	}

}
