package com.thread;

/**
 * <pre>
 * File Name:   TestMitiThread.java
 * Created by:  IntelliJ IDEA.
 * Copyright:   Copyright (c) 2003-2006
 * Company:     Lavasoft( [url]http://lavasoft.blog.51cto.com/[/url])
 * Author:      leizhimin
 * Modifier:    leizhimin
 * Date Time:   2007-5-17 10:03:12
 * Readme:      通过扩展Thread类实现多线程
 * </pre>
 */
public class TestMitiThread {
	public static void main(String[] rags) {
		System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
		new MitiSay("A").start();
		new MitiSay("B").start();
		System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
	}
}

class MitiSay extends Thread {
	public MitiSay(String threadName) {
		super(threadName);
	}

	public void run() {
		System.out.println(getName() + " 线程运行开始!");
		for (int i = 0; i < 10; i++) {
			System.out.println(i + " " + getName());
			try {
				sleep((int) Math.random() * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(getName() + " 线程运行结束!");
	}
}