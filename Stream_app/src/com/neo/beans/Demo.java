package com.neo.beans;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {
	public static void main(String[] args) {
		
		Stream<Integer> s = Stream.of(2,3,5,7,23,4,1,45,65,11,24,32);
		s.filter(i -> i%2 == 0).forEach(System.out::println);
		
		List<String> l = Arrays.asList("Akshay","Kumar","Ashok","Khedkar","Shital");
		l.stream().filter(s1 -> s1.startsWith("A")).forEach(i -> System.out.println(i));
		l.stream().map(a -> a.toUpperCase()).collect(Collectors.toList()).forEach(System.out::println);
		
		l.stream().filter(i->i.startsWith("A")).map(i -> i.toLowerCase()).forEach(System.out::println);
	}

}
