package com.wei.boot.cocurrent;

public class NoVisibility {

	private static boolean ready;
	private static int number;
	
	public static class ReaderThread extends Thread{
		@Override
		public void run() {
			while(!ready) {
				Thread.yield();
				System.out.println(Thread.currentThread().getState());
			}
			System.out.println(number);
		}
	}
	
	public static void main(String[] args) {
		new ReaderThread().start();
		number = 48;
		ready = true;
	}
}
