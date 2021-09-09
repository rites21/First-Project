package com.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import com.app.exception.BusinessException;
import com.app.model.Products;
import com.app.model.Cart;
import com.app.model.Orders;
import com.app.search.service.CustomerSearchService;
import com.app.search.service.impl.CustomerSearchServiceImpl;

public class CustomerLogin {
	private static Logger logc = Logger.getLogger(CustomerLogin.class);

	public static void main(String[] args) {
		CustomerSearchService customersearchserviceobject = new CustomerSearchServiceImpl();
		System.out.println("-------SHOP PROJECT------");
		System.out.println("---CUSTOMER LOGIN PAGE---");
		Scanner sc=new Scanner(System.in);
		String id,password;
		int choice,option=0;
		do {
			logc.info("\n1. Register New Account");
			logc.info("2. Login");
			logc.info("3. Exit");
			logc.info("Enter your choice");
		choice = sc.nextInt();
		switch(choice)
		{
		case 1:logc.info("Registering New Account");
				try{
					boolean answer1=customersearchserviceobject.registernewaccount();
					if(answer1==true) {logc.info("Registration Successful");}
					else {logc.warn("Internal Server Error: Could not register new customer");}
				}catch(BusinessException e) {logc.warn(e.getMessage());}
				break;
		case 2:logc.info("Login");
				logc.info("Enter Your ID");
				id=sc.next();
				logc.info("Enter Password");
				password=sc.next();
				try{
					boolean answer2=customersearchserviceobject.login(id,password);
					if(answer2==true)
					{
						logc.info("Login Successful");
						do {
							logc.info("\n1. View All Products");
							logc.info("2. View your Cart");
							logc.info("3. View your Orders");
							logc.info("4. Log out");
							logc.info("Enter your choice");
							option = sc.nextInt();
							switch(option){
							case 1:logc.info("Showing All Products");
									try{
											List<Products> productlist=customersearchserviceobject.viewallproducts();
											if(productlist!=null && productlist.size()>0) {
												logc.info("Total there are "+productlist.size()+" number of Products you can buy");
												for(Products pr:productlist) {
													logc.info(pr);
												}
											}
											logc.info("1. Add to Cart");
											logc.info("2. Go back");
											logc.info("Enter Choice");
											int i=sc.nextInt();
											if(i==1)
											{
												List<Integer> plist = new ArrayList<Integer>();
												int tempp=0;
												logc.info("Keep Entering ProductId of all Products you want in cart");
												logc.info("Enter 0 when finished");
												logc.info("Enter here:");
												do{
													tempp=sc.nextInt();
													if(tempp>0)
														plist.add(tempp);
													
												}while(tempp>0);
												//here add them to cart
												if(customersearchserviceobject.addtocart(plist,id))
												{
													logc.info("Successfully Added To Cart");
												}
												else {logc.info("Not even a single Product added to cart");}
											}
											else
											if(i!=1) {
												logc.info("Going Back!!");
											}
									}catch (BusinessException e)
										{logc.warn(e.getMessage());}
									break;
							case 2:logc.info("Your Cart");
									try {
										List<Cart> cartlist=customersearchserviceobject.viewcart(id);
										if(cartlist!=null && cartlist.size()>0) {
											logc.info("Total there are "+cartlist.size()+" number of Records in your Cart");
											for(Cart cr:cartlist) {
												logc.info(cr);
											}
										}
										logc.info("Your Cart Total: "+customersearchserviceobject.getcarttotal(id));
										//here move from cart to orders table
										logc.info("1. Place Order on Cart items");
										logc.info("2. Go back");
										logc.info("Enter Choice");
										int k=sc.nextInt();
										if(k==1)
										{
											List<Integer> carttoorderlist = new ArrayList<Integer>();
											int tempvar=0;
											logc.info("Keep Entering CartId of all CartItems you want to place Order for");
											logc.info("Enter 0 when finished");
											logc.info("Enter here:");
											do{
												tempvar=sc.nextInt();
												if(tempvar>0)
													carttoorderlist.add(tempvar);
												
											}while(tempvar>0);
											//here add them to cart
											if(customersearchserviceobject.fromCartTOorders(carttoorderlist,id))
											{
												logc.info("Successfully placed Order upon");
											}
											else {logc.info("Not even a single order is placed");}
										}
										else
										if(k!=1) {
											logc.info("Going Back!!");
										}
										//till here
									}catch (BusinessException e)
										{logc.warn(e.getMessage());}
									break;
							case 3:logc.info("Your Orders");
									try {
										List<Orders> orderlist=customersearchserviceobject.vieworders(id);
										if(orderlist!=null && orderlist.size()>0) {
											logc.info("Total there are "+orderlist.size()+" number of your Orders");
											for(Orders or:orderlist) {
												logc.info(or);
											}
										}
										logc.info("Your Orders Total: "+customersearchserviceobject.getordertotal(id));
										
										logc.info("1. Mark Multiple Orders as Received");
										logc.info("2. Go back");
										logc.info("Enter Choice");
										int ii=sc.nextInt();
										if(ii==1)
										{
											List<Integer> olist = new ArrayList<Integer>();
											int tempo=0;
											logc.info("Keep Entering OrderId of all Products you want to update as Recieved");
											logc.info("Enter 0 when finished");
											logc.info("Enter here:");
											do{
												tempo=sc.nextInt();
												if(tempo>0)
													olist.add(tempo);
												
											}while(tempo>0);
											//update orders here
											if(customersearchserviceobject.updateorderbycustomer(olist,id))
											{
												logc.info("Successfully Updates as Recieved");
											}
											else {logc.info("Not even a single Orders updated as received");}
										}
										else
										if(ii!=1) {
											logc.info("Going Back!!");
										}
										
									}catch (BusinessException e)
										{logc.warn(e.getMessage());}
									
									break;
							case 4:logc.info("Going back to Main Menu");
									try {Thread.sleep(2000);} catch (InterruptedException e){}
									break;
							default:logc.info("Invalid Choice : Choose Correct Choice");
									break;
							}
						}while(option!=4);
					}
					else {logc.warn("Internal Server Error: Forced Logout or Login Error(Invalid Credentials)");}
					}catch(BusinessException e) {logc.warn(e.getMessage());}
				break;
		case 3:logc.info("Exiting!!");
			   try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			   break;
		default:logc.info("Invalid Choice : Please choose from above choices");
				break;
		}
		}while(choice!=3);
		sc.close();
//		Main.main(null);
	}
}
