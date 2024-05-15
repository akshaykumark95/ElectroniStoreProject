package com.neo.beans;

import java.util.Arrays;
import java.util.List;

public class SlicingDemo {
	
	public static void main(String[] args) {
		
		List<String> countries = Arrays.asList("India", "USA", "UK", "China", "India", "USA");
		/*
		 * countries.stream() .distinct() .forEach(System.out::println);
		 */
		
		/*
		 * countries.stream() .limit(4) .forEach(System.out::println);
		 */
		
		countries.stream()
		         .skip(2)
		         .forEach(System.out::println);
	}

}
