package com.lcwd.electronic.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.electronic.store.dtos.ApiResponceMessage;
import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.OrderUpdateRequest;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	//create
	@PostMapping
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request){
		OrderDto orderDto = orderService.createOrder(request);
		return new ResponseEntity<OrderDto>(orderDto,HttpStatus.CREATED);
	}
	
	//delete
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponceMessage> removeOrder(@PathVariable String orderId){
		orderService.removeOrder(orderId);
		ApiResponceMessage responceMessage = ApiResponceMessage.builder()
				.message("Order removed !!")
				.status(HttpStatus.OK)
				.success(true)
				.build();
		return new ResponseEntity<ApiResponceMessage>(responceMessage,HttpStatus.OK);	
	}
	
	//get orders of user
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable String userId){
		List<OrderDto> orderOfUser = orderService.getOrderOfUser(userId);
		return new ResponseEntity<List<OrderDto>>(orderOfUser,HttpStatus.OK);
	}
	
	//get orders
	@GetMapping
	public ResponseEntity<PageableResponse<OrderDto>> getOrder(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "orderDate", required = false) String sortBy,
			@RequestParam(value = "sortPage", defaultValue = "desc", required = false) String sortDir
			){
		PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	//update order
	@PutMapping("/{orderId}")
	public ResponseEntity<OrderDto> updateOrder(@PathVariable String orderId, @RequestBody OrderUpdateRequest request){
		OrderDto updateOrder = orderService.updateOrder(orderId, request);
		return new ResponseEntity<OrderDto>(updateOrder,HttpStatus.OK);	
	}

}
