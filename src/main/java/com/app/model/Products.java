package com.app.model;

public class Products {
private int product_id;
private String product_name;
private String product_type;
private double product_price;
private int product_quantity;
private String product_company;
public int getProduct_id() {
	return product_id;
}
public void setProduct_id(int product_id) {
	this.product_id = product_id;
}
public String getProduct_name() {
	return product_name;
}
public void setProduct_name(String product_name) {
	this.product_name = product_name;
}
public String getProduct_type() {
	return product_type;
}
public void setProduct_type(String product_type) {
	this.product_type = product_type;
}
public double getProduct_price() {
	return product_price;
}
public void setProduct_price(double product_price) {
	this.product_price = product_price;
}
public int getProduct_quantity() {
	return product_quantity;
}
public void setProduct_quantity(int product_quantity) {
	this.product_quantity = product_quantity;
}
public String getProduct_company() {
	return product_company;
}
public void setProduct_company(String product_company) {
	this.product_company = product_company;
}

@Override
public String toString() {
	return "Product [ProductId=" + product_id + ", Product Name=" + product_name + ", ProductType=" + product_type + ", ProductPrice=" + product_price +", ProductQuantity="+ product_quantity +", ProductCompany=" + product_company + "]";
}

}
