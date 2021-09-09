package com.app.search.service;

import java.util.List;
import com.app.exception.BusinessException;
import com.app.model.Products;
import com.app.model.Cart;
import com.app.model.Orders;

public interface CustomerSearchService {
	public boolean registernewaccount() throws BusinessException;
	public boolean login(String id,String password) throws BusinessException;
	public List<Products> viewallproducts() throws BusinessException;
	public Double getcarttotal(String customerid) throws BusinessException;
	public List<Cart> viewcart(String id) throws BusinessException;
	public boolean addtocart(List<Integer> plist, String id) throws BusinessException;
	
	public List<Orders> vieworders(String id) throws BusinessException;
	public Double getordertotal(String customerid) throws BusinessException;
	public boolean updateorderbycustomer(List<Integer> plist, String id) throws BusinessException;
	
	public boolean fromCartTOorders(List<Integer> clist, String id) throws BusinessException;
}
