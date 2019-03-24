package com.fehead.spring.jdbc;

public class Employee {
	
	private String last_name;
	private String email;
	private int dept_id;
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	
	@Override
	public String toString() {
		return "Employee [last_name=" + last_name + ", email=" + email + ", dept_id=" + dept_id + "]";
	}
	
}
