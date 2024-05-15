package com.neo.function;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Demo {
	
	public static void main(String[] args) {
		
		Function<String, String> f = (name) -> name.toUpperCase();
		System.out.println(f.apply("Akshaykumar"));
		
		Function<Integer, Integer> f1 = (i) -> (i+i);
		System.out.println(f1.apply(10));
		
		BiFunction<Integer, Integer, Integer> f2 = (i,j) -> (i*j);
		System.out.println(f2.apply(20, 30));
	}

}
