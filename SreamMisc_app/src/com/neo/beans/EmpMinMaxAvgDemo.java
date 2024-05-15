package com.neo.beans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmpMinMaxAvgDemo {
	
	public static void main(String[] args) {
		
		Employee e1=new Employee("AKshaykumar", 28, 82000);
		Employee e2=new Employee("Sidheshwar", 25, 67000);
		Employee e3=new Employee("Saurabh", 26, 52000);
		Employee e4=new Employee("Akash", 29, 60000);
		Employee e5=new Employee("Sagar", 28, 43000);
		
		List<Employee> emp=new ArrayList<>();
		emp.add(e1);
		emp.add(e2);
		emp.add(e3);
		emp.add(e4);
		emp.add(e5);
		
		Double avgSalary = emp.stream().collect(Collectors.averagingInt(a-> a.getSalary()));
		System.out.println("Average salay ::"+avgSalary);
		
		 Optional<Employee> minSalary = emp.stream().collect(Collectors.minBy(Comparator.comparing(Employee::getSalary)));
		 System.out.println("Minimum salary ::"+minSalary.get());
		
		 Optional<Employee> maxSalary = emp.stream().collect(Collectors.maxBy(Comparator.comparing(Employee::getSalary)));
		 System.out.println("Maximum salary is ::"+maxSalary.get());
	}

}
