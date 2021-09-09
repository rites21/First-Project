package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.dao.CustomerDAO;
import com.app.dao.dbutil.MySqlDbConnection;
import com.app.exception.BusinessException;
import com.app.model.Cart;
import com.app.model.Customers;
import com.app.model.Orders;
import com.app.model.Products;

public class CustomerDAOimpl implements CustomerDAO{
	
	private static Logger log22 = Logger.getLogger(CustomerDAOimpl.class);

	@Override
	public int checkifidexists(String str) throws BusinessException {
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select * from customers where customerid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,str);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next())
			{return 1;}
			else
				return 0;
		} catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
	}

	@Override
	public boolean registernewaccount(Customers customer) throws BusinessException {
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="Insert into customers(password,firstname,lastname,email,address,gender,contactno,customerid) values(?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,customer.getPassword());
			preparedStatement.setString(2,customer.getFirstname());
			preparedStatement.setString(3,customer.getLastname());
			preparedStatement.setString(4,customer.getEmail());
			preparedStatement.setString(5,customer.getAddress());
			preparedStatement.setString(6,customer.getGender());
			preparedStatement.setString(7,customer.getContactno());
			preparedStatement.setString(8,customer.getCustomerId());
			int res=preparedStatement.executeUpdate();
			if(res==1) {
				return true;
			}else {
				throw new BusinessException("Server Error: Could not enter New Customer");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
	}

	@Override
	public List<Products> showallproducts() throws BusinessException{
		List<Products> prolist=new ArrayList<>();
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select * from products";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Products pr=new Products();
				
				pr.setProduct_id(resultSet.getInt("productid"));
				pr.setProduct_name(resultSet.getString("productname"));
				pr.setProduct_type(resultSet.getString("producttype"));
				pr.setProduct_price(resultSet.getDouble("productprice"));
				pr.setProduct_quantity(resultSet.getInt("productquantity"));
				pr.setProduct_company(resultSet.getString("productcompany"));
				prolist.add(pr);
			}
			
			if(prolist.size()==0) {
				throw new BusinessException("Cart Empty");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return prolist;
	}

	@Override
	public boolean validatelogin(String id, String password) throws BusinessException {
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select * from customers where customerid=? and password=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,id);
			preparedStatement.setString(2,password);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next())
			{return true;}
			else
				return false;	
		}catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
	}

	@Override
	public List<Cart> viewcartitems(String idcustomer) throws BusinessException {
		List<Cart> cartlist=new ArrayList<>();
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select cartid,customerid,cart.productid,productprice,productname,producttype,productcompany from cart Inner Join products ON products.productid=cart.productid where cart.customerid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,idcustomer);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Cart cr=new Cart();
				cr.setCustomer_id(idcustomer);
				cr.setCartid(resultSet.getInt("cartid"));
				cr.setProduct_id(resultSet.getInt("productid"));
				cr.setProduct_price(resultSet.getDouble("productprice"));
				cr.setProduct_name(resultSet.getString("productname"));
				cr.setProduct_type(resultSet.getString("producttype"));
				cr.setProduct_company(resultSet.getString("productcompany"));
				cartlist.add(cr);
			}
			
			if(cartlist.size()==0) {
				throw new BusinessException("Cart Empty");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return cartlist;
	}
	@Override
	public boolean addproductstocart(List<Integer> plist, String customerid) throws BusinessException {
		int ab=0;
		try(Connection connection=MySqlDbConnection.getConnection()){
			for(Integer pid:plist)
			{
				String sql="Insert into cart(customerid,productid) values(?,?)";
				PreparedStatement preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setString(1,customerid);
				preparedStatement.setInt(2,pid);
				int res=preparedStatement.executeUpdate();
				if(res==1) {
					ab++;
				}else {
					ab--;
				}

			}//end of for loop

		} catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		if(ab>0) {return true;} else {return false;}
	}//end of method

	@Override
	public Double getcarttotal(String customerid) throws BusinessException {
		Double total=0.0;
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select sum(productprice) AS Total from cart Inner Join products ON products.productid=cart.productid where cart.customerid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,customerid);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
				{
					total=resultSet.getDouble("total");
				}
			if(total<=0.0) {
				throw new BusinessException("No Cart Items to calculate");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return total;
	}

	@Override
	public boolean updateorderbycustomer(List<Integer> olist, String customerid) throws BusinessException {
		int ab=0;
		try(Connection connection=MySqlDbConnection.getConnection()){
			for(Integer oid:olist)
			{
				String sql="Update orders set customerstatus='Recieved' where customerid=? and orderid=?";
				PreparedStatement preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setString(1,customerid);
				preparedStatement.setInt(2,oid);
				int res=preparedStatement.executeUpdate();
				if(res==1) {
					ab++;
				}else {
					ab--;
				}

			}//end of for loop

		} catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		if(ab>0) {return true;} else {return false;}
	}

	@Override
	public Double getordertotal(String customerid) throws BusinessException {
		Double total=0.0;
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select sum(total) AS TotalBill from orders where customerid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,customerid);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
				{
					total=resultSet.getDouble("TotalBill");
				}
			if(total<=0.0) {
				throw new BusinessException("No Order Items to calculate");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return total;
	}

	@Override
	public List<Orders> vieworders(String idcustomer) throws BusinessException {
		List<Orders> orderlist=new ArrayList<>();
		try(Connection connection=MySqlDbConnection.getConnection()){
			String sql="select * from orders where customerid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,idcustomer);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Orders or=new Orders();
				or.setCustomerid(idcustomer);
				or.setOrderid(resultSet.getInt("orderid"));
				or.setProductid(resultSet.getInt("productid"));
				or.setProductname(resultSet.getString("productname"));
				or.setTotal(resultSet.getDouble("total"));
				or.setCustomerstatus(resultSet.getString("customerstatus"));
				or.setSellerstatus(resultSet.getString("sellerstatus"));
				orderlist.add(or);
			}
			
			if(orderlist.size()==0) {
				throw new BusinessException("Order list for you is Empty");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		return orderlist;
	}

	@Override
	public boolean fromCartTOorders(List<Integer> clist, String id) throws BusinessException {
		int productid,result,ab=0;
		String productname;
		double productprice;
		try(Connection connection=MySqlDbConnection.getConnection()){
			for(Integer cartid:clist)
			{
			String sql="select productid from cart where cartid=? and customerid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,cartid);
			preparedStatement.setString(2,id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				productid=resultSet.getInt("productid");
			}
			else
			{throw new BusinessException("some not available in cart, so process halted, enter again");}
			sql="delete from cart where cartid=? and customerid=?";
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,cartid);
			preparedStatement.setString(2,id);
			result=preparedStatement.executeUpdate();
			if(result==1) 
			{
				//delete successful from cart
				sql="select productname,productprice from products where productid=?";
				preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setInt(1,productid);
				resultSet=preparedStatement.executeQuery();
				if(resultSet.next())
				{
					productname=resultSet.getString("productname");
					productprice=resultSet.getDouble("productprice");
					sql="Insert into orders(productid,productname,customerid,total,customerstatus,sellerstatus) values(?,?,?,?,?,?)";
					preparedStatement=connection.prepareStatement(sql);
					preparedStatement.setInt(1,productid);
					preparedStatement.setString(2,productname);
					preparedStatement.setString(3,id);
					preparedStatement.setDouble(4,productprice);
					preparedStatement.setString(5,"Not Received");
					preparedStatement.setString(6,"Not Shipped");
					int res=preparedStatement.executeUpdate();
					if(res==1)
					{
						ab++;
					}
					else
					{ab--;}
				}
				else {throw new BusinessException("Removed from Cart but Unable to find Product, Add to cart again");}
			}
			else
			{throw new BusinessException("Unable to remove from your cart and add to orders");}
			
			}//for each ends here
		} catch (ClassNotFoundException | SQLException e) {
			log22.error(e);
			throw new BusinessException("Internal error occured contact sysadmin");
		}
		if(ab>0)
			{return true;}
		else {return false;}
	}





}
