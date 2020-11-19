package com.spring.conv.service;

public class GSVO {
	private String productName;
	private String productPrice;
	private String productStock;
	private String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductStock() {
		return productStock;
	}
	public void setProductStock(String productStock) {
		this.productStock = productStock;
	}
	@Override
	public String toString() {
		return "GSVO [productName=" + productName + ", productPrice=" + productPrice + ", productStock=" + productStock
				+ "]";
	}
	
}
