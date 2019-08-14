package com.wei.boot.cocurrent;

import java.util.concurrent.ConcurrentHashMap;

public class MapTest {

	public static void main(String[] args) {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		map.put("a", "1");
		System.out.println(map);
	}
}
