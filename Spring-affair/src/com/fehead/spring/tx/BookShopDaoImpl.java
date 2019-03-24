package com.fehead.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
		String sql = "UPDATE book_stock SET stock = stock-1 WHERE isbn = ?";
		jdbcTemplate.update(sql,isbn);
	}

	@Override
	public void updateUserAccount(String username, int price) {
		String sql = "UPDATE account SET balance = balance - 1 WHERE username = ?";
		jdbcTemplate.update(sql,price,username);
	}

}
