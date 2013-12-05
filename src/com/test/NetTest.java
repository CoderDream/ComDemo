package com.test;

import java.util.Enumeration;

import javax.comm.CommPortIdentifier;

public class NetTest {

	static CommPortIdentifier portId; // 先行定義Port的物件
	static Enumeration portList; // 所有port的集合，請參考collection相關章節

	public static void main(String[] args) {
		portList = CommPortIdentifier.getPortIdentifiers();
		// hasMoreElements()指的是，是否還有其它物件內容
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			System.out.println(" " + portId.getName()); // 取得通訊埠的名稱，例如：COM1、COM2
		}
	}
}