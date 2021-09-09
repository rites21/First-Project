package com.app.model;

public class Cart {

private int cartid;
private int product_id;
private String product_name;
private String product_type;
private String product_company;
private Double product_price;
private String customer_id;

public int getCartid() {
	return cartid;
}
public void setCartid(int cartid) {
	this.cartid = cartid;
}
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
public String getProduct_company() {
	return product_company;
}
public void setProduct_company(String product_company) {
	this.product_company = product_company;
}
public Double getProduct_price() {
	return product_price;
}
public void setProduct_price(Double product_price) {
	this.product_price = product_price;
}
public String getCustomer_id() {
	return customer_id;
}
public void setCustomer_id(String customer_id) {
	this.customer_id = customer_id;
}
@Override
public String toString() {
	return "[Cartid= "+cartid+", CustomerId= "+customer_id+", ProductId= "+product_id+", ProductPrice= "+product_price+", ProductName= "+product_name+", ProductType= "+product_type+", ProductCompany= "+product_company+"]";
}
}