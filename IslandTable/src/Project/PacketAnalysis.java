package Project;

public class PacketAnalysis {
	byte[] receive;

	public PacketAnalysis(byte[] receive) {
		this.receive = receive;
	}

	void SendToUI() {
		switch (receive[3]) {
		case 'S':
			// SensorBoard, MainBoard 상태 체크
			if (receive[1] == 0x02) {
				byte sensor_hour = (byte) (receive[7] / 16 * 10 + receive[7] % 16);
				byte sensor_minute = (byte) (receive[8] / 16 * 10 + receive[8] % 16);
				String tem = new Character((char) receive[10]).toString() + new Character((char) receive[11]).toString()
						+ "." + new Character((char) receive[12]).toString();
				String huminity = new Character((char) receive[14]).toString()
						+ new Character((char) receive[15]).toString() + "."
						+ new Character((char) receive[16]).toString();
				String co2 = new Character((char) receive[18]).toString() + new Character((char) receive[19]).toString()
						+ new Character((char) receive[20]).toString() + new Character((char) receive[21]).toString();
				String lux = new Character((char) receive[23]).toString() + new Character((char) receive[24]).toString()
						+ new Character((char) receive[25]).toString() + new Character((char) receive[26]).toString();
				int gas = receive[28];
				if (receive[28] < 0) {
					gas = (int) receive[28] + 256;
				}
				gas = gas + 100;
				Frame.jtfSensorData[0].setText(sensor_hour + " : " + sensor_minute);
				Frame.jtfSensorData[1].setText(tem + "℃");
				Frame.jtfSensorData[2].setText(huminity + "％");
				Frame.jtfSensorData[3].setText(co2 + "ppm");
				Frame.jtfSensorData[4].setText(lux + "lx");
				Frame.jtfSensorData[5].setText(gas + "");
			} else if (receive[1] == 0x01) {
				byte main_hour = (byte) (receive[5] / 16 * 10 + receive[5] % 16);
				byte main_minute = (byte) (receive[6] / 16 * 10 + receive[6] % 16);
				String onoff_ch1 = receive[8] + "";
				String onoff_ch2 = receive[9] + "";
				String onoff_ch3 = receive[10] + "";
				Frame.jtfMainData[0].setText(main_hour + " : " + main_minute);
				Frame.jtfMainData[1].setText(onoff_ch1);
				Frame.jtfMainData[2].setText(onoff_ch2);
				Frame.jtfMainData[3].setText(onoff_ch3);
			}
			break;

		// MainBoard LED Data 체크
		case 's':
			// duty와 pwm의 입력이 거꾸로 되어있음.
			long duty_ch1 = ((receive[7] & 0xff) << 16) | ((receive[6] & 0xff) << 8) | ((receive[5] & 0xff));
			long duty_ch2 = ((receive[14] & 0xff) << 16) | ((receive[13] & 0xff) << 8) | ((receive[12] & 0xff));
			long duty_ch3 = ((receive[21] & 0xff) << 16) | ((receive[20] & 0xff) << 8) | ((receive[19] & 0xff));
			long pwm_ch1 = ((receive[10] & 0xff) << 16) | ((receive[9] & 0xff) << 8) | ((receive[8] & 0xff));
			long pwm_ch2 = ((receive[17] & 0xff) << 16) | ((receive[16] & 0xff) << 8) | ((receive[15] & 0xff));
			long pwm_ch3 = (receive[24] & 0xff) << 16 | (receive[23] & 0xff) << 8 | (receive[22] & 0xff);
			int elec_ch1 = receive[26];
			int elec_ch2 = receive[27];
			int elec_ch3 = receive[28];
			Frame.jtfMainData[4].setText(pwm_ch1 + "");
			Frame.jtfMainData[5].setText(pwm_ch2 + "");
			Frame.jtfMainData[6].setText(pwm_ch3 + "");
			Frame.jtfMainData[7].setText(duty_ch1 + "");
			Frame.jtfMainData[8].setText(duty_ch2 + "");
			Frame.jtfMainData[9].setText(duty_ch3 + "");
			Frame.jtfMainData[10].setText(elec_ch1 + "");
			Frame.jtfMainData[11].setText(elec_ch2 + "");
			Frame.jtfMainData[12].setText(elec_ch3 + "");
			break;

		// MainBoard LED On/Off Time 체크
		case 'R':
			byte hour_ch1_on = (byte) (receive[5] / 16 * 10 + receive[5] % 16);
			byte minute_ch1_on = (byte) (receive[6] / 16 * 10 + receive[6] % 16);

			byte hour_ch2_on = (byte) (receive[10] / 16 * 10 + receive[10] % 16);
			byte minute_ch2_on = (byte) (receive[11] / 16 * 10 + receive[11] % 16);

			byte hour_ch3_on = (byte) (receive[15] / 16 * 10 + receive[15] % 16);
			byte minute_ch3_on = (byte) (receive[16] / 16 * 10 + receive[16] % 16);

			byte hour_ch1_off = (byte) (receive[7] / 16 * 10 + receive[7] % 16);
			byte minute_ch1_off = (byte) (receive[8] / 16 * 10 + receive[8] % 16);

			byte hour_ch2_off = (byte) (receive[12] / 16 * 10 + receive[12] % 16);
			byte minute_ch2_off = (byte) (receive[13] / 16 * 10 + receive[13] % 16);

			byte hour_ch3_off = (byte) (receive[17] / 16 * 10 + receive[17] % 16);
			byte minute_ch3_off = (byte) (receive[18] / 16 * 10 + receive[18] % 16);

			Frame.jtfMainData[13].setText(hour_ch1_on + ":" + minute_ch1_on);
			Frame.jtfMainData[14].setText(hour_ch2_on + ":" + minute_ch2_on);
			Frame.jtfMainData[15].setText(hour_ch3_on + ":" + minute_ch3_on);
			Frame.jtfMainData[16].setText(hour_ch1_off + ":" + minute_ch1_off);
			Frame.jtfMainData[17].setText(hour_ch2_off + ":" + minute_ch2_off);
			Frame.jtfMainData[18].setText(hour_ch3_off + ":" + minute_ch3_off);
			break;
		}
	}
}
