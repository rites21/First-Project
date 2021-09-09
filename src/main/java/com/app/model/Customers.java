package com.app.model;

public class Customers {

private String customerid;
private String firstname;
private String lastname;
private String email;
private String address;
private String password;
private String gender;
private String contactno;
public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getAddress() {
	return address;
}
public void setAddress(String addr) {
	address = addr;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getCustomerId() {
	return customerid;
}
public void setCustomerId(String Id) {
	customerid = Id;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getContactno() {
	return contactno;
}
public void setContactno(String contactno) {
	this.contactno = contactno;
}
@Override
public String toString() {
	return "Customer [CustomerId=" + customerid + ", Password=" + password + ", FirstName=" + firstname + ", LastName=" + lastname +", Email="+ email +", gender=" + gender
			+ ", Address=" + address + ", ContactNo=" + contactno + "]";
}
}