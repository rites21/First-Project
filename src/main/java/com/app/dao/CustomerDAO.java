package com.app.dao;

import java.util.List;

import com.app.exception.BusinessException;
import com.app.model.Customers;
import com.app.model.Products;
import com.app.model.Cart;
import com.app.model.Orders;

public interface CustomerDAO {

	public int checkifidexists(String str) throws BusinessException;
	public boolean registernewaccount(Customers customer) throws BusinessException;
	public boolean validatelogin(String id,String password) throws BusinessException;
	public boolean addproductstocart(List<Integer> plist,String customerid) throws BusinessException;
	public Double getcarttotal(String customerid) throws BusinessException;
	public List<Products> showallproducts() throws BusinessException;
	public List<Cart> viewcartitems(String idcustomer) throws BusinessException;
	public boolean updateorderbycustomer(List<Integer> plist,String customerid) throws BusinessException;
	public Double getordertotal(String customerid) throws BusinessException;
	public List<Orders> vieworders(String idcustomer) throws BusinessException;
	
	public boolean fromCartTOorders(List<Integer> clist, String id) throws BusinessException;
}
