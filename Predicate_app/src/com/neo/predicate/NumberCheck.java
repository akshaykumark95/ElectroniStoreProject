package com.neo.predicate;

import java.util.function.Predicate;

public class NumberCheck {
	public static void main(String[] args) {
		
		Predicate<Integer> predicate = (number) -> number>20;
		System.out.println(predicate.test(15));
	}

}
