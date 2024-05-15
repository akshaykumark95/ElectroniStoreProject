package com.neo.beans;

import java.util.StringJoiner;

public class StringJoinerDemo {
	
	public static void main(String[] args) {
		
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("Akshay");
		joiner.add("Kumar");
		joiner.add("Ashok");
		joiner.add("Khedkar");
		
		System.out.println(joiner);
	}

}
