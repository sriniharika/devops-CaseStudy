package com.myapp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="products")

public class Product {
	
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", category=" + category + ", productName=" + productName
				+ ", description=" + description + ", price=" + price + ", quantity=" + quantity + "]";
	}

	@Id
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@Column(name="CATEGORY",nullable=false)
	private String category;
	
	@Column(name="PRODUCT_NAME",nullable=false)
	private String productName;
	
	@Column(name="PRODUCT_DESCRIPTION",nullable=false)	
	private String description;
	
	@Column(name="PRODUCT_PRICE",nullable=false)
	private Double price;
	
	@Column(name="PRODUCT_QUANTITY",nullable=false)
	private Integer quantity;
	
	
	public String getCategory() {
		return category;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Product() {
		
	}

	public Product(String category,String productName, String description, Double price, Integer quantity) {
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.category = category;
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	
	

}
