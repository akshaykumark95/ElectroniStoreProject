package com.lcwd.electronic.store.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

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
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${product.image.path}")
	private String imagePath;
	
	Logger logger=LoggerFactory.getLogger(ProductController.class);
	
	//create
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
		ProductDto createdProduct = productService.create(productDto);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto){
		ProductDto updatedProduct = productService.update(productId, productDto);
		return new ResponseEntity<ProductDto>(updatedProduct, HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponceMessage> deleteProduct(@PathVariable String productId) {
		productService.delete(productId);
		ApiResponceMessage responseMessage = ApiResponceMessage.builder().message("Product deleted successfully !! ").status(HttpStatus.OK).success(true).build();
		return new ResponseEntity<ApiResponceMessage>(responseMessage, HttpStatus.OK);
	}
	
	//get single product
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
		ProductDto productDto = productService.get(productId);
		return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);
	}
	
	//get all products
	@GetMapping
	public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortPage", defaultValue = "desc", required = false) String sortDir
			){
		logger.info("data check");
		PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<ProductDto>>(pageableResponse, HttpStatus.OK);
		//logger.info("");
	}
	
	//get all live products
	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortPage", defaultValue = "desc", required = false) String sortDir
			){
		PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<ProductDto>>(pageableResponse, HttpStatus.OK);
	}
	
	//search product
	@GetMapping("/search/{query}")
	public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
			@PathVariable String query,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortPage", defaultValue = "desc", required = false) String sortDir
			){
		PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PageableResponse<ProductDto>>(pageableResponse, HttpStatus.OK);
	}
	
	//upload product image
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponse> uploadProductImage(
			@RequestParam("productImage") MultipartFile image, @PathVariable String productId
			) throws IOException{
		String imageName = fileService.uploadFile(image, imagePath);
		ProductDto product = productService.get(productId);
		product.setProductImageName(imageName);
		ProductDto productDto = productService.update(productId, product);
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("Product image uploaded successfully !!").success(true).status(HttpStatus.CREATED).build();
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}
	
	//server product 
	@GetMapping("/image/{productId}")
	public void serverProductImage(@PathVariable String productId ,HttpServletResponse response) throws IOException {
		ProductDto product = productService.get(productId);
		InputStream resource = fileService.getResource(imagePath, product.getProductImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}


}
