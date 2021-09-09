package com.app;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.app.EmployeeLogin;
import com.app.exception.BusinessException;
import com.app.search.service.CustomerSearchService;
import com.app.search.service.EmployeeSearchService;
import com.app.search.service.impl.CustomerSearchServiceImpl;
import com.app.search.service.impl.EmployeeSearchServiceImpl;

class TestcaseEmplogin {

	@Test
	void employeecredentialscheck() {
		assertEquals(false, EmployeeLogin.employeecredentialscheck("abc","abs"), "Invalid Credentials");
		assertEquals(false, EmployeeLogin.employeecredentialscheck("abs","abs"), "Invalid Credentials");
		assertEquals(false, EmployeeLogin.employeecredentialscheck("abc","abcc"), "Invalid Credentials");
		assertEquals(true, EmployeeLogin.employeecredentialscheck("abc","abc"), "Valid Credentials");
		assertEquals(true, EmployeeLogin.employeecredentialscheck("abc","abc"), "Valid Credentials");

		
	}
	@Test
	void checkforspecialcharacter() {
		assertEquals(false, EmployeeLogin.checkforspecialcharacter("bc44"), "valid Username");
		assertEquals(false, EmployeeLogin.checkforspecialcharacter("44abcabc"), "valid Username");
		assertEquals(false, EmployeeLogin.checkforspecialcharacter("abcc4"), "valid Username");
		assertEquals(true, EmployeeLogin.checkforspecialcharacter("44ab>cab<c"), "Invalid Username");
		assertEquals(true, EmployeeLogin.checkforspecialcharacter("abcc4!!"), "Invalid Username");
	}

	@Test
	void deleteproduct() throws BusinessException {
		EmployeeSearchService et=new EmployeeSearchServiceImpl();
		assertEquals(false,et.deleteproduct(2));
	}
	
	@Test
	void login() throws BusinessException {
		CustomerSearchService ct=new CustomerSearchServiceImpl();
		assertEquals(true,ct.login("ash999","ash999"));
		assertEquals(false,ct.login("ksh","ksh"));
	}
}
