package com.neo.predicate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class PredicateJoinApp {
	
	public static void main(String[] args) {
		
		Person p1 = new Person("Raju", 20);
		Person p2 = new Person("Nick", 23);
		Person p3 = new Person("Rani", 21);
		Person p4 = new Person("Sita", 19);
		Person p5 = new Person("Rutu", 65);
		
		List<Person> persons = Arrays.asList(p1,p2,p3,p4,p5);
		
		Predicate<Person> lowestLimit = (p) -> p.getAge()>=21;
		Predicate<Person> highestLimit = (p) -> p.getAge()<=64;
		
		for(Person p : persons) {
			Predicate<Person> predicate = lowestLimit.and(highestLimit);
			boolean isEligibleForMerrage = predicate.test(p);
			if(isEligibleForMerrage) {
				System.out.println(p.getName());
			}
		}
	}

}
