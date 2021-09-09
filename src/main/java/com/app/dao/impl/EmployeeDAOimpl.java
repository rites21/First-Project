package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.dao.EmployeeDAO;
import com.app.dao.dbutil.MySqlDbConnection;
import com.app.exception.BusinessException;
import com.app.model.Products;
import com.app.model.Customers;
import com.app.model.Orders;

public class EmployeeDAOimpl implements EmployeeDAO{
	private static Logger log2 = Logger.getLogger(EmployeeDAOimpl.class);
//	@Override
//	public int validateemployee(String empname, String emppassword) throws BusinessException{
//		try(Connection connection=MySqlDbConnection.getConnection()){
//			String sql="select * from employeedetails";
//			PreparedStatement preparedStatement=connection.prepareStatement(sql);
//			ResultSet resultSet=preparedStatement.executeQuery();
//			if(resultSet.next())
//			{
//				if((resultSet.getString("employeeusername").equals(empname)) && (resultSet.getString("employeepassword").equals(emppassword)))
//				{return 1;}
//				else
//					{System.out.println("INVALID : PLEASE ENTER CORRECT CREDENIALS"); return 0;}
//			}else {
//				throw new BusinessException("CREDENTIALS DOES NOT EXIST");
//			}
//		} catch (ClassNotFoundException | SQLException e) {
//			//log.error(e);
//			throw new BusinessException("INTERNAL ERROR OCCURED : CONTACT SYSTEM ADMIN");
//		}
//	}
	@Override
	public List<Customers> getallcustomers() throws BusinessException{
		List<Customers> customerlist=new ArrayList<>();
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select * from customers";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Customers c=new Customers();
				c.setCustomerId(resultSet.getString("customerid"));
				c.setPassword(resultSet.getString("password"));
				c.setFirstname(resultSet.getString("firstname"));
				c.setLastname(resultSet.getString("lastname"));
				c.setEmail(resultSet.getString("email"));
				c.setAddress(resultSet.getString("address"));
				c.setGender(resultSet.getString("gender"));
				c.setContactno(resultSet.getString("contactno"));
				customerlist.add(c);
			}
			
			if(customerlist.size()==0) {
				throw new BusinessException("No Customers");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log2.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return customerlist;
	}
	@Override
	public boolean addproduct(Products product) throws BusinessException{
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="Insert into Products(productname,producttype,productprice,productquantity,productcompany) values(?,?,?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,product.getProduct_name());
			preparedStatement.setString(2,product.getProduct_type());
			preparedStatement.setDouble(3,product.getProduct_price());
			preparedStatement.setInt(4,product.getProduct_quantity());
			preparedStatement.setString(5,product.getProduct_company());

			int res=preparedStatement.executeUpdate();
			if(res==1) {
				return true;
			}else {
				throw new BusinessException("Server Error: Could not enter New Product");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log2.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
	}
	@Override
	public int markshipped(Products product) throws BusinessException{
		return 0;
	}
	@Override
	public boolean deleteproduct(int productid) throws BusinessException{
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="Delete from Products where productid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,productid);
			int res=preparedStatement.executeUpdate();
			if(res==1) {
				return true;
			}else {
				throw new BusinessException("Server Error: Could not Delete Product");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log2.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
	}
	@Override
	public boolean modifyproductquantity(int productid, int newquantity) throws BusinessException {
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="Update Products set productquantity=? where productid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,newquantity);
			preparedStatement.setInt(2,productid);

			int res=preparedStatement.executeUpdate();
			if(res==1) {
				return true;
			}else {
				throw new BusinessException("Server Error: Could not update Product quantity");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log2.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
	}//end of method
	@Override
	public boolean updateorderbyemployee(List<Integer> olist) throws BusinessException {
		int ab=0;
		try(Connection connection=MySqlDbConnection.getConnection()){
			for(Integer oid:olist)
			{
				String sql="Update orders set sellerstatus='Shipped' where orderid=?";
				PreparedStatement preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setInt(1,oid);
				int res=preparedStatement.executeUpdate();
				if(res==1) {
					ab++;
				}else {
					ab--;
				}

			}//end of for loop

		} catch (ClassNotFoundException | SQLException e) {
			log2.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		if(ab>0) {return true;} else {return false;}
	}
	@Override
	public List<Orders> viewallorders() throws BusinessException {
		List<Orders> orderlist=new ArrayList<>();
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select * from orders";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Orders or=new Orders();
				or.setCustomerid(resultSet.getString("customerid"));
				or.setOrderid(resultSet.getInt("orderid"));
				or.setProductid(resultSet.getInt("productid"));
				or.setProductname(resultSet.getString("productname"));
				or.setTotal(resultSet.getDouble("total"));
				or.setCustomerstatus(resultSet.getString("customerstatus"));
				or.setSellerstatus(resultSet.getString("sellerstatus"));
				orderlist.add(or);
			}
			
			if(orderlist.size()==0) {
				throw new BusinessException("Order list is Completely Empty");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log2.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return orderlist;
	}
	@Override
	public List<Customers> getallcustomersbyemail(String str) throws BusinessException {
		List<Customers> customerlist=new ArrayList<>();
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select * from customers where email=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,str);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Customers c=new Customers();
				c.setCustomerId(resultSet.getString("customerid"));
				c.setPassword(resultSet.getString("password"));
				c.setFirstname(resultSet.getString("firstname"));
				c.setLastname(resultSet.getString("lastname"));
				c.setEmail(resultSet.getString("email"));
				c.setAddress(resultSet.getString("address"));
				c.setGender(resultSet.getString("gender"));
				c.setContactno(resultSet.getString("contactno"));
				customerlist.add(c);
			}
			
			if(customerlist.size()==0) {
				throw new BusinessException("No Customers");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log2.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return customerlist;
	}
	@Override
	public List<Customers> getallcustomersbycontact(String str) throws BusinessException {
		List<Customers> customerlist=new ArrayList<>();
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select * from customers where contactno=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,str);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Customers c=new Customers();
				c.setCustomerId(resultSet.getString("customerid"));
				c.setPassword(resultSet.getString("password"));
				c.setFirstname(resultSet.getString("firstname"));
				c.setLastname(resultSet.getString("lastname"));
				c.setEmail(resultSet.getString("email"));
				c.setAddress(resultSet.getString("address"));
				c.setGender(resultSet.getString("gender"));
				c.setContactno(resultSet.getString("contactno"));
				customerlist.add(c);
			}
			
			if(customerlist.size()==0) {
				throw new BusinessException("No Customers");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log2.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return customerlist;
	}
	@Override
	public List<Customers> getallcustomersbyname(String str) throws BusinessException {
		List<Customers> customerlist=new ArrayList<>();
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select * from customers where firstname=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,str);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Customers c=new Customers();
				c.setCustomerId(resultSet.getString("customerid"));
				c.setPassword(resultSet.getString("password"));
				c.setFirstname(resultSet.getString("firstname"));
				c.setLastname(resultSet.getString("lastname"));
				c.setEmail(resultSet.getString("email"));
				c.setAddress(resultSet.getString("address"));
				c.setGender(resultSet.getString("gender"));
				c.setContactno(resultSet.getString("contactno"));
				customerlist.add(c);
			}
			
			if(customerlist.size()==0) {
				throw new BusinessException("No Customers");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log2.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return customerlist;
	}
	
}
