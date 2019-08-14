package com.wei.boot.cocurrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试不可变对象
 * @author weisihua
 *
 */
public class Inmutable {

	public static final Map<String, String> map = new HashMap<>();
	
	public static final String[] array = {"1","2"};
	
	public static void main(String[] args) {
		map.put("1", "2");
		map.put("2", "2");
		map.put("3", "2");
		map.put("4", "2");
		map.put("5", "2");
		map.put("11", "2");
		map.put("12", "2");
		map.put("13", "2");
		map.put("14", "2");
		map.put("15", "2");
		map.put("16", "2");
		map.put("17", "2");
		map.put("18", "2");
		map.put("19", "2");
		map.put("111", "2");
		map.put("1112", "2");
		array[1] = "test";
		//map = new HashMap<>();
		System.out.println(map);
		System.out.println(array);
		
		List<String> test = new ArrayList<>();
		test.add("a");
	}
	
	public static void addSth(String... strings) {
		System.out.println(strings);
	}
}
