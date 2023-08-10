package com.lcwd.electronic.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.ApiResponceMessage;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.services.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request){
		CartDto cartDto = cartService.addItemToCart(userId, request);
		return new ResponseEntity<>(cartDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}/items/{itemId}")
	public ResponseEntity<ApiResponceMessage> removeItemFromCart(@PathVariable String userId, @PathVariable int itemId){
		cartService.removeItemFromCart(userId, itemId);
		ApiResponceMessage responce = ApiResponceMessage.builder()
							.message("Item removed from the cart !!")
							.success(true)
							.status(HttpStatus.OK)
							.build();
		return new ResponseEntity<ApiResponceMessage>(responce, HttpStatus.OK);
	}
	
	//clear cart
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponceMessage> clearCart(@PathVariable String userId ){
		cartService.clearCart(userId);
		ApiResponceMessage responce = ApiResponceMessage.builder()
							.message("Now cart is empty !!")
							.success(true)
							.status(HttpStatus.OK)
							.build();
		return new ResponseEntity<ApiResponceMessage>(responce, HttpStatus.OK);
	}
	
	//get cart from user
	@GetMapping("/{userId}")
	public ResponseEntity<CartDto> getCart(@PathVariable String userId ){
		CartDto cartDto = cartService.getCartByUser(userId);
		return new ResponseEntity<>(cartDto,HttpStatus.OK);
	}

}
