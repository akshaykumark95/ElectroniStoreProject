package com.lcwd.electronic.store.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductDto {
	
	private String productId;
	private String title;
	private String description;
	private int price;
	private int discountedPrice;
	private Date addedDate;
	private int quantity;
	private boolean live;
	private boolean stock;
	private String productImageName;
	private CategoryDto category;

}
