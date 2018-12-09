package com.tutorial.main;

public class Product<T,M> {
	private T brand;
	private M modelNumber;
	
	public T getBrand() {
		return brand;
	}
	public void setBrand(T brand) {
		this.brand = brand;
	}
	public M getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(M modelNumber) {
		this.modelNumber = modelNumber;
	}
	
}
