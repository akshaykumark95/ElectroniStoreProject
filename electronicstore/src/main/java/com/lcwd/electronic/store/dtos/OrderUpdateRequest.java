package com.lcwd.electronic.store.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdateRequest {
	
	private String orderStatus;
	private String paymentStatus;
	private String billingAddress;
	private String billingPhone;
	private String billingName;
	private Date deliveredDate;

}
