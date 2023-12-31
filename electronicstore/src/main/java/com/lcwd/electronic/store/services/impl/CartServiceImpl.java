package com.lcwd.electronic.store.services.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.exceptions.ResourseNotFoundException;
import com.lcwd.electronic.store.repositories.CartItemRepository;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.CartService;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ModelMapper mapper; 

	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
		String productId = request.getProductId();
		int quantity = request.getQuantity();
		
		if(quantity<=0) {
			throw new BadApiRequest("Requested quantity is not valid");
			}
		
		//fetch product
		Product product = productRepository.findById(productId).orElseThrow(() -> new ResourseNotFoundException("Product is not found in database !!"));
		//fetch user
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User is not found in database !!"));
		
		Cart cart=null;
		try {
			    cart = cartRepository.findByUser(user).get();
		}
		catch (NoSuchElementException e) {
			cart=new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setCreatedAt(new Date());
		}
		
		
		//perform cart operation
		//if cart items already present then update item
		AtomicReference<Boolean> updated=new AtomicReference<>(false);
		 List<CartItem> items = cart.getItems();
		 items =  items .stream().map(item -> {
			 
			 if(item.getProduct().getProductId().equals(productId)) {
				 //item already present in cart
				 item.setQuantity(quantity);
				 item.setTotalPrice(quantity*product.getDiscountedPrice());
				 updated.set(true);
			 }
			 return item;
		 }).collect(Collectors.toList());
		 
		// cart.setItems(updatedItems);
		 		
		
		//create items
		if(!updated.get()) {
			CartItem cartItems = CartItem.builder()
					.quantity(quantity)
					.totalPrice(quantity*product.getDiscountedPrice())
					.cart(cart)
					.product(product)
					.build();
		cart.getItems().add(cartItems);
		}
		
		
		cart.setUser(user);
		Cart updatedCart = cartRepository.save(cart);
		return mapper.map(updatedCart, CartDto.class);
	}

	@Override
	public void removeItemFromCart(String userId, int cartItemId) {
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()-> new ResourseNotFoundException("Item is not found !!"));
		cartItemRepository.delete(cartItem);
	}

	@Override
	public void clearCart(String userId) {	
		//fetch user
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User is not found in database !!"));
		Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourseNotFoundException("cart is found with given user !!"));
		cart.getItems().clear();
		cartRepository.save(cart);
	}

	@Override
	public CartDto getCartByUser(String userId) {
		//fetch user
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User is not found in database !!"));
		Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourseNotFoundException("cart is found with given user !!"));
		return mapper.map(cart, CartDto.class);
	}

}
