package com.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

import com.app.exception.BusinessException;
import com.app.model.Cart;
import com.app.model.Customers;
import com.app.model.Orders;
import com.app.model.Products;
import com.app.search.service.CustomerSearchService;
import com.app.search.service.EmployeeSearchService;
import com.app.search.service.impl.CustomerSearchServiceImpl;
import com.app.search.service.impl.EmployeeSearchServiceImpl;

public class Main {
	public static boolean employeecredentialscheck(String empname, String emppassword){
		if(empname.equals("ritesh") && emppassword.equals("ritesh"))
			return true;
		else
			return false;
	}
	public static boolean checkforspecialcharacter(String name){
		if(Pattern.matches("(.)*(\\W)(.)*", name))//this checks for special characters in name
		{
			return true;
		}
		else
			return false;
	}
	private static Logger logmain = Logger.getLogger(EmployeeLogin.class);

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int loginchoice;
		do{
			logmain.info("Welcome User\n1. Login as Employee\n2. Login as Customer\n3. Exit App\nEnter your choice:");
			loginchoice=Integer.parseInt(sc.nextLine());
			switch(loginchoice){
//xxxxxxxxxEmployee login page//
			case 1:int choice=0,ch=0;
				EmployeeSearchService employeesearchserviceobject = new EmployeeSearchServiceImpl();
				System.out.println("*HELLO  Employee!* Welcome to Ritesh's Shopping App");
				System.out.println("**********EMPLOYEE LOGIN--**********");
				do {
				logmain.info("1.Login");
				logmain.info("2.Exit");
				choice=Integer.parseInt(sc.nextLine());
				switch(choice) {
				case 1:System.out.print("Enter Username: ");
					   String empname=sc.nextLine();
					   if(checkforspecialcharacter(empname)) {break;}//if contains special character then break
					   System.out.print("Enter Password: ");
					   String emppassword=sc.nextLine();
				if(employeecredentialscheck(empname,emppassword))
				{
					do {
					logmain.info("\nEmployee login Successful");
					logmain.info("1. View Customers Details");
					logmain.info("2. Add a new Product");
					logmain.info("3. Mark Order as Shipped");
					//logmain.info("4. Modify Quantity of Existing Products");
					logmain.info("4. Delete Existing Product");
					logmain.info("5. Logout");
					ch=Integer.parseInt(sc.nextLine());
					switch(ch){
					case 1:logmain.info("Showing Customer Details");
							try {
								 List<Customers> customerlist=employeesearchserviceobject.viewcustomerdetails();
								 if(customerlist!=null && customerlist.size()>0) {
									 logmain.info("Total there are "+customerlist.size()+" number of Customers");
									 for(Customers c:customerlist) {
										 logmain.info(c);
									 }
								 }
							}catch (BusinessException e)
								{logmain.warn(e.getMessage());}
						   break;
					case 2:logmain.info("Add New Products");
							try{boolean answer1=employeesearchserviceobject.addnewproducts();
							if(answer1==true) {logmain.info("New Product Added Successfully");}
							else {logmain.warn("Internal Server Error: Could not insert new product");}
							}catch(BusinessException e) {logmain.warn(e.getMessage());}
							break;
					case 3:logmain.info("Mark Order as Shipped");
							try {
								List<Orders> orderlist=employeesearchserviceobject.viewallorders();
								if(orderlist!=null && orderlist.size()>0) {
									logmain.info("Total there are "+orderlist.size()+" number of All Orders");
									for(Orders or:orderlist) {
										logmain.info(or);
									}
								}
								logmain.info("1. Mark Multiple Orders as Shipped");
								logmain.info("2. Go back");
								logmain.info("Enter Choice");
								int i=Integer.parseInt(sc.nextLine());
								if(i==1)
								{
									List<Integer> olist = new ArrayList<Integer>();
									int tempo=0;
									logmain.info("Keep Entering OrderId of all Products you want to update as Recieved");
									logmain.info("Enter 0 when finished");
									logmain.info("Enter here:");
									do{
										tempo=Integer.parseInt(sc.nextLine());
										if(tempo>0)
											olist.add(tempo);
									}while(tempo>0);
									//update orders here
									if(employeesearchserviceobject.updateorderbyemployee(olist))
									{
										logmain.info("Successfully Updates as Shippped");
									}
									else {logmain.info("Not even a single Orders updated as shipped");}
								}
								else
									if(i!=1) {
										logmain.info("Going Back!!");
									}
							}catch (BusinessException e)
								{logmain.warn(e.getMessage());}
//							break;
//					case 4:logmain.info("Modify Quantity of Existing Products");
//							try{
//								logmain.info("Enter Product Id to modify quantity for that product:");
//								int id2=Integer.parseInt(sc.nextLine());
//								boolean answer2=employeesearchserviceobject.modifyproductsquantity(id2);
//								if(answer2==true) {logmain.info("Product Quantity Updated Successfully");}
//								else {logmain.warn("Internal Server Error: Could not update product quantity");}
//							}catch(BusinessException e) {logmain.warn(e.getMessage());}
//						   break;
					case 4:logmain.info("Remove Existing Product");
							try{
							logmain.info("Enter Product Id to Remove:");
							int id3=Integer.parseInt(sc.nextLine());
							boolean answer3=employeesearchserviceobject.deleteproduct(id3);
							if(answer3==true) {logmain.info("Product Deleted Successfully");}
							else {logmain.warn("Internal Server Error: Could not remove product");}
							}catch(BusinessException e) {logmain.warn(e.getMessage());}
							break;
					case 5:logmain.info("Logging out");
							choice=2;
					       break;
					default:logmain.info("Incorrect Choice : Enter Correct Choice");
							break;
					}
					}while(ch!=6);
				}
				else
					{System.out.println("Invalid Employee Credentials");}
				break;
				case 2:logmain.info("Exiting!!");
						break;
				default:logmain.info("Incorrect Choice : Enter Correct Choice");
						break;
				}
				}while(choice!=2);
						break;
//*******************************************start of customer login********************************************
				case 2:CustomerSearchService customersearchserviceobject = new CustomerSearchServiceImpl();
				System.out.println("*HELLO CUSTOMER!* Wemcome to Ritesh's Shopping APP");
				System.out.println("**********CUSTOMER LOGIN--*********");
				String id,password;
				int choice2,option=0;
				do {
					logmain.info("\n1. Register New Account");
					logmain.info("2. Login");
					logmain.info("3. Exit");
					logmain.info("Enter your choice");
				choice2 = Integer.parseInt(sc.nextLine());
				switch(choice2)
				{
				case 1:logmain.info("Registering New Account");
						try{
							boolean answer1=customersearchserviceobject.registernewaccount();
							if(answer1==true) {logmain.info("Registration Successful");}
							else {logmain.warn("Internal Server Error: Could not register new customer");}
						}catch(BusinessException e) {logmain.warn(e.getMessage());}
						break;
				case 2:logmain.info("Login");
						logmain.info("Enter Your ID");
						id=sc.nextLine();
						logmain.info("Enter Password");
						password=sc.nextLine();
						try{
							boolean answer2=customersearchserviceobject.login(id,password);
							if(answer2==true)
							{
								logmain.info("Login Successful");
								do {
									logmain.info("\n1. View All Products");
									logmain.info("2. View your Cart");
									logmain.info("3. View your Orders");
									logmain.info("4. Log out");
									logmain.info("Enter your choice");
									option = Integer.parseInt(sc.nextLine());
									switch(option){
									case 1:logmain.info("Showing All Products");
											try{
													List<Products> productlist=customersearchserviceobject.viewallproducts();
													if(productlist!=null && productlist.size()>0) {
														logmain.info("Total there are "+productlist.size()+" number of Products you can buy");
														for(Products pr:productlist) {
															logmain.info(pr);
														}
													}
													logmain.info("1. Add to Cart");
													logmain.info("2. Go back");
													logmain.info("Enter Choice");
													int i=Integer.parseInt(sc.nextLine());
													if(i==1)
													{
														List<Integer> plist = new ArrayList<Integer>();
														int tempp=0;
														logmain.info("Keep Entering ProductId of all Products you want in cart");
														logmain.info("Enter 0 when finished");
														logmain.info("Enter here:");
														do{
															tempp=Integer.parseInt(sc.nextLine());
															if(tempp>0)
																plist.add(tempp);
															
														}while(tempp>0);
														//here add them to cart
														if(customersearchserviceobject.addtocart(plist,id))
														{
															logmain.info("Successfully Added To Cart");
														}
														else {logmain.info("Not even a single Product added to cart");}
													}
													else
													if(i!=1) {
														logmain.info("Going Back!!");
													}
											}catch (BusinessException e)
												{logmain.warn(e.getMessage());}
											break;
									case 2:logmain.info("Your Cart");
											try {
												List<Cart> cartlist=customersearchserviceobject.viewcart(id);
												if(cartlist!=null && cartlist.size()>0) {
													logmain.info("Total there are "+cartlist.size()+" number of Records in your Cart");
													for(Cart cr:cartlist) {
														logmain.info(cr);
													}
												}
												logmain.info("Your Cart Total: "+customersearchserviceobject.getcarttotal(id));
												//here move from cart to orders table
												logmain.info("1. Place Order on Cart items");
												logmain.info("2. Go back");
												logmain.info("Enter Choice");
												int k=Integer.parseInt(sc.nextLine());
												if(k==1)
												{
													List<Integer> carttoorderlist = new ArrayList<Integer>();
													int tempvar=0;
													logmain.info("Keep Entering CartId of all CartItems you want to place Order for");
													logmain.info("Enter 0 when finished");
													logmain.info("Enter here:");
													do{
														tempvar=Integer.parseInt(sc.nextLine());
														if(tempvar>0)
															carttoorderlist.add(tempvar);
														
													}while(tempvar>0);
													//here add them to cart
													if(customersearchserviceobject.fromCartTOorders(carttoorderlist,id))
													{
														logmain.info("Successfully placed Order upon");
													}
													else {logmain.info("Not even a single order is placed");}
												}
												else
												if(k!=1) {
													logmain.info("Going Back!!");
												}
												//till here
											}catch (BusinessException e)
												{logmain.warn(e.getMessage());}
											break;
									case 3:logmain.info("Your Orders");
											try {
												List<Orders> orderlist=customersearchserviceobject.vieworders(id);
												if(orderlist!=null && orderlist.size()>0) {
													logmain.info("Total there are "+orderlist.size()+" number of your Orders");
													for(Orders or:orderlist) {
														logmain.info(or);
													}
												}
												logmain.info("Your Orders Total: "+customersearchserviceobject.getordertotal(id));
												
												logmain.info("1. Mark Multiple Orders as Received");
												logmain.info("2. Go back");
												logmain.info("Enter Choice");
												int ii=Integer.parseInt(sc.nextLine());
												if(ii==1)
												{
													List<Integer> olist = new ArrayList<Integer>();
													int tempo=0;
													logmain.info("Keep Entering OrderId of all Products you want to update as Recieved");
													logmain.info("Enter 0 when finished");
													logmain.info("Enter here:");
													do{
														tempo=Integer.parseInt(sc.nextLine());
														if(tempo>0)
															olist.add(tempo);
														
													}while(tempo>0);
													//update orders here
													if(customersearchserviceobject.updateorderbycustomer(olist,id))
													{
														logmain.info("Successfully Updates as Recieved");
													}
													else {logmain.info("Not even a single Orders updated as received");}
												}
												else
												if(ii!=1) {
													logmain.info("Going Back!!");
												}
												
											}catch (BusinessException e)
												{logmain.warn(e.getMessage());}
											
											break;
									case 4:logmain.info("Going back to Main Menu");
											try {Thread.sleep(2000);} catch (InterruptedException e){}
											break;
									default:logmain.info("Invalid Choice : Choose Correct Choice");
											break;
									}
								}while(option!=4);
							}
							else {logmain.warn("Internal Server Error: Forced Logout or Login Error(Invalid Credentials)");}
							}catch(BusinessException e) {logmain.warn(e.getMessage());}
						break;
				case 3:logmain.info("Exiting!!");
					   try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					   break;
				default:logmain.info("Invalid Choice : Please choose from above choices");
						break;
				}
				}while(choice2!=3);
						break;
				case 3:System.out.println("Closing App");
						sc.close();
						break;
				default:System.out.println("Invalid Choice");
						break;
			}
		}while(loginchoice!=3);
		

	}

}
