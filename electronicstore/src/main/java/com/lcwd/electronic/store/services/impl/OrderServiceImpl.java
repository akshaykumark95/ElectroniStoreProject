package com.lcwd.electronic.store.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.OrderUpdateRequest;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.Order;
import com.lcwd.electronic.store.entities.OrderItem;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.exceptions.ResourseNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.OrderRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
//import com.lcwd.electronic.store.services.OrderService;
import com.lcwd.electronic.store.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public OrderDto createOrder(CreateOrderRequest orderDto) {
		
		String userId=orderDto.getUserId();
		String cartId=orderDto.getCartId();
		
		//fetch user
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User is not available with given Id !!"));
		
		//fetch cart
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourseNotFoundException("Cart is not available with given Id !!"));
		List<CartItem> cartItems = cart.getItems();
		
		if(cartItems.size()<=0) {
			throw new BadApiRequest("Invalid number of cart items");
		}
		
		Order order = Order.builder()
			.billingName(orderDto.getBillingName())
			.billingPhone(orderDto.getBillingPhone())
			.billingAddress(orderDto.getBillingAddress())
			.orderDate(new Date())
			.deliveredDate(null)
			.paymentStatus(orderDto.getPaymentStatus())
			.orderStatus(orderDto.getOrderStatus())
			.orderId(UUID.randomUUID().toString())
			.user(user)
			.build();
		
		//orderItems, amount
		
		//cartItem->orderItem
		AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
		List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
			OrderItem orderItem = OrderItem.builder()
				.quantity(cartItem.getQuantity())
				.product(cartItem.getProduct())
				.totalPrice(cartItem.getQuantity()*cartItem.getProduct().getDiscountedPrice())
				.order(order)
				.build();
			orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());
			return orderItem;
		}).collect(Collectors.toList());
		
		order.setOerderItems(orderItems);
		order.setOrderAmount(orderAmount.get());
		
		cart.getItems().clear();
		cartRepository.save(cart);
		Order savedOrder = orderRepository.save(order);
			
		return mapper.map(savedOrder, OrderDto.class);
	}

	@Override
	public void removeOrder(String orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourseNotFoundException("Order not found !!"));
		orderRepository.delete(order);
	}

	@Override
	public List<OrderDto> getOrderOfUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourseNotFoundException("User is not found with given id !!"));
		List<Order> orders = orderRepository.findByUser(user);
		List<OrderDto> orderDtos = orders.stream().map(order -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
		return orderDtos;
	}

	@Override
	public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Order> page = orderRepository.findAll(pageable);
		return Helper.getPageableResponse(page, OrderDto.class);
	}

	@Override
	public OrderDto updateOrder(String orderId, OrderUpdateRequest request) {
		Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourseNotFoundException("Order not found with given id !!"));
		order.setBillingAddress(request.getBillingAddress());
		order.setBillingName(request.getBillingName());
		order.setBillingPhone(request.getBillingPhone());
		order.setOrderStatus(request.getOrderStatus());
		order.setPaymentStatus(request.getPaymentStatus());
		order.setDeliveredDate(request.getDeliveredDate());
		Order updatedOrder= orderRepository.save(order);
		return mapper.map(updatedOrder, OrderDto.class);
	}

}
