package com.wei.boot.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/")
    String home() {
        return "我是spring-boot小测试";
    }
	
	@RequestMapping("/jdbc")
	String testJdbc(){
		String sql = "select count(1) from menu";
		int a = jdbcTemplate.queryForObject(sql, Integer.class);
		return "共有："+a+"个菜单";
	}
}
