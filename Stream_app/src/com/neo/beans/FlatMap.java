package com.neo.beans;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FlatMap {
	
	public static void main(String[] args) {
		
		List<String> javaCourses = Arrays.asList("Core java", "Adv Java");
		List<String> pythonCourses = Arrays.asList("Core", "adv");
		List<String> cloudCourses =  Arrays.asList("Devops", "AWS", "Azure");
		
		List<List<String>> allCourses = Arrays.asList(javaCourses, pythonCourses, cloudCourses);
		Stream<List<String>> stream1 = allCourses.stream();
		Stream<String> flatMap = stream1.flatMap(s->s.stream());
		flatMap.forEach(System.out::println);
	}

}
