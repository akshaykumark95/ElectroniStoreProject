package com.lcwd.electronic.store.exceptions;

import lombok.Builder;

@Builder
public class ResourseNotFoundException extends RuntimeException
{

	public ResourseNotFoundException() {
		super("Resourse not Found !! ");
	}
	
	public ResourseNotFoundException(String msg) {
		super(msg);
	}
}
