
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class UI extends JFrame implements ActionListener {
	WritingPacket writing;
	PacketStore ps;
	IntToByteArray ia;
	// Connect 변수들

	String[] fn = { "센서보드", "메인보드", "포트설정" };
	JFrame frm = new JFrame("아일랜드식탁_예슬버전");
	JLabel[] lab1 = new JLabel[3];
	static JTextField[] jtf1 = new JTextField[3];
	JButton[] btn1 = new JButton[3];
	JPanel pan1 = new JPanel();
	JLabel clab1 = new JLabel("Wait Connect....");
	JPanel pan2 = new JPanel();

	// LED Control 변수들

	String[] sn = { "RED", "BLUE", "PAN" };
	JRadioButton[] jrb1 = new JRadioButton[3];
	JLabel[] lab2 = new JLabel[3];
	ButtonGroup bg = new ButtonGroup();
	JPanel pan3 = new JPanel();

	int LEDlcount = 0, LEDfcount = 0, LEDbcount = 0;
	String[] tn = { "Power on/off", "0 or 1", "Inten", "(0 ~ 8)", "PWM/DUTY", "on/off time" };
	JLabel[] lab3 = new JLabel[6];
	static JTextField[] jtf2 = new JTextField[6];
	JButton[] btn2 = new JButton[4];
	JPanel pan4 = new JPanel();

	JPanel pan5 = new JPanel();

	JPanel pan6 = new JPanel();

	// Monitoring UI

	// * 3개

	// 첫번째
	String[] fon = { "시간", "온도", "습도", "CO2", "조도", "가스" };
	JLabel[] lab4 = new JLabel[6];
	static JTextField[] jtf3 = new JTextField[6];
	JPanel pan7 = new JPanel();

	JButton btn3 = new JButton("getData");

	JPanel pan8 = new JPanel();

	// 두번째

	JLabel timelab = new JLabel("시간");
	static JTextField timefield = new JTextField();
	JPanel timepanel = new JPanel();

	String[] fin = { " ", "1CH", "2CH", "3CH", "", "동작", "Duty", "PWM", " ", "전류", " ", "on time", "off time", " " };
	JLabel[] lab5 = new JLabel[14];
	static JTextField[] jtf4 = new JTextField[18];
	JButton[] btn4 = new JButton[3];
	int lcount = 0, fcount = 0, bcount = 0;
	JPanel pan9 = new JPanel();
	JPanel pan10 = new JPanel();

	// 세번째

	String[] heads = { "1", "2", "3" };
	Object[][] data = null;
	DefaultTableModel dtm = new DefaultTableModel();// data,heads
	JTable jt = new JTable(dtm);
	JScrollPane jsp = new JScrollPane(jt);

	JPanel pan11 = new JPanel();
	JPanel centerpanel = new JPanel();
	// 맨 밑부분

	static DefaultListModel dlm = new DefaultListModel();
	static JList jl = new JList(dlm);
	static JScrollPane jsp1 = new JScrollPane(jl);
	JPanel pan12 = new JPanel();

	public UI() {
		frm.setSize(965, 550);
		frm.setLayout(new BorderLayout());

		//// Connect UI 생성

		for (int i = 0; i < 3; i++) {
			lab1[i] = new JLabel(fn[i]);
			jtf1[i] = new JTextField();
			btn1[i] = new JButton("확인");
			btn1[i].addActionListener(this); 

		}
		jtf1[2].setText("COM3");

		pan1.setLayout(new GridLayout(3, 3));

		for (int i = 0; i < 3; i++) {
			pan1.add(lab1[i]);
			pan1.add(jtf1[i]);
			pan1.add(btn1[i]);
		}

		pan2.setLayout(new BorderLayout());
		pan2.add(pan1, BorderLayout.CENTER);
		pan2.add(clab1, BorderLayout.SOUTH);
		pan2.setBorder(new TitledBorder("Connect"));

		//// LED Control UI

		for (int i = 0; i < 3; i++) {
			lab2[i] = new JLabel(sn[i]);
			jrb1[i] = new JRadioButton();
			jrb1[i].addActionListener(this);

		}

		for (int i = 0; i < 3; i++)
			bg.add(jrb1[i]);

		pan3.setLayout(new GridLayout(4, 2));

		for (int i = 0; i < 3; i++) {
			pan3.add(lab2[i]);
			pan3.add(jrb1[i]);
		}

		for (int i = 0; i < 6; i++) {
			lab3[i] = new JLabel(tn[i]);
			jtf2[i] = new JTextField();
		}

		for (int i = 0; i < 4; i++) {
			btn2[i] = new JButton("확인");
			btn2[i].addActionListener(this);
		}

		pan4.setLayout(new GridLayout(4, 4));

		for (int i = 0; i < 4; i++) {
			pan4.add(lab3[LEDlcount++]);
			pan4.add(jtf2[LEDfcount++]);
			if (i == 0 || i == 1)
				pan4.add(lab3[LEDlcount++]);
			else
				pan4.add(jtf2[LEDfcount++]);
			pan4.add(btn2[LEDbcount++]);
		}

		pan5.setBorder(new TitledBorder("LED Control"));
		pan5.setLayout(new BorderLayout());
		pan5.add(pan3, BorderLayout.WEST);
		pan5.add(pan4, BorderLayout.CENTER);

		pan6.setLayout(new GridLayout(0, 2));
		pan6.add(pan2);
		pan6.add(pan5);

		// Monitoring UI
		// 첫번째
		for (int i = 0; i < 6; i++) {
			lab4[i] = new JLabel(fon[i]);
			jtf3[i] = new JTextField();
		}

		pan7.setLayout(new GridLayout(6, 2));

		for (int i = 0; i < 6; i++) {
			pan7.add(lab4[i]);
			pan7.add(jtf3[i]);
		}

		pan8.setLayout(new BorderLayout());
		pan8.setBorder(new TitledBorder("SensorBoard"));
		pan8.add(pan7, BorderLayout.CENTER);
		btn3.addActionListener(this);
		pan8.add(btn3, BorderLayout.SOUTH);

		// 두번째

		timepanel.setLayout(new GridLayout(0, 5));
		timepanel.add(timelab);
		timepanel.add(timefield);

		for (int i = 0; i < lab5.length; i++)
			lab5[i] = new JLabel(fin[i]);
		for (int i = 0; i < jtf4.length; i++)
			jtf4[i] = new JTextField();
		for (int i = 0; i < btn4.length; i++) {
			btn4[i] = new JButton("CHECK");
			btn4[i].addActionListener(this);
		}

		pan9.setLayout(new GridLayout(7, 5));
		for (int i = 0; i < 35; i++) {
			if (i % 5 == 4) {
				if (i == 4 || i == 19 || i == 24 || i == 34)
					pan9.add(lab5[lcount++]);
				else
					pan9.add(btn4[bcount++]);
			} else if (i > 5) {
				if (i % 5 != 0)
					pan9.add(jtf4[fcount++]);
				else
					pan9.add(lab5[lcount++]);
			} else
				pan9.add(lab5[lcount++]);
		}

		pan10.setLayout(new BorderLayout());
		pan10.setBorder(new TitledBorder("Main Board"));
		pan10.add(timepanel, BorderLayout.NORTH);
		pan10.add(pan9, BorderLayout.CENTER);

		// 세번째

		pan11.setLayout(new GridLayout(0, 1));
		pan11.setBorder(new TitledBorder("ETC"));
		pan11.add(jsp);

		centerpanel.setLayout(new BorderLayout());
		centerpanel.setBorder(new TitledBorder("Main Border"));
		centerpanel.add(pan8, BorderLayout.WEST);
		centerpanel.add(pan10, BorderLayout.CENTER);
		centerpanel.add(pan11, BorderLayout.EAST);

		// 맨 밑부분

		pan12.setLayout(new GridLayout(0, 1));
		pan12.setBorder(new TitledBorder("PacketList"));
		pan12.add(jsp1);

		frm.add(pan6, BorderLayout.NORTH);
		frm.add(centerpanel, BorderLayout.CENTER);
		frm.add(pan12, BorderLayout.SOUTH);
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			ps = new PacketStore();
			ia = new IntToByteArray();
			StringTokenizer sttz1, sttz2;
			// PORT와 CONNECT////////////////////////////////////
			if (e.getSource().equals(btn1[2])) {
				writing = new WritingPacket(jtf1[2].getText());
				clab1.setText(writing.Connect());
				btn1[2].setEnabled(false);

			}
			byte cn = 0;// 채널번호
			if (jrb1[0].isSelected())
				cn = 1;
			else if (jrb1[1].isSelected())
				cn = 2;
			else if (jrb1[2].isSelected())
				cn = 3;
			// 메인보드, 상태체크 설정
			if (e.getSource().equals(btn1[0])) {
				sttz1 = new StringTokenizer(jtf1[0].getText(), ":");
				byte hour = Byte.parseByte(sttz1.nextToken());
				byte minute = Byte.parseByte(sttz1.nextToken());
				byte buffer[] = ps.sensorTime((byte) 0x02, hour, minute);// 센서시간
				writing.Send(buffer);
				jtf1[0].setText("");
			} else if (e.getSource().equals(btn1[1])) {
				sttz1 = new StringTokenizer(jtf1[1].getText(), ":");
				byte hour = Byte.parseByte(sttz1.nextToken());
				byte minute = Byte.parseByte(sttz1.nextToken());
				byte buffer[] = ps.sensorTime((byte) 0x01, hour, minute);// 메인보드
				writing.Send(buffer);
				jtf1[1].setText("");
			}
			/////////////////////////////////////////////////////

			// LED CONTROL
			// LED ON/OFF(0~1)
			if (e.getSource().equals(btn2[0])) {
				byte[] buffer = ps.LED_CONTROLL(cn, Byte.parseByte(jtf2[0].getText()));
				writing.Send(buffer);
				jtf2[0].setText("");
			}
			// INTENSITY값(0~8)
			if (e.getSource().equals(btn2[1])) {
				byte[] buffer = ps.INTENSITY(cn, Byte.parseByte(jtf2[1].getText()));
				writing.Send(buffer);
				jtf2[1].setText("");
			}
			// ON OFF TIME
			if (e.getSource().equals(btn2[3])) {
				sttz1 = new StringTokenizer(jtf2[4].getText(), ":");
				sttz2 = new StringTokenizer(jtf2[5].getText(), ":");
				byte on_hour = Byte.parseByte(sttz1.nextToken());
				byte on_minute = Byte.parseByte(sttz1.nextToken());
				byte off_hour = Byte.parseByte(sttz2.nextToken());
				byte off_minute = Byte.parseByte(sttz2.nextToken());
				byte[] buffer = ps.ONOFF_TIME(cn, on_hour, on_minute, off_hour, off_minute);
				writing.Send(buffer);
				jtf2[4].setText("");
				jtf2[5].setText("");
			}
			// PWM/DUTY
			if (e.getSource().equals(btn2[2])) {
				// 받아온 값 -> 이진수 -> 8개씩 나눠서 바이트배열에 저장
				sttz1 = new StringTokenizer(ia.IntToByteArray(Integer.parseInt(jtf2[2].getText())), " ");
				sttz2 = new StringTokenizer(ia.IntToByteArray(Integer.parseInt(jtf2[3].getText())), " ");
				byte[] pwmByte = new byte[3];
				byte[] dutyByte = new byte[3];
				for (int i = 0; i < dutyByte.length; i++) {
					if (sttz1.hasMoreTokens() == false || sttz2.hasMoreTokens() == false)
						break;
					Integer byteAsInt_1 = Integer.parseInt(sttz1.nextToken(), 2);
					pwmByte[i] = byteAsInt_1.byteValue();
					Integer byteAsInt_2 = Integer.parseInt(sttz2.nextToken(), 2);
					dutyByte[i] = byteAsInt_2.byteValue();
					System.out.println(pwmByte[i] + " " + dutyByte[i]);
				}
				byte[] buffer = ps.PWM_DUTY(cn, pwmByte, dutyByte);
				writing.Send(buffer);
				jtf2[2].setText("");
				jtf2[3].setText("");
			}
			///// Mainboard///////////////////////////////////////////////////////
			if (e.getSource().equals(btn3)) {
				byte[] buffer = ps.CONDITION_1();// 상태체크
				writing.Send(buffer);
			}
			if (e.getSource().equals(btn4[0])) {
				byte[] buffer = ps.LED_ONOFF_CHECK();
				writing.Send(buffer);
			} else if (e.getSource().equals(btn4[1])) {
				byte[] buffer = ps.PWM_DUTY_CHECK();
				writing.Send(buffer);
			} else if (e.getSource().equals(btn4[2])) {
				byte[] buffer = ps.ONOFF_TIME_CHECK();
				writing.Send(buffer);// ON OFF TIME 가져오기

			}

		} catch (Exception e2) {
			clab1.setText("RETRY CONNECT!!!");
		}

	}
}
