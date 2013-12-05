package com.thread;

/**
 * 通过实现 Runnable 接口实现多线程
 */
public class TestMitiThread1 implements Runnable {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
		TestMitiThread1 test = new TestMitiThread1();
		Thread thread1 = new Thread(test);
		Thread thread2 = new Thread(test);
		thread1.start();
		thread2.start();
		System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
		for (int i = 0; i < 10; i++) {
			System.out.println(i + " " + Thread.currentThread().getName());
			try {
				Thread.sleep((int) Math.random() * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
	}
}