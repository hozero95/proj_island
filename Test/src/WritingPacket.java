
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class WritingPacket implements SerialPortEventListener {
	PacketStore ps = new PacketStore();
	SerialPort serialport;
	String portID;
	StringTokenizer strb;
	int count;
	PacketToUI divise;

	WritingPacket(String portID) {
		this.portID = portID;
	}

	String Connect() {
		serialport = new SerialPort(portID);
		try {
			serialport.openPort();
			serialport.setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			serialport.addEventListener(this);
			// 이벤트발생하면!!
		} catch (Exception e) {
			return "RETRY CONNECT!!!";
		}
		return "Success Connect";
	}

	void Send(byte[] buffer) {
		try {
			Print("USER", buffer);
			serialport.writeBytes(buffer);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	void Print(String type, byte[] buffer) {
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		String sysdate = sf.format(new Date());
		String text = "";
		for (int i = 0; i < buffer.length; i++) {
			text += String.format("%x", buffer[i]) + "  ";
		}
		UI.dlm.addElement(sysdate + " " + type + " : " + text);
		UI.jsp1.getVerticalScrollBar().setValue(UI.jsp1.getVerticalScrollBar().getMaximum());// 스크롤바
																								// 자동
																								// 내리기
	}

	@Override
	public void serialEvent(SerialPortEvent event) {

		if (event.isRXCHAR()) {
			Convert con = new Convert();
			String result = "";
			byte buffer[] = new byte[30];
			try {
				byte[] receive = serialport.readBytes();
				for (int i = 0; i < receive.length; i++) {
					if (receive.length < 30)
						return;
					if (receive[i] == 0x02 && receive[i + 29] == 0x03) {
						// 버퍼어레이 잘라낼 수 있는 방q
						for (int j = i; j < receive.length; j++) {
							result += receive[j] + " ";
							strb = new StringTokenizer(result, " ");
						}
						for (int j = 0; j < buffer.length; j++) {
							if (strb.hasMoreTokens() == false)
								break;
							buffer[j] = Byte.parseByte(strb.nextToken());
						}
						Print("PLANT", buffer);
						divise = new PacketToUI(buffer);
						divise.SendToUI();
						divise = null;
						break;
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("오류다임마 ~ : " + e.getMessage());
				e.printStackTrace();
			}

		}

	}

}
