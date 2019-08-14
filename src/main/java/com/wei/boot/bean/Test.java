package com.wei.boot.bean;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
//		AtomicInteger ai = new AtomicInteger(0);
//		Thread t = new Thread("test");
//		ThreadLocal<String> tl = new ThreadLocal<>();
//		System.out.println(tl);
		
		List<UserInfo> list = new ArrayList<>();
		UserInfo u1 = new UserInfo();
		u1.setCode(1);
		UserInfo u2 = new UserInfo();
		u2.setCode(2);
		UserInfo u3 = new UserInfo();
		u3.setCode(3);
		UserInfo u4 = new UserInfo();
		u4.setCode(4);
		list.add(u1);
		list.add(u2);
		list.add(u3);
		list.add(u4);
		
		List<UserInfo> subList = list.subList(0, 2);
		subList.clear();
		
		
		System.out.println(list);
		
	}
}
