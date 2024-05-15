package com.neo.predicate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Task2 {
	
	public static void main(String[] args) {
		
		List<String> l= Arrays.asList("Kajal","Samantha","Anushka","Trisha","Anupama","Aishwarya");
		
		Predicate<String> pred= (s) -> s.startsWith("A");
		
		for(String name : l) {
          if(pred.test(name))
          {
        	  System.out.println(name);
          }
		}

	}

}
