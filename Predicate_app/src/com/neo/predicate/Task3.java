package com.neo.predicate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Task3 {
	
	public static void main(String[] args) {
		
		Person p1 = new Person("Raju", 20);
		Person p2 = new Person("Nick", 10);
		Person p3 = new Person("Rani", 21);
		Person p4 = new Person("Sita", 19);
		
		List<Person> persons = Arrays.asList(p1,p2,p3,p4);
		
		Predicate<Person> agePrediacte = (person) -> person.getAge() >= 18;
		
		for(Person p : persons) {
			
			if(agePrediacte.test(p)){
				System.out.println(p.getName());
			}
			
		}
		
	}

}
