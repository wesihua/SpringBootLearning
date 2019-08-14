package com.wei.boot.bean;

public enum EnumType {

	NORMAL(0, "正常"),
	
	UNNORMAL(1, "非正常");
	
	private int code;
	
	private String info;

	EnumType(int code, String info) {
		this.code = code;
		this.info = info;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public static void main(String[] args) {
		System.out.println(EnumType.NORMAL.name());
		System.out.println(EnumType.UNNORMAL.getInfo());
	}
}
