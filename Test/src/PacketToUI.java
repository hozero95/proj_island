
public class PacketToUI {
	byte[] receive;

	public PacketToUI(byte[] receive) {
		this.receive = receive;
	}

	void SendToUI() {
		switch (receive[3]) {
		case 'S':
			// 시간, 채널 onoff 여부
			if (receive[1] == 0x02) {
				// 16진수로 되어있는 시간을 10진수로 바꿔주기
				String a = "";
				a = String.format("%x", receive[7]);
				int ononff_hour = Integer.valueOf(a, 16);
				a = String.format("%x", receive[8]);
				int onoff_minute = Integer.valueOf(a, 16);
				String tem = new Character((char) receive[10]).toString() + new Character((char) receive[11]).toString()
						+ "." + new Character((char) receive[12]).toString();
				String huminity = new Character((char) receive[14]).toString()
						+ new Character((char) receive[15]).toString() + "."
						+ new Character((char) receive[16]).toString();
				String co2 = new Character((char) receive[18]).toString() + new Character((char) receive[19]).toString()
						+ new Character((char) receive[20]).toString() + new Character((char) receive[21]).toString();
				String lux = new Character((char) receive[23]).toString() + new Character((char) receive[24]).toString()
						+ new Character((char) receive[25]).toString() + new Character((char) receive[26]).toString();
				int gas = Integer.parseInt(String.format("%x", receive[28]));
				UI.jtf3[0].setText(ononff_hour + ":" + onoff_minute);
				UI.jtf3[1].setText(tem + "℃");
				UI.jtf3[2].setText(huminity + "％");
				UI.jtf3[3].setText(co2 + "％");
				UI.jtf3[4].setText(lux + "lux");
				UI.jtf3[5].setText(gas + "");
				break;

			} else if (receive[1] == 0x01) {
				String a = "";
				a = String.format("%x", receive[5]);
				int main_hour = Integer.valueOf(a, 16);
				a = String.format("%x", receive[6]);
				int main_minute = Integer.valueOf(a, 16);
				String onoff_ch1 = receive[8] + "";
				String onoff_ch2 = receive[9] + "";
				String onoff_ch3 = receive[10] + "";
				UI.timefield.setText(main_hour + ":" + main_minute);
				UI.jtf4[0].setText(onoff_ch1);
				UI.jtf4[1].setText(onoff_ch2);
				UI.jtf4[2].setText(onoff_ch3);
				break;
			}

			// pwm, duty, 전류
		case 's':
			long pwm_ch1 = ((receive[7] & 0xff) << 16) | ((receive[6] & 0xff) << 8) | ((receive[5] & 0xff));
			long pwm_ch2 = ((receive[14] & 0xff) << 16) | ((receive[13] & 0xff) << 8) | ((receive[12] & 0xff));
			long pwm_ch3 = ((receive[21] & 0xff) << 16) | ((receive[20] & 0xff) << 8) | ((receive[19] & 0xff));
			long duty_ch1 = ((receive[10] & 0xff) << 16) | ((receive[9] & 0xff) << 8) | ((receive[8] & 0xff));
			long duty_ch2 = ((receive[17] & 0xff) << 16) | ((receive[16] & 0xff) << 8) | ((receive[15] & 0xff));
			long duty_ch3 = (receive[24] & 0xff) << 16 | (receive[23] & 0xff) << 8 | (receive[22] & 0xff);
			int elec_ch1 = (receive[26] & 0xff);
			int elec_ch2 = (receive[27] & 0xff);
			int elec_ch3 = (receive[28] & 0xff);
			UI.jtf4[3].setText(pwm_ch1 + "");
			UI.jtf4[4].setText(pwm_ch2 + "");
			UI.jtf4[5].setText(pwm_ch3 + "");
			UI.jtf4[6].setText(duty_ch1 + "");
			UI.jtf4[7].setText(duty_ch2 + "");
			UI.jtf4[8].setText(duty_ch3 + "");
			UI.jtf4[9].setText(elec_ch1 + "");
			UI.jtf4[10].setText(elec_ch2 + "");
			UI.jtf4[11].setText(elec_ch3 + "");
			break;

		// onoff 시간
		case 'R':
			String a = "";
			a = String.format("%x", receive[5]);
			int hour_ch1_on = Integer.valueOf(a, 16);
			a = String.format("%x", receive[6]);
			int minute_ch1_on = Integer.valueOf(a, 16);

			a = String.format("%x", receive[10]);
			int hour_ch2_on = Integer.valueOf(a, 16);
			a = String.format("%x", receive[11]);
			int minute_ch2_on = Integer.valueOf(a, 16);

			a = String.format("%x", receive[15]);
			int hour_ch3_on = Integer.valueOf(a, 16);
			a = String.format("%x", receive[16]);
			int minute_ch3_on = Integer.valueOf(a, 16);

			a = String.format("%x", receive[7]);
			int hour_ch1_off = Integer.valueOf(a, 16);
			a = String.format("%x", receive[8]);
			int minute_ch1_off = Integer.valueOf(a, 16);

			a = String.format("%x", receive[12]);
			int hour_ch2_off = Integer.valueOf(a, 16);
			a = String.format("%x", receive[13]);
			int minute_ch2_off = Integer.valueOf(a, 16);

			a = String.format("%x", receive[17]);
			int hour_ch3_off = Integer.valueOf(a, 16);
			a = String.format("%x", receive[18]);
			int minute_ch3_off = Integer.valueOf(a, 16);

			UI.jtf4[12].setText(hour_ch1_on + ":" + minute_ch1_on);
			UI.jtf4[13].setText(hour_ch2_on + ":" + minute_ch2_on);
			UI.jtf4[14].setText(hour_ch3_on + ":" + minute_ch3_on);
			UI.jtf4[15].setText(hour_ch1_off + ":" + minute_ch1_off);
			UI.jtf4[16].setText(hour_ch2_off + ":" + minute_ch2_off);
			UI.jtf4[17].setText(hour_ch3_off + ":" + minute_ch3_off);
			break;

		}

	}

}
