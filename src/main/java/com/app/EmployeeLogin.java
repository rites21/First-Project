package com.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import com.app.model.Customers;
import com.app.model.Orders;
import com.app.exception.BusinessException;
import com.app.search.service.EmployeeSearchService;
import com.app.search.service.impl.EmployeeSearchServiceImpl;

public class EmployeeLogin {
	public static boolean employeecredentialscheck(String empname, String emppassword){
		if(empname.equals("abc") && emppassword.equals("abc"))
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
	private static Logger log = Logger.getLogger(EmployeeLogin.class);

	public static void main(String[] args) {
		int choice=0,ch=0;
		Scanner sc=new Scanner(System.in);
		EmployeeSearchService employeesearchserviceobject = new EmployeeSearchServiceImpl();
		System.out.println("-------SHOP PROJECT------");
		System.out.println("---EMPLOYEE LOGIN PAGE---");
		do {
		log.info("1.Login");
		log.info("2.Exit");
		choice=sc.nextInt();
		switch(choice) {
		case 1:System.out.print("Enter Username: ");
			   String empname=sc.next();
			   if(checkforspecialcharacter(empname)) {break;}//if contains special character then break
			   System.out.print("Enter Password: ");
			   String emppassword=sc.next();
		if(employeecredentialscheck(empname,emppassword))
		{
			do {
			log.info("\nEmployee Login Successful");
			log.info("1. View Customers Details");
			log.info("2. Add a new Product");
			log.info("3. Mark Order as Shipped");
//			log.info("4. Modify Quantity of Existing Products");
			log.info("4. Delete Existing Product");
			log.info("5. Logout");
			ch=sc.nextInt();
			switch(ch){
			case 1:log.info("Showing Customer Details");
					try {
						 List<Customers> customerlist=employeesearchserviceobject.viewcustomerdetails();
						 if(customerlist!=null && customerlist.size()>0) {
							 log.info("Total there are "+customerlist.size()+" number of Customers");
							 for(Customers c:customerlist) {
								 log.info(c);
							 }
						 }
					}catch (BusinessException e)
						{log.warn(e.getMessage());}
				   break;
			case 2:log.info("Add New Products");
					try{boolean answer1=employeesearchserviceobject.addnewproducts();
					if(answer1==true) {log.info("New Product Added Successfully");}
					else {log.warn("Internal Server Error: Could not insert new product");}
					}catch(BusinessException e) {log.warn(e.getMessage());}
					break;
			case 3:log.info("Mark Order as Shipped");
					try {
						List<Orders> orderlist=employeesearchserviceobject.viewallorders();
						if(orderlist!=null && orderlist.size()>0) {
							log.info("Total there are "+orderlist.size()+" number of All Orders");
							for(Orders or:orderlist) {
								log.info(or);
							}
						}
						log.info("1. Mark Multiple Orders as Shipped");
						log.info("2. Go back");
						log.info("Enter Choice");
						int i=sc.nextInt();
						if(i==1)
						{
							List<Integer> olist = new ArrayList<Integer>();
							int tempo=0;
							log.info("Keep Entering OrderId of all Products you want to update as Recieved");
							log.info("Enter 0 when finished");
							log.info("Enter here:");
							do{
								tempo=sc.nextInt();
								if(tempo>0)
									olist.add(tempo);
							}while(tempo>0);
							//update orders here
							if(employeesearchserviceobject.updateorderbyemployee(olist))
							{
								log.info("Successfully Updates as Shippped");
							}
							else {log.info("Not even a single Orders updated as shipped");}
						}
						else
							if(i!=1) {
								log.info("Going Back!!");
							}
					}catch (BusinessException e)
						{log.warn(e.getMessage());}
					break;
//			case 4:log.info("Modify Quantity of Existing Products");
//					try{
//						log.info("Enter Product Id to modify quantity for that product:");
//						int id2=sc.nextInt();
//						boolean answer2=employeesearchserviceobject.modifyproductsquantity(id2);
//						if(answer2==true) {log.info("Product Quantity Updated Successfully");}
//						else {log.warn("Internal Server Error: Could not update product quantity");}
//					}catch(BusinessException e) {log.warn(e.getMessage());}
//				   break;
			case 4:log.info("Remove Existing Product");
					try{
					log.info("Enter Product Id to Remove:");
					int id3=sc.nextInt();
					boolean answer3=employeesearchserviceobject.deleteproduct(id3);
					if(answer3==true) {log.info("Product Deleted Successfully");}
					else {log.warn("Internal Server Error: Could not remove product");}
					}catch(BusinessException e) {log.warn(e.getMessage());}
					break;
			case 5:log.info("Logging out");
					choice=2;
			       break;
			default:log.info("Incorrect Choice : Enter Correct Choice");
					break;
			}
			}while(ch!=6);
		}
		else
			{System.out.println("Invalid Employee Credentials");}
		break;
		case 2:log.info("Exiting!!");
				break;
		default:log.info("Incorrect Choice : Enter Correct Choice");
				break;
		}
		}while(choice!=2);
		sc.close();
//		Main.main(null);
//		try{
//			int ve=empp.validateemployee(empname,emppassword);
//			if(ve==1) {}
//			else {tries++;}
//		   } catch (BusinessException e)
//				{System.out.println(e.getMessage());}
//		}while(tries<5);
//		System.out.println("Invalid Credentials used for 5 times : Closing App");
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

	}

}
