package Project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Connect implements SerialPortEventListener {
	String port;
	SerialPort serialPort;

	PacketAnalysis analysis;

	StringTokenizer strb;

	Connect(String port) {
		this.port = port;
	}

	void connect() {
		serialPort = new SerialPort(port);
		try {
			serialPort.openPort();// Open serial port
			serialPort.setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			serialPort.addEventListener(this);
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}

	void sendPacket(byte[] message) {
		try {
			serialPort.writeBytes(message);
			printLog("Computer", message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void printLog(String type, byte[] message) {
		String text = "";
		SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
		String sysDate = date.format(new Date());

		for (int i = 0; i < message.length; i++) {
			text += String.format("%x", message[i]) + " ";
		}
		Frame.jtaLog.append(type + "(" + sysDate + ") : " + text + "\n");
		Frame.jtaLog.setCaretPosition(Frame.jtaLog.getText().length());
	}

	public void serialEvent(SerialPortEvent e) {
		if (e.isRXCHAR()) {
			String result = "";
			byte message[] = new byte[30];
			try {
				byte[] receive = serialPort.readBytes();
				for (int i = 0; i < receive.length; i++) {
					if (receive.length < 30)
						return;
					if (receive[i] == 0x02 && receive[i + 29] == 0x03) {
						for (int j = i; j < receive.length; j++) {
							result += receive[j] + " ";
							strb = new StringTokenizer(result, " ");
						}
						for (int j = 0; j < message.length; j++) {
							if (strb.hasMoreTokens() == false)
								break;
							message[j] = Byte.parseByte(strb.nextToken());
						}
						printLog("Firmware", message);
						analysis = new PacketAnalysis(message);
						analysis.SendToUI();
						analysis = null;
						break;
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
