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
	 * ִ��INSERT��UPDATE��DELETE
	 * */
	@Test
	public void testUpdate(){
		String sql = "UPDATE employees SET LAST_NAME=? WHERE id = ?";
		jdbcTemplate.update(sql, "Jack",2);
	}
	
	/*
	 * ִ���������£�������INSERT��UPDATE��DELETE
	 * ���һ��������Object[]��List���ͣ���Ϊ�޸�һ����¼��Ҫһ��Object�����飬��ô��������Ҫ���Object����
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
	 * �����ݿ��л�ȡһ����¼��ʵ�ʵõ���Ӧ��һ������
	 * ע�⣺
	 * 	���ǵ���queryForObject(String sql,Class<Employee> requiredType ,Object...args)������
	 * 	��Ҫ����queryForObject(String sql,RowMapper<Employee> rowMapper ,Object...args)����
	 * 		�����е�RowMapperָ�����ȥӳ���������У����õ���ΪBeanPropertyRowMapper
	 * 		��ʹ��SQL�е��еı�����������������������ӳ�䡣���磺last_name lastname
	 * 		�۲�֧�ּ������ԣ���employees�е���departments��
	 * 		 jdbcTemplateֻ��JDBC��һ��С���ߣ�������ORM���
	 * 		��ע�ⴴ��RowMapperʱҪ����Employee.class
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
	 * �鿴ʵ����ļ���
	 * ����query
	 * */
	@Test
	public void testQuery(){
		String sql = "SELECT id,last_name,email,dept_id FROM employees WHERE id > ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		List<Employee> employees = jdbcTemplate.query(sql, rowMapper,2);
		
		System.out.println(employees);
	}
	
	/*
	 * ��ȡ�����е�ֵ������ͳ�Ʋ�ѯ
	 * ʹ��
	 * */
	@Test
	public void testQueryForObject2(){
		String sql = "SELECT count(id) FROM employees";
		long count = jdbcTemplate.queryForObject(sql , long.class);
		
		System.out.println(count);
	}

}
