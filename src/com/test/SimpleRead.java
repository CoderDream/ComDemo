package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

public class SimpleRead implements Runnable, SerialPortEventListener {
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
					SimpleRead reader = new SimpleRead();

					break;
				}
			}
		}
	}

	public SimpleRead() {
		try {
			serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
		} catch (PortInUseException e) {
		}
		try {
			inputStream = serialPort.getInputStream();
		} catch (IOException e) {
		}
		try {
			serialPort.addEventListener(this);
		} catch (TooManyListenersException e) {
		}
		serialPort.notifyOnDataAvailable(true);
		try {
			serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
		}
		readThread = new Thread(this);
		readThread.start();
	}

	public void run() {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
		}
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
			byte[] readBuffer = new byte[1048576];
			// int newData = 0;
			// byte[] readBuffer = getPack(inputStream, 1048576, newData);
			// System.out.println(new String(readBuffer).trim());

			try {
				while (inputStream.available() > 0) {
					// System.out.println("@#$%");
					int numBytes = inputStream.read(readBuffer);
					System.out.println("# numBytes: " + numBytes);
					System.out.println(new String(readBuffer).trim());
				}

			} catch (IOException e) {
			}

			break;
		}
	}

	public byte[] getPack(InputStream is, int PacketLength, int newData) {

		while (true) {
			// PacketLength为数据包长度
			byte[] msgPack = new byte[PacketLength];
			for (int i = 0; i < PacketLength; i++) {
				try {
					if ((newData = is.read()) != -1) {
						int newDataA = is.read(msgPack);
						System.out.println(msgPack);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return msgPack;
		}
	}
}
