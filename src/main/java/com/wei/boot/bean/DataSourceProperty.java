package com.wei.boot.bean;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;

public class DataSourceProperty implements Serializable {

	private static final long serialVersionUID = -7726167789066889432L;
	
	@Value("${app.database.url}")
	private String url;
	@Value("${app.database.username}")
	private String username;
	@Value("${app.database.password}")
	private String password;
	@Value("${app.database.initial-size}")
	private int initialSize;
	@Value("${app.database.min-idle}")
	private int minIdle;
	@Value("${app.database.max-idle}")
	private int maxIdle;
	@Value("${app.database.max-active}")
	private int maxActive;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	
	
}
