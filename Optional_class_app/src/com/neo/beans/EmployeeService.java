package com.neo.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeService {
	
	private Map<Integer, Employee> empMap = new HashMap<>();
	
	/*
	 * public Employee getEmpById(int empId) { return empMap.get(empId); }
	 */
	
	public Optional<Employee> getEmpById(int empId) {
		Employee emp = empMap.get(empId);
		Optional<Employee> optionalEmp = Optional.ofNullable(emp);
		return optionalEmp;
	}

}
