package com.spring.jdbc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.jdbc.model.Employee;

@Controller
public class EmployeeController {
	public static final Logger logger=LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	@Qualifier("dbDatasource")
	private DataSource dataSource;
	
	public void setDatasource(DataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	@RequestMapping(value="rest/emps", method=RequestMethod.GET)
	public @ResponseBody List<Employee> getAll(){
		logger.info("Start all employee retrieval");
		List<Employee> empl_list=new ArrayList();
		
		String query="select id,name,role from Employee";
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		
		List<Map<String, Object>> emprows=jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> emp_element:emprows) {
			Employee employee=new Employee();
			employee.setId(Integer.parseInt(String.valueOf(emp_element.get("id"))));
			employee.setName(String.valueOf(emp_element.get("name")));
			employee.setRole(String.valueOf(emp_element.get("role")));
			empl_list.add(employee);
		}
		return empl_list;
	}
}
