package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.UnsupportedCommOperationException;

public class SimpleRead3 implements Runnable{
	static CommPortIdentifier portId;
	static Enumeration portList;

	InputStream inputStream;
	SerialPort serialPort;
	Thread readThread;

	public static void main(String[] args) {
		portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println("portId.getName(): " + portId.getName());
				if (portId.getName().equals("COM2")) {
					// if (portId.getName().equals("/dev/term/a")) {
					SimpleRead3 reader = new SimpleRead3();

					break;
				}
			}
		}
	}

	public SimpleRead3() {
		try {
			serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
		} catch (PortInUseException e) {
		}
		try {
			inputStream = serialPort.getInputStream();
		} catch (IOException e) {
		}
		//try {
			//serialPort.addEventListener(this);
		//} catch (TooManyListenersException e) {
		//}
		serialPort.notifyOnDataAvailable(true);
		try {
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
		}

		byte[] readBuffer = new byte[2048];
		//byte[] readBuffer = new byte[8192];

		try {
			while (inputStream.available() > 0) {
			int numBytes = inputStream.read(readBuffer);
			}
			System.out.print(new String(readBuffer).trim());
		} catch (IOException e) {
		}

		readThread = new Thread(this);
		readThread.start();
	}

	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			// byte[] readBuffer = new byte[20];
			byte[] readBuffer = new byte[20];

			try {
				// while (inputStream.available() > 0) {
				int numBytes = inputStream.read(readBuffer);
				// }
				System.out.print(new String(readBuffer));
			} catch (IOException e) {
			}
			break;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
