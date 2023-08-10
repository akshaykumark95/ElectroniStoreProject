package com.lcwd.electronic.store.controller;

import java.io.IOException;
import java.io.InputStream;

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
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.ImageResponse;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.services.CategoryService;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.ProductService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${category.profile.image.path}")
	private String imageUploadPath;
	
	private Logger logger=LoggerFactory.getLogger(CategoryController.class);
	
	//create
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto categoryDto1 = categoryService.create(categoryDto);
		return new ResponseEntity<CategoryDto>(categoryDto1,HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId,@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto updatedDto = categoryService.update(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updatedDto,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponceMessage> deleteCategory(@PathVariable String categoryId){
		categoryService.delete(categoryId);
		ApiResponceMessage message=ApiResponceMessage.builder().message("Category deleted suucessfully !!").success(true).status(HttpStatus.OK).build();
		return new ResponseEntity<ApiResponceMessage>(message,HttpStatus.OK);
	}
	
	//get all
	@GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>> getAll(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortPage", defaultValue = "asc", required = false) String sortDir
			){
		PageableResponse<CategoryDto> pageableResponse= categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<CategoryDto>>(pageableResponse,HttpStatus.OK);
	}
	
	//get single category
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
		CategoryDto categoryDto = categoryService.get(categoryId);
		return ResponseEntity.ok(categoryDto);
	}
	
	//upload category image
	@PostMapping("/image/{categoryId}")
	public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile image, @PathVariable String categoryId) throws IOException{
		String imageName = fileService.uploadFile(image, imageUploadPath);
		CategoryDto category = categoryService.get(categoryId);
		category.setCoverImage(imageName);
		CategoryDto categoryDto = categoryService.update(category, categoryId);
		ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).message("Cover image uploaded successfully !!").success(true).status(HttpStatus.CREATED).build();
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}
	
	//serve cover image
	@GetMapping("/image/{categoryId}")
	public void serverCoverImage(@PathVariable String categoryId ,HttpServletResponse response) throws IOException {
		CategoryDto category = categoryService.get(categoryId);
		logger.info("Cover image name {} :",category.getCoverImage());
		InputStream resource = fileService.getResource(imageUploadPath, category.getCoverImage());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	//create product with category
	@PostMapping("/{categoryId}/products")
	public ResponseEntity<ProductDto> createProductWithCategory(
			@PathVariable String categoryId, @RequestBody ProductDto dto
			){
		ProductDto productWithCategory = productService.createProductWithCategory(dto, categoryId);
		return new ResponseEntity<ProductDto>(productWithCategory,HttpStatus.CREATED);
	}
	
	//assign category to the product
	@PutMapping("/{categoryId}/products/{productId}")
	public ResponseEntity<ProductDto> assignCategoryToProduct(@PathVariable String categoryId, @PathVariable String productId){
		ProductDto assignCategoryToProduct = productService.assignCategoryToProduct(productId, categoryId);
		return new ResponseEntity<>(assignCategoryToProduct, HttpStatus.OK);
	}
	
	//get product by category
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<PageableResponse<ProductDto>> getProductByCategory(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortPage", defaultValue = "asc", required = false) String sortDir,
			@PathVariable String categoryId
			){
		PageableResponse<ProductDto> pageableResponse= productService.getAllOfCategory(categoryId,pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<ProductDto>>(pageableResponse,HttpStatus.OK);
	} 

}
