package com.neo.beans;

import java.util.Optional;

public class Demo {
	
	public static void main(String[] args) {
		

		EmployeeService service = new EmployeeService();
//		Employee emp = service.getEmpById(101);
		
		Optional<Employee> empId = service.getEmpById(101);
		
		if(empId.isPresent()) {
			Employee employee = empId.get();
			System.out.println(employee.getEmpName());
		}
		
		else {
			System.out.println("With the given Id no record found");
		}
	}
	

}
