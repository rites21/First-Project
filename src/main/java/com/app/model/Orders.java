package com.app.model;

public class Orders {
private int orderid;
private int productid;
private String productname;
private String customerid;
private double total;
private String customerstatus;
private String sellerstatus;
public int getOrderid() {
	return orderid;
}
public void setOrderid(int orderid) {
	this.orderid = orderid;
}
public int getProductid() {
	return productid;
}
public void setProductid(int productid) {
	this.productid = productid;
}
public String getProductname() {
	return productname;
}
public void setProductname(String productname) {
	this.productname = productname;
}
public String getCustomerid() {
	return customerid;
}
public void setCustomerid(String customerid) {
	this.customerid = customerid;
}
public double getTotal() {
	return total;
}
public void setTotal(double total) {
	this.total = total;
}
public String getCustomerstatus() {
	return customerstatus;
}
public void setCustomerstatus(String customerstatus) {
	this.customerstatus = customerstatus;
}
public String getSellerstatus() {
	return sellerstatus;
}
public void setSellerstatus(String sellerstatus) {
	this.sellerstatus = sellerstatus;
}
@Override
public String toString() {
	return "Orders [orderid=" + orderid + ", productid=" + productid + ", productname=" + productname + ", customerid="+ customerid + ", total=" + total + ", customerstatus=" + customerstatus + ", sellerstatus=" + sellerstatus+"]";
}
}