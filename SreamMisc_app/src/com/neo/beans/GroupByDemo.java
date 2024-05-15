package com.neo.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByDemo {
	
	public static void main(String[] args) {
		
		List<User> list = new ArrayList<>();
		list.add(new User("Akshaykumar", 82000, "India"));
		list.add(new User("Rahul", 80000, "India"));
		list.add(new User("Sagar", 78000, "USA"));
		list.add(new User("Gaurang", 82700, "UK"));
		list.add(new User("Bablu", 32000, "China"));
		list.add(new User("Rohit", 42000, "UK"));
		list.add(new User("Raghu", 56000, "Japan"));
		list.add(new User("Ram", 92000, "USA"));
		
		Map<String,List<User>> collect = list.stream().collect(Collectors.groupingBy(User::getCountry));
		System.out.println(collect);
	}

}
