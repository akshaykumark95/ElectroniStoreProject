package com.neo.beans;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsDemo {
	
	public static void main(String[] args) {
		
		List<String> name= Arrays.asList("Akshaykumar", "Ashok", "Khedkar");
		
		Map<String,Integer> collect = name.stream().collect(Collectors.toMap(s->s, s->s.length()));
		
		System.out.println(collect);
	}

}
