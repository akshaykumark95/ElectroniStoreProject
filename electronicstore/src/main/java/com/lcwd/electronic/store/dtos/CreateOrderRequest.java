package com.lcwd.electronic.store.dtos;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
	@NotBlank(message = "Cart id is required")
	private String cartId;
	@NotBlank(message = "User id is required")
	private String userId;
	private String orderStatus="PENDING";
	private String paymentStatus="NOTPAID";
	@NotBlank(message = "Address id is required")
	private String billingAddress;
	@NotBlank(message = "Phone number id is required")
	private String billingPhone;
	@NotBlank(message = "Name id is required")
	private String billingName;

}
