package com.neo.beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;

public class TestDemo {
	
	public static void main(String[] args) {
		
		Date date1= new Date();
		System.out.println(date1);
		
		LocalDate now = LocalDate.now();
		System.out.println(now);
		
		LocalDate date = LocalDate.of(1995, 05, 13);
		System.out.println(date);
		
		date= LocalDate.of(2024, Month.MAY, 15);
		System.out.println(date);
		
		//converting String to date using parse
		date = LocalDate.parse("1995-05-13");
		System.out.println(date);
		
		//adding 4 days to given date
		date = date.plusDays(4);
		System.out.println(date);
		
		//adding 4 months to given date
		date = date.plusMonths(4);
		System.out.println(date);
		
		LocalTime now1 = LocalTime.now();
		System.out.println(now1);
		
		//check date is before given date
		boolean isBefore = LocalDate.parse("2024-05-11").isBefore(LocalDate.parse("2023-09-03"));
		System.out.println(isBefore);
		
		//to check date after the given date
		boolean isAfter = LocalDate.parse("2024-05-11").isAfter(LocalDate.parse("2023-03-15"));
		System.out.println(isAfter);
		
		LocalTime time = LocalTime.of(12, 50, 04);
		System.out.println(time);
		
		time = LocalTime.parse("23:31:24");
		System.out.println(time);
		
		time = time.plusSeconds(45);
		System.out.println(time);
		
		time = time.plusMinutes(23);
		System.out.println(time);
		
		time = time.plusHours(2);
		System.out.println(time);
		
		Period p = Period.between(LocalDate.parse("1998-10-11"), LocalDate.now());
		System.out.println(p);
		
		
	}

}
