package com.lcwd.electronic.store.services;

import java.util.List;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;


public interface UserService {
	
	//create
	UserDto createUser(UserDto userDto);
	
	//update
	UserDto updateUser(UserDto userDto,String userId);
	
	//delete
	void deleteUser(String userId);
	
	//find all
	PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//find by id
	UserDto findById(String userId);
	
	//find by email
	UserDto findByEmail(String email);
	
	//search
	List<UserDto> searchUser(String keyword);

}
