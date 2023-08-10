package com.lcwd.electronic.store.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.exceptions.ResourseNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Value("${product.image.path}")
	private String imagePath;
	
	private Logger logger=LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public ProductDto create(ProductDto productDto) {
		Product product = mapper.map(productDto, Product.class);
		
		// id automatic created
		String productId = UUID.randomUUID().toString();
		product.setProductId(productId);
		
		//added date
		product.setAddedDate(new Date());
		
		Product savedProduct = productRepository.save(product);
		return mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductDto update(String productId, ProductDto productDto) {
		Product product = productRepository.findById(productId).orElseThrow(()-> new ResourseNotFoundException("Product is not found with given Id !!"));
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setQuantity(productDto.getQuantity());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isLive());
		product.setProductImageName(productDto.getProductImageName());
		
		//save the entity
		Product updatedProduct = productRepository.save(product);
		return mapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public void delete(String productId) {
		Product product = productRepository.findById(productId).orElseThrow(()-> new ResourseNotFoundException("Product is not found with given Id !!"));
		
		//delete image
		String fullPath=imagePath+product.getProductImageName();
		try {
			Path path=Paths.get(fullPath);
			Files.delete(path);
		}
		catch(NoSuchFileException ex){
			logger.info("Cover image is not found in this folder");
			ex.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		productRepository.delete(product);	
	}

	@Override
	public ProductDto get(String productId) {
		Product product = productRepository.findById(productId).orElseThrow(()-> new ResourseNotFoundException("Product is not found with given Id !!"));
		return mapper.map(product, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> page=productRepository.findAll(pageable);
		PageableResponse<ProductDto> pageableResponse=	Helper.getPageableResponse(page, ProductDto.class);
		return pageableResponse;
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> page = productRepository.findByLiveTrue(pageable);
		PageableResponse<ProductDto> pageableResponse=	Helper.getPageableResponse(page, ProductDto.class);
		return pageableResponse;
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);
		PageableResponse<ProductDto> pageableResponse=	Helper.getPageableResponse(page, ProductDto.class);
		return pageableResponse;
	}

	@Override
	public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourseNotFoundException("Category is not found with given id"));
		
		Product product = mapper.map(productDto, Product.class);
		
		// id automatic created
		String productId = UUID.randomUUID().toString();
		product.setProductId(productId);
		
		//added date
		product.setAddedDate(new Date());
		
		product.setCategory(category);
		
		Product savedProduct = productRepository.save(product);
		return mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductDto assignCategoryToProduct(String productId, String categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourseNotFoundException("Category not found with given id !!"));
		Product product = productRepository.findById(productId).orElseThrow(()-> new ResourseNotFoundException("Product not found with given id !!"));
		product.setCategory(category);
		Product updatedProduct = productRepository.save(product);
		return mapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourseNotFoundException("Category not found with given id !!"));
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Product> page = productRepository.findByCategory(category, pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

}
