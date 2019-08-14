package com.wei.boot.cocurrent;

public class TestThread1 extends Thread {

	private ThreadTest tt;
	public TestThread1(ThreadTest t) {
		this.tt = t;
	}
	
	@Override
	public void run() {
		try {
			tt.printSomething();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
