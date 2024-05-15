package com.neo.predicate;

import java.util.function.Predicate;

public class Task {
	
	public static void main(String[] args) {
		
	Predicate<String> pre =	(name) -> name.length() >=7;
	System.out.println(pre.test("Akshaykumar"));
	}

}
