package com.wei.boot.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("random")
public class RandomController {

	@Value("${my.secret}")
	private String secret;
	
	@Value("${my.number}")
	private int number;
	
	@Value("${my.bignumber}")
	private long bigNumber;
	
	@Value("${my.uuid}")
	private String uuid;
	
	@Value("${my.numbers.in.range}")
	private int rangeNumber;
	
	@Value("${my.numbers.less.than.ten}")
	private int lessThan;
	
	@RequestMapping("show")
	public String showRandom(){
		String result = "下面是配置文件中的随机数：<br/><br/>";
		result += "密文是："+secret+"<br/><br/>";
		result += "int是："+number+"<br/><br/>";
		result += "long是："+bigNumber+"<br/><br/>";
		result += "uuid是："+uuid+"<br/><br/>";
		result += "范围int是："+rangeNumber+"<br/><br/>";
		result += "大小范围int是："+lessThan+"<br/><br/>";
		return result;
	}
}
