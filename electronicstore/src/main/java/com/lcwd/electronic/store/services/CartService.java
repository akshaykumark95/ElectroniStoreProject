package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {
	//add items to cart
	//case 1: cart for user is not available:create cart and then add items
	//case 2: cart available add the items to the cart
	CartDto addItemToCart(String userId, AddItemToCartRequest request);
	
	//remove item from cart
	void removeItemFromCart(String userId, int cartItemId);
	
	//clear cart
	void clearCart(String userId);
	
	//get cart of user
	CartDto getCartByUser(String userId);
	

}
