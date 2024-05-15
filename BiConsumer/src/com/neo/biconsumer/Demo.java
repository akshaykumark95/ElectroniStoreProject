package com.neo.biconsumer;

import java.util.function.BiConsumer;

public class Demo {
	
	public static void main(String[] args) {
		
		BiConsumer<String, String> bc = (agr1, agr2) -> {
			System.out.println("My name is "+agr1);
			System.out.println("I'm from "+ agr2);
		};
		bc.accept("Akshaykumar", "Parbhani");
	}

}
