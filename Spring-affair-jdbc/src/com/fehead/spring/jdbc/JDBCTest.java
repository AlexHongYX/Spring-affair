package com.fehead.spring.jdbc;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JDBCTest {

	private ApplicationContext ctx = null;
	private JdbcTemplate jdbcTemplate;
	
	{
		ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		jdbcTemplate = (JdbcTemplate)ctx.getBean("jdbcTemplate");
	}
	
	@Test
	public void testDataSource() throws SQLException{
		DataSource dataSource = (DataSource) ctx.getBean("dataSource");
		try {
			System.out.println(dataSource.getConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 执行INSERT，UPDATE，DELETE
	 * */
	@Test
	public void testUpdate(){
		String sql = "UPDATE employees SET LAST_NAME=? WHERE id = ?";
		jdbcTemplate.update(sql, "Jack",2);
	}
	
	/*
	 * 执行批量更新：批量的INSERT，UPDATE，DELETE
	 * 最后一个参数是Object[]的List类型：因为修改一条记录需要一个Object的数组，那么多条就需要多个Object数组
	 * */
	
	@Test
	public void testBatchUpdate(){
		String sql = "INSERT INTO employees(last_name,email,dept_id) VALUES (?,?,?)";
		
		List<Object[]> batchArgs = new ArrayList<>();
		
		batchArgs.add(new Object[]{"AA","fehead@qq.com",1});
		batchArgs.add(new Object[]{"BB","fehead@qq.com",3});
		batchArgs.add(new Object[]{"CC","fehead@qq.com",1});
		batchArgs.add(new Object[]{"DD","fehead@qq.com",2});
		
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	/*
	 * 从数据库中获取一条记录，实际得到对应的一个对象
	 * 注意：
	 * 	不是调用queryForObject(String sql,Class<Employee> requiredType ,Object...args)方法！
	 * 	需要调用queryForObject(String sql,RowMapper<Employee> rowMapper ,Object...args)方法
	 * 		①其中的RowMapper指定如何去映射结果集的行，常用的类为BeanPropertyRowMapper
	 * 		②使用SQL中的列的别名完成列名和类的属性名的映射。例如：last_name lastname
	 * 		③不支持级联属性（在employees中调用departments）
	 * 		 jdbcTemplate只是JDBC的一个小工具，而不是ORM框架
	 * 		④注意创建RowMapper时要传入Employee.class
	 * 
	 * */
	@Test
	public void testQueryForObject(){
		String sql = "SELECT id,last_name,email,dept_id FROM employees WHERE id = ?";
		
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper,1);
		
		System.out.println(employee);
		
	}
	
	/*
	 * 查看实体类的集合
	 * 调用query
	 * */
	@Test
	public void testQuery(){
		String sql = "SELECT id,last_name,email,dept_id FROM employees WHERE id > ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		List<Employee> employees = jdbcTemplate.query(sql, rowMapper,2);
		
		System.out.println(employees);
	}
	
	/*
	 * 获取单个列的值，或做统计查询
	 * 使用
	 * */
	@Test
	public void testQueryForObject2(){
		String sql = "SELECT count(id) FROM employees";
		long count = jdbcTemplate.queryForObject(sql , long.class);
		
		System.out.println(count);
	}

}
