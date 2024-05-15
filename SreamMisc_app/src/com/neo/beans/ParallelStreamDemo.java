package com.neo.beans;

import java.util.stream.Stream;

public class ParallelStreamDemo {
	
	public static void main(String[] args) {
		
		Stream<Integer> stream = Stream.of(2,3,6,67,32,34,67,12,39,3);
		stream.forEach(s->System.out.println(s+" :: "+Thread.currentThread().getName()));
		
		System.out.println("-----------Parallel Stream-----------------");
		Stream<Integer> stream1 = Stream.of(2,3,6,67,32,34,67,12,39,3);
		stream1.parallel().forEach(s->System.out.println(s+" :: "+Thread.currentThread().getName()));
	}

}
