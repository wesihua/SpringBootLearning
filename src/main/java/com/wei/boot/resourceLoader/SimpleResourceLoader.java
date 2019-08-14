package com.wei.boot.resourceLoader;

import java.net.URL;
import java.util.Enumeration;

public class SimpleResourceLoader {

	public static void main(String[] args) throws Exception {
		String path = "application.yml";
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(path);
		while(urls.hasMoreElements()) {
			URL url = urls.nextElement();
			System.out.println(url.toString());
		}
	}
}
