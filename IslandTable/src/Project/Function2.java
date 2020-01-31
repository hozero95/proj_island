package Project;

public class Function2 extends Thread {
	Packet packet;
	ToByte toByte;

	public Function2() {
		packet = new Packet();
		toByte = new ToByte();
	}

	void sleep() {
		try {
			Thread.sleep(1750);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
	}

	// 1�ʸ��� Red ���� ��� ��ȭ�ϱ�
	public void run() {
		int duty = 20;
		boolean dutyCheck = true;
		byte[] message;
		while (Frame.lightOnOff[1]) {
			if (dutyCheck) {
				duty = duty + 20;
				if (duty > 230) {
					dutyCheck = false;
				}
			} else {
				duty = duty - 20;
				if (duty < 30) {
					dutyCheck = true;
				}
			}
			System.out.println(duty);
			byte dutyValue = toByte.intToHexByte(duty);
			message = packet.PWM_Duty((byte) 0x01, (byte) 0xFF, dutyValue);
			Frame.connect.sendPacket(message);
			sleep();
		}
	}
}
