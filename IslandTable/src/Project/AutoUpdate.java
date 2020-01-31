package Project;

public class AutoUpdate extends Thread {
	Packet packet;
	
	public AutoUpdate() {
		packet = new Packet();
	}
	
	void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
	}
	
	void sleepCycle() {
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
	}
	
	public void run() {
		byte[] message;
		while(true) {
			Frame.jtaLog.append("Board Update\n");
			message = packet.Sensor_Check();
			Frame.connect.sendPacket(message);
			sleep();
			message = packet.Main_Check();
			Frame.connect.sendPacket(message);
			sleep();
			message = packet.PWM_Duty_Check();
			Frame.connect.sendPacket(message);
			sleep();
			message = packet.OnOff_Time_Check();
			Frame.connect.sendPacket(message);
			sleepCycle();
		}
	}
}
