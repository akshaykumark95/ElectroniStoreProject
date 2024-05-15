package com.neo.consumer;

import java.util.function.Consumer;

public class Demo {
	
	public static void main(String[] args) {
		
		Consumer<String> c1=(name) -> System.out.println(name+", good morning !!");
		c1.accept("Akshaykumar");
		
		Consumer<Integer> c2 = (i) -> System.out.println(i*i);
		c2.accept(4);
	}

}
