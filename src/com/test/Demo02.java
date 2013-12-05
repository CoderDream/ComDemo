package com.test;

import javax.comm.CommDriver;

public class Demo02 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommDriver driver = null;

		String driverName = "com.sun.comm.Win32Driver";

		try {
			driver = (CommDriver) Class.forName(driverName).newInstance();
			driver.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
