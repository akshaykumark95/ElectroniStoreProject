package com.lcwd.electronic.store.services;

import java.util.List;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;

public interface ProductService {
	
	//create
	ProductDto create(ProductDto productDto);
	
	//update
	ProductDto update(String productId, ProductDto productDto);
	
	//delete
	void delete(String productId);
	
	//get single product
	ProductDto get(String productId);
	
	//get all product
	PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//get live product
	PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//search
	PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//add product with category
	ProductDto createProductWithCategory(ProductDto productDto, String categoryId);
	
	//assign category to the product
	ProductDto assignCategoryToProduct(String productId, String categoryId);
	
	//GET ALL THE PRODUCTS INSIDE THE CATEGORY
	PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);

}
