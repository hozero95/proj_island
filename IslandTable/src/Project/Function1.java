package Project;

public class Function1 extends Thread {
	Packet packet;

	public Function1() {
		packet = new Packet();
	}

	void sleep() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
	}

	// Red, Blue Α¶Έν ±τΊύΐΜ±β
	public void run() {
		byte[] message;
		while (Frame.lightOnOff[0]) {
			message = packet.Ch_OnOff((byte) 0x01, (byte) 0x01);
			Frame.connect.sendPacket(message);
			sleep();
			message = packet.Ch_OnOff((byte) 0x02, (byte) 0x01);
			Frame.connect.sendPacket(message);
			sleep();
			message = packet.Ch_OnOff((byte) 0x01, (byte) 0x00);
			Frame.connect.sendPacket(message);
			sleep();
			message = packet.Ch_OnOff((byte) 0x02, (byte) 0x00);
			Frame.connect.sendPacket(message);
			sleep();
		}
	}
}
