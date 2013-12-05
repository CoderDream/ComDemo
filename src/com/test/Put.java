package com.test;

import java.io.IOException;

public class Put {

	// 2011.9.17
	public String write() {

		// 发送指令R，仪器发送一次净重数据
		SerialBean.WritePort("R");
		// 读取数据
		SerialBean.ReadPort();
		String temp = SerialBean.result.trim(); // 我这里temp是形如 wn125.000kg 的数据
		if (!temp.equals("") && temp.length() == 11) {
			return (change(temp)).toString();

		} else {
			return "";
		}

	}

	// 响应开始称重
	public String startWeight(String num) {

		int n = Integer.parseInt(num.trim());
		SerialBean SB = new SerialBean(n);
		SB.Initialize();
		return SerialBean.openSignal + ""; // 返回初始化信息
	}

	// 响应停止称重
	public void endWeight() {

		try {
			// 关闭输入、输出流
			SerialBean.in.close();
			SerialBean.out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (SerialBean.serialPort != null) {
			SerialBean.serialPort.close(); // 关闭串口
		}

		SerialBean.serialPort = null;
		SerialBean.portId = null;

		SerialBean.result = "";

	}

	/**
	 * 将形如 wn125.000kg 格式的重量转换为 125.000 (kg)(四舍五入，小数点后保留两位)
	 */
	public String change(String source) {
		Double result = 0.0;
		String s1 = source.substring(2, 9);
		try {
			result = Double.parseDouble(s1);
			result = Arith.round(result, 2);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return result.toString();
	}

	public static void main(String[] args) {
		Put put = new Put();
		put.startWeight("3");
		put.endWeight();
	}

}
