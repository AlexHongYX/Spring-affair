package com.fehead.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.BalanceStrategy;

@Repository("bookShopDao")
public class BookShopDaoImpl implements BookShopDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int findBookPriceByIsbn(String isbn) {
		String sql = "SELECT price FROM book WHERE isbn=?";
		return jdbcTemplate.queryForObject(sql, Integer.class,isbn);
	}

	@Override
	public void updateBookStock(String isbn) {
		
		String sql2 = "SELECT stock FROM book_stock WHERE isbn = ?";
		int stock = jdbcTemplate.queryForObject(sql2, Integer.class,isbn);
		if(stock==0){
			throw new BookStockException("¿â´æ²»×ã£¡");
		}
		String sql = "UPDATE book_stock SET stock = stock-1 WHERE isbn = ?";
		jdbcTemplate.update(sql,isbn);
	}

	@Override
	public void updateUserAccount(String username, int price) {
		String sql2 = "SELECT stock FROM user WHERE username = ?";
		int balance = jdbcTemplate.queryForObject(sql2, Integer.class,username);
		if(balance<price){
			throw new UserException("Óà¶î²»×ã£¡");
		}
		String sql = "UPDATE user SET balance = balance - ? WHERE username = ?";
		jdbcTemplate.update(sql,price,username);
	}

}
