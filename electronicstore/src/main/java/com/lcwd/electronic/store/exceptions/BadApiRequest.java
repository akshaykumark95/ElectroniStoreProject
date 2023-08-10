package com.lcwd.electronic.store.exceptions;

import lombok.Builder;

@Builder
public class BadApiRequest extends RuntimeException {
	
	public BadApiRequest() {
		super("Bad Api Request !!");
	}
	
	public BadApiRequest(String msg) {
		super(msg);
	}

}
