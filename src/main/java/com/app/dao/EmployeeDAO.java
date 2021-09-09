package com.app.dao;
import java.util.List;

import com.app.exception.BusinessException;
import com.app.model.Products;
import com.app.model.Customers;
import com.app.model.Orders;

public interface EmployeeDAO {
	public boolean addproduct(Products product) throws BusinessException;
	public boolean deleteproduct(int productid) throws BusinessException;
	public boolean modifyproductquantity(int productid,int newquantity) throws BusinessException;
	
	public List<Customers> getallcustomers() throws BusinessException;
	public int markshipped(Products product) throws BusinessException;
	//int validateemployee(String empname, String emppassword) throws BusinessException;
	public boolean updateorderbyemployee(List<Integer> olist) throws BusinessException;
	public List<Orders> viewallorders() throws BusinessException;

	public List<Customers> getallcustomersbyemail(String str) throws BusinessException;
	public List<Customers> getallcustomersbycontact(String str) throws BusinessException;
	public List<Customers> getallcustomersbyname(String str) throws BusinessException;
}
