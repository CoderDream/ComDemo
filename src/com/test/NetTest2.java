package com.test;

import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;

public class NetTest2 {

	public static void main(String[] args) {

		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier portId = null;
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getName().equalsIgnoreCase("COM2")) {
				try {
					SerialPort serialPort = (SerialPort) portId.open("随便什么名称", 2000);
					System.out.println("EEE");
				} catch (PortInUseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}