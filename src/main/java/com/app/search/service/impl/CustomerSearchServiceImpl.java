package com.app.search.service.impl;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.app.exception.BusinessException;
import com.app.model.Cart;
import com.app.model.Customers;
import com.app.model.Orders;
import com.app.model.Products;
import com.app.dao.CustomerDAO;
import com.app.dao.impl.CustomerDAOimpl;
import com.app.search.service.CustomerSearchService;

public class CustomerSearchServiceImpl implements CustomerSearchService{
	private static Logger logs = Logger.getLogger(CustomerSearchServiceImpl.class);
	private CustomerDAO customerDAOobject=new CustomerDAOimpl();
	Scanner sc=new Scanner(System.in);

	@Override
	public boolean registernewaccount() throws BusinessException {
		Customers c=new Customers();
		int i=0;
		try{
			logs.info("Enter CustomerId(We will check if its available or not)");
			logs.info("Its Recommended to make an Id with your name with numbers(abc123)");
			do{
				logs.info("Enter here:");
				String cid=sc.nextLine();
				int checkidvalue=customerDAOobject.checkifidexists(cid);
				if(checkidvalue==1)
				{
					logs.info("ID already taken");
				}
				else {
					i=1;
					c.setCustomerId(cid);
				}
			}while(i==0);
			logs.info("password");
			String password=sc.nextLine();
			c.setPassword(password);
			logs.info("firstname");
			String fn=sc.nextLine();
			c.setFirstname(fn);
			logs.info("lastname");
			String ln=sc.nextLine();
			c.setLastname(ln);
			logs.info("email");
			String em=sc.nextLine();
			c.setEmail(em);
			logs.info("address");
			String ad=sc.nextLine();
			c.setAddress(ad);
			logs.info("gender");
			String gd=sc.nextLine();
			c.setGender(gd);
			logs.info("contactno");
			String cn=sc.nextLine();
			c.setContactno(cn);
		}catch(Exception e) {
			throw new BusinessException("Error in inputing details");
		}
		if(customerDAOobject.registernewaccount(c))
			{return true;}
		else
			{return false;}
	}

	@Override
	public boolean login(String id,String password) throws BusinessException {
		try {
			return customerDAOobject.validatelogin(id,password);
			
		}catch(Exception e) {
			throw new BusinessException("Error in login details");
		}
	}

	@Override
	public List<Products> viewallproducts() throws BusinessException {
		List<Products> productlist=customerDAOobject.showallproducts();
		return productlist;
	}
	//sc.close();

	@Override
	public List<Cart> viewcart(String Custid) throws BusinessException {
		
		List<Cart> cartlist=customerDAOobject.viewcartitems(Custid);
		return cartlist;
	}

	@Override
	public boolean addtocart(List<Integer> plist,String id) throws BusinessException {
		if(plist!=null)
			{return customerDAOobject.addproductstocart(plist,id);}
		else
			return false;
	}

	@Override
	public Double getcarttotal(String customerid) throws BusinessException {
		if(customerid!=null)
		{
			return customerDAOobject.getcarttotal(customerid);
		}
		return null;
	}

	@Override
	public List<Orders> vieworders(String id) throws BusinessException {
		List<Orders> orderlist=customerDAOobject.vieworders(id);
		return orderlist;
	}

	@Override
	public Double getordertotal(String customerid) throws BusinessException {
		if(customerid!=null)
		{
			return customerDAOobject.getordertotal(customerid);
		}
		return null;
	}

	@Override
	public boolean updateorderbycustomer(List<Integer> olist, String id) throws BusinessException {
		if(olist!=null)
		{return customerDAOobject.updateorderbycustomer(olist,id);}
	else
		return false;
	}

	@Override
	public boolean fromCartTOorders(List<Integer> clist, String id) throws BusinessException {
		if(clist!=null)
		{return customerDAOobject.fromCartTOorders(clist,id);}
	else
		return false;
	}
	
}
