package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

/**
 * <pre>
 * @author xulin
 * 
 * 2 : read: size: 34; string:90 000000000000
 * 90 000000000000
 * 
 * 
 * </pre>
 * 
 */
public class SimpleRead_B1 {
	static CommPortIdentifier portId;
	static Enumeration portList;

	InputStream inputStream;
	SerialPort serialPort;
	Thread readThread;

	public static void main(String[] args) {
		SimpleRead_B1 sb1 = new SimpleRead_B1();
		for (int i = 0; i < 100; i++) {
			System.out.print(i + " : ");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sb1.getStr();
		}
	}

	public void getStr() {
		portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				// System.out.println("portId.getName(): " + portId.getName());
				if (portId.getName().equals("COM5")) {
					// Thread helloAppModule = new Thread(, "Sender App Module");
					// // helloAppModule.setDaemon(true);
					// helloAppModule.start();

					new SimpleRead_B();
					break;
				}
			}
		}
	}

	class SimpleRead_B implements Runnable {

		public SimpleRead_B() {
			try {
				serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
			} catch (PortInUseException e) {
			}
			try {
				inputStream = serialPort.getInputStream();
			} catch (IOException e) {
			}
			// try {
			// serialPort.addEventListener(this);
			// } catch (TooManyListenersException e) {
			// }
			serialPort.notifyOnDataAvailable(true);
			try {
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} catch (UnsupportedCommOperationException e) {
			}

			readThread = new Thread(this);
			readThread.start();
		}

		@Override
		public void run() {
			// byte[] readBuffer = new byte[2048];
			// byte[] readBuffer = new byte[8192];
			// byte[] readBuffer = new byte[81920];
			int size = 17 * 50;
			byte[] readBuffer = new byte[size];
			try {
				while (inputStream.available() > 0) {
					int numBytes = inputStream.read(readBuffer);
					// System.out.println("read: size: " + numBytes + "; string:" + new String(readBuffer).trim());
					System.out.println("read: size: " + numBytes + "; string:" + new String(readBuffer));
					String string = new String(readBuffer);
					int i = string.indexOf("");
					System.out.println("index 90: " + i);
					if (-1 != i && i + 10 < string.length()) {
						
						String num = string.substring(i + 4, i + 10);
						System.out.println("num: " + num);
					}

					// int rtn = string.indexOf("90");
					// System.out.println("index 90: " + i);
					break;
				}

			} catch (IOException e) {
			}
		}
	}
}
