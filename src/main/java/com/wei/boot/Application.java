package com.wei.boot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Configuration  //java-annotation配置
//@EnableAutoConfiguration
//@ComponentScan // spring-annotation配置
@SpringBootApplication  //相当于 @EnableAutoConfiguration，@ComponentScan，@Configuration 的综合
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
//		SpringApplication app = new SpringApplication(Application.class);
//		//app.setBannerMode(Banner.Mode.OFF);
//		app.run(args);
	}

}
