package com.lcwd.electronic.store.services;

import java.util.List;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.OrderUpdateRequest;
import com.lcwd.electronic.store.dtos.PageableResponse;

public interface OrderService {
	
	//create order
	OrderDto createOrder(CreateOrderRequest orderDto);
	
	//remove order
	void removeOrder(String orderId);
	
	//get orders of user
	List<OrderDto> getOrderOfUser(String userId);
	
	//get orders
	PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//update order
	OrderDto updateOrder(String orderId, OrderUpdateRequest request);
	
	//other methods(logic)
	

}
