package com.neo.bean;

import java.util.function.BiPredicate;

public class Demo {
	
	public static void main(String[] args) {
		
		BiPredicate<Integer, Integer> biPredicate = (i,j) -> (i+j) >=100;
		
		System.out.println(biPredicate.test(10, 20));
	}

}
