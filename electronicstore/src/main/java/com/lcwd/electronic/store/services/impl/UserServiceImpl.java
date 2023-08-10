package com.lcwd.electronic.store.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourseNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${user.profile.image.path}")
	private String imagePath;
	
	Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserDto createUser(UserDto userDto) {
		
		//generate unique id in String format
		String userId=UUID.randomUUID().toString();
		userDto.setUserId(userId);
		//encoding password
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		// dto->entity
		User user=dtoToEntity(userDto);
		User savedUser=userRepository.save(user);
		//entity->dto
		UserDto newDto=entityToDto(savedUser);
		return newDto;
	}


	@Override
	public UserDto updateUser(UserDto userDto,String userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourseNotFoundException("User is not available with given id!!"));
		user.setName(userDto.getName());
		//update email
		user.setAbout(userDto.getAbout());
		user.setGender(userDto.getGender());
		user.setPassword(userDto.getPassword());
		user.setImageName(userDto.getImageName());
		
		//save data
		User updatedUser=userRepository.save(user);
		UserDto updatedDto=entityToDto(updatedUser);
		return updatedDto;
	}

	@Override
	public void deleteUser(String userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourseNotFoundException("User is not available with given id!!"));
		//delete user profile image
		String fullPath=imagePath+user.getImageName();
		try {
			Path path=Paths.get(fullPath);
			Files.delete(path);
		}
		catch (NoSuchFileException ex) {
			logger.info("user image is not found in this folder");
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//delete data
		userRepository.delete(user);
	}

	@Override
	public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
//		pageNumber default starts from 0		
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<User> page=userRepository.findAll(pageable);
		PageableResponse<UserDto> response=Helper.getPageableResponse(page, UserDto.class);
		return response;
	}

	@Override
	public UserDto findById(String userId) {
		User user=userRepository.findById(userId).orElseThrow(()->new ResourseNotFoundException("User is not availabel with given id!!"));
		return entityToDto(user);
	}

	@Override
	public UserDto findByEmail(String email) {
		User user=userRepository.findByEmail(email).orElseThrow(()->new ResourseNotFoundException("User is not available with given email!!"));
		return entityToDto(user);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> users=userRepository.findByNameContaining(keyword);
		List<UserDto> dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
		return dtoList;
	}
	
	private UserDto entityToDto(User savedUser) {
		// TODO Auto-generated method stub
//		UserDto userDto=UserDto.builder()
//				      .userId(savedUser.getUserId())
//				      .name(savedUser.getName())
//				      .email(savedUser.getEmail())
//				      .password(savedUser.getPassword())
//				      .gender(savedUser.getGender())
//				      .about(savedUser.getAbout())
//				      .imageName(savedUser.getImageName()).build();
		return mapper.map(savedUser, UserDto.class);
	}

	private User dtoToEntity(UserDto userDto) {
		
//		User user=User.builder()
//		        .userId(userDto.getUserId())
//		        .name(userDto.getName())
//		        .email(userDto.getEmail())
//		        .password(userDto.getPassword())
//		        .gender(userDto.getGender())
//		        .about(userDto.getAbout())
//		        .imageName(userDto.getImageName()).build();
		return mapper.map(userDto, User.class);
	}

}
