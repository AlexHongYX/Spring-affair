package com.fehead.spring.tx;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTransactionTest {

	private ApplicationContext ctx = null;
	private BookShopDao bookShopDao = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		bookShopDao = ctx.getBean(BookShopDao.class);
	}
	
	@Test
	public void testBookShopDaoUpdateBookStock() {
		bookShopDao.updateBookStock("1001");
	}
	
	@Test
	public void testBookShopDaoUpdateUser(){
		bookShopDao.updateUserAccount("AA", 200);
	}
	
	

}
