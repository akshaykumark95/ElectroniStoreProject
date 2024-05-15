package com.neo.bean;

public class Demo {
	
	public static void m2() {
		System.out.println("This is m2 method");
	}
	
	public static void main(String[] args) {
//		MyIterface myIterface = () -> System.out.println("M1 method");
		MyIterface myIterface = Demo :: m2;
		myIterface.m1();
	}

}

@FunctionalInterface
interface MyIterface {
	public void m1();
}
