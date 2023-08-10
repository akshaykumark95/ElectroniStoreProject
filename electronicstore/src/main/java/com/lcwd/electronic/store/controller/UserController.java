package com.lcwd.electronic.store.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.lcwd.electronic.store.dtos.ApiResponceMessage;
import com.lcwd.electronic.store.dtos.ImageResponse;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	private Logger logger=LoggerFactory.getLogger(UserController.class);
	
	//create
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto userDto1=userService.createUser(userDto);
		return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable String userId,@Valid @RequestBody UserDto userDto){
		UserDto updatedUserDto=userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
		
	}
	
	//delete
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponceMessage> deleteUser(@PathVariable String userId){
		userService.deleteUser(userId);
		ApiResponceMessage message=ApiResponceMessage.builder().message("User deleted successfully!!").success(true).status(HttpStatus.OK).build();
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	//get all
	@GetMapping
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir			
			){
		return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir), HttpStatus.OK);
	}
	
	//get single
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> findById(@PathVariable String userId){
		return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
	}
	
	//get by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> findByEmail(@PathVariable String email){
		return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
	}
	
	//search user
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<UserDto>> findBy(@PathVariable String keywords){
		return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
	}
	
	//upload user image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(
			@RequestParam("userImage") MultipartFile image, @PathVariable String userId
			) throws IOException{
		String imageName=fileService.uploadFile(image, imageUploadPath);
		UserDto user=userService.findById(userId);
		user.setImageName(imageName);
		UserDto userDto=userService.updateUser(user, userId);
		ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).message("Image uploaded successfully").success(true).status(HttpStatus.CREATED).build();
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}
	
	//serve user image
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
		UserDto user=userService.findById(userId);
		logger.info("user image name: {}",user.getImageName());
		InputStream resourse=fileService.getResource(imageUploadPath, user.getImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resourse, response.getOutputStream());
	}

}
