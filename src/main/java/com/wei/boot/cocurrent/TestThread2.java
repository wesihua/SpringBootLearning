package com.wei.boot.cocurrent;

public class TestThread2 extends Thread {
	private ThreadTest tt;
	public TestThread2(ThreadTest t) {
		this.tt = t;
	}
	@Override
	public void run() {
		tt.printString();
	}
}
