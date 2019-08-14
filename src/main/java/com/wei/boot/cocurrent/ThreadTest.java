package com.wei.boot.cocurrent;

public class ThreadTest {

	public synchronized void printSomething() throws InterruptedException {
		System.out.println("我是同步方法");
		Thread.sleep(3000);
		printString();
	}
	
	public void printString() {
		System.out.println("我不是同步代码！");
	}
	
	public static void main(String[] args) {
		ThreadTest tt = new ThreadTest();
		TestThread1 t1 = new TestThread1(tt);
		TestThread1 t3 = new TestThread1(tt);
		TestThread2 t2 = new TestThread2(tt);
		
		t1.start();
		t2.start();
		t3.start();
	}
}
