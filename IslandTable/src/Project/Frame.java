package Project;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Frame extends JFrame {
	private Container ct;
	private JPanel jpTop, jpCen, jpToper, jpConnect, jpControl, jpCenter, jpSensor, jpMain, jpFunction, jpLog;
	private JButton jbPort, jbMainBoard, jbSensorBoard, jbOnOff, jbIntensity, jbPWMDuty, jbTime, jbSensorData,
			jbMainData;
	private JButton[] jbLight;
	private JTextField jtfPort, jtfMainBoard, jtfSensorBoard, jtfIntensity, jtfPWM, jtfDuty, jtfOnTime, jtfOffTime;
	static JTextField[] jtfSensorData;
	private String[] sensorData = { "시간", "온도", "습도", "CO2", "조도", "가스" };
	static JTextField[] jtfMainData;
	private JRadioButton[] jrbChannel;
	private ButtonGroup group;
	private String[] channel = { "Red", "Blue", "Pan" };
	static JTextArea jtaLog;
	static JScrollPane jspLog;

	static Connect connect;
	private Packet packet;
	private ToByte toByte;
	private Function1 func1;
	private Function2 func2;
//	private AutoUpdate auto;

	private boolean[] chOnOff;
	static boolean[] lightOnOff;
	private boolean[] lightCheck;

	private void initComps() {
		ct = getContentPane();

		// Connect, Control, Function Panel
		jpTop = new JPanel();
		jpToper = new JPanel();

		// Connect
		jpConnect = new JPanel();
		jtfPort = new JTextField("COM4");
		jtfMainBoard = new JTextField("HH:MM");
		jtfSensorBoard = new JTextField("HH:MM");
		jbPort = new JButton("연결");
		jbMainBoard = new JButton("설정");
		jbSensorBoard = new JButton("설정");

		// Control
		jpControl = new JPanel();
		jrbChannel = new JRadioButton[3];
		group = new ButtonGroup();
		for (int i = 0; i < jrbChannel.length; i++) {
			jrbChannel[i] = new JRadioButton(channel[i]);
			group.add(jrbChannel[i]);
		}
		jtfIntensity = new JTextField("0~10");
		jtfPWM = new JTextField("0~255");
		jtfDuty = new JTextField("0~255");
		jtfOnTime = new JTextField("HH:MM");
		jtfOffTime = new JTextField("HH:MM");
		chOnOff = new boolean[3];
		for (int i = 0; i < chOnOff.length; i++) {
			chOnOff[i] = true;
		}
		jbOnOff = new JButton("On/Off");
		jbIntensity = new JButton("설정");
		jbPWMDuty = new JButton("설정");
		jbTime = new JButton("설정");

		// Function
		jpFunction = new JPanel();
		jbLight = new JButton[2];
		for (int i = 0; i < jbLight.length; i++) {
			jbLight[i] = new JButton("On");
		}
		lightOnOff = new boolean[2];
		for (int i = 0; i < lightOnOff.length; i++) {
			lightOnOff[i] = false;
		}
		lightCheck = new boolean[2];
		for (int i = 0; i < lightCheck.length; i++) {
			lightCheck[i] = true;
		}

		// SensorBoard, MainBoard, Log Panel
		jpCen = new JPanel();
		jpCenter = new JPanel();

		// SensorBoard
		jpSensor = new JPanel();
		jtfSensorData = new JTextField[6];
		for (int i = 0; i < jtfSensorData.length; i++) {
			jtfSensorData[i] = new JTextField();
		}
		jbSensorData = new JButton("확인");

		// MainBoard
		jpMain = new JPanel();
		jtfMainData = new JTextField[19];
		for (int i = 0; i < jtfMainData.length; i++) {
			jtfMainData[i] = new JTextField();
		}
		jbMainData = new JButton("확인");

		// Log
		jpLog = new JPanel();
		jtaLog = new JTextArea();
		jspLog = new JScrollPane(jtaLog);

		packet = new Packet();
		toByte = new ToByte();
	}

	private void addComps() {
		// Connect Pannel
		jpConnect.setLayout(new GridLayout(4, 3, 5, 5));
		jpConnect.setBorder(new TitledBorder("Connect"));
		jpConnect.add(new JLabel("포트 설정"));
		jpConnect.add(jtfPort);
		jpConnect.add(jbPort);
		jpConnect.add(new JLabel("센서 시간 설정"));
		jpConnect.add(jtfSensorBoard);
		jpConnect.add(jbSensorBoard);
		jpConnect.add(new JLabel("메인 시간 설정"));
		jpConnect.add(jtfMainBoard);
		jpConnect.add(jbMainBoard);
		jpConnect.add(new JLabel(""));
		jpConnect.add(new JLabel(""));
		jpConnect.add(new JLabel(""));

		// Control Pannel
		jpControl.setLayout(new GridLayout(4, 4, 5, 5));
		jpControl.setBorder(new TitledBorder("Control"));
		for (int i = 0; i < jrbChannel.length; i++) {
			jpControl.add(jrbChannel[i]);
		}
		jpControl.add(jbOnOff);
		jpControl.add(new JLabel("Intensity"));
		jpControl.add(jtfIntensity);
		jpControl.add(new JLabel(""));
		jpControl.add(jbIntensity);
		jpControl.add(new JLabel("PWM/Duty"));
		jpControl.add(jtfPWM);
		jpControl.add(jtfDuty);
		jpControl.add(jbPWMDuty);
		jpControl.add(new JLabel("On/Off Time"));
		jpControl.add(jtfOnTime);
		jpControl.add(jtfOffTime);
		jpControl.add(jbTime);

		// Function Pannel
		jpFunction.setLayout(new GridLayout(4, 1, 5, 5));
		jpFunction.setBorder(new TitledBorder("Function"));
		jpFunction.add(new JLabel("Light Flash", JLabel.CENTER));
		for (int i = 0; i < jbLight.length; i++) {
			jpFunction.add(jbLight[i]);
		}
		jpFunction.add(new JLabel(""));

		// SensorBoard Pannel
		jpSensor.setLayout(new GridLayout(2, 7, 5, 5));
		jpSensor.setBorder(new TitledBorder("Sensor Board"));
		jpSensor.add(new JLabel(""));
		for (int i = 0; i < sensorData.length; i++) {
			jpSensor.add(new JLabel(sensorData[i], JLabel.CENTER));
		}
		jpSensor.add(jbSensorData);
		for (int i = 0; i < jtfSensorData.length; i++) {
			jpSensor.add(jtfSensorData[i]);
		}

		// MainBoard Pannel
		jpMain.setLayout(new GridLayout(8, 5, 5, 4));
		jpMain.setBorder(new TitledBorder("Main Board"));
		jpMain.add(jbMainData);
		jpMain.add(new JLabel(""));
		jpMain.add(new JLabel("시간", JLabel.CENTER));
		jpMain.add(jtfMainData[0]);
		jpMain.add(new JLabel(""));
		jpMain.add(new JLabel("Ch1", JLabel.CENTER));
		jpMain.add(new JLabel("Ch2", JLabel.CENTER));
		jpMain.add(new JLabel("Ch3", JLabel.CENTER));
		jpMain.add(new JLabel("동작"));
		for (int i = 0; i < 3; i++) {
			jpMain.add(jtfMainData[i + 1]);
		}
		jpMain.add(new JLabel("PWM"));
		for (int i = 0; i < 3; i++) {
			jpMain.add(jtfMainData[i + 4]);
		}
		jpMain.add(new JLabel("Duty"));
		for (int i = 0; i < 3; i++) {
			jpMain.add(jtfMainData[i + 7]);
		}
		jpMain.add(new JLabel("전류"));
		for (int i = 0; i < 3; i++) {
			jpMain.add(jtfMainData[i + 10]);
		}
		jpMain.add(new JLabel("On Time"));
		for (int i = 0; i < 3; i++) {
			jpMain.add(jtfMainData[i + 13]);
		}
		jpMain.add(new JLabel("Off Time"));
		for (int i = 0; i < 3; i++) {
			jpMain.add(jtfMainData[i + 16]);
		}

		// Log Pannel
		jpLog.setLayout(new GridLayout(1, 1, 5, 5));
		jpLog.setBorder(new TitledBorder("Packet Log"));
		jpLog.add(jspLog);

		// 전체 Layout 설정
		jpToper.setLayout(new GridLayout(1, 2, 5, 5));
		jpToper.add(jpConnect);
		jpToper.add(jpControl);

		jpTop.setLayout(new BorderLayout(5, 5));
		jpTop.add(jpToper, BorderLayout.CENTER);
		jpTop.add(jpFunction, BorderLayout.EAST);

		jpCenter.setLayout(new BorderLayout(5, 5));
		jpCenter.add(jpSensor, BorderLayout.NORTH);
		jpCenter.add(jpMain, BorderLayout.CENTER);

		jpCen.setLayout(new GridLayout(1, 2, 5, 5));
		jpCen.add(jpCenter);
		jpCen.add(jpLog);

		ct.setLayout(new BorderLayout(5, 5));
		ct.add(jpTop, BorderLayout.NORTH);
		ct.add(jpCen, BorderLayout.CENTER);
	}

	private void showWnd(String title, int w, int h) {
		setTitle(title);
		setSize(w, h);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public Frame(String title, int w, int h) {
		initComps();
		addComps();
		addActionListener();
		showWnd(title, w, h);
	}

	private void addActionListener() {

		// Connect
		jbPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect = new Connect(jtfPort.getText());
				connect.connect();
				jtaLog.append("연결되었습니다.\n");
//				auto = new AutoUpdate();
//				auto.start();
				jtfPort.setEditable(false);
				jbPort.setEnabled(false);
			}
		});
		jbSensorBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] str = jtfSensorBoard.getText().split(":");
				byte[] time = toByte.ToHexByteTime(str);
				byte[] message = packet.RTC_Time((byte) 0x02, time);

				connect.sendPacket(message);
			}
		});
		jbMainBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] str = jtfMainBoard.getText().split(":");
				byte[] time = toByte.ToHexByteTime(str);
				byte[] message = packet.RTC_Time((byte) 0x01, time);

				connect.sendPacket(message);
			}
		});

		// Control
		jbOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jrbChannel[0].isSelected()) {
					if (chOnOff[0]) {
						byte[] message = packet.Ch_OnOff((byte) 0x01, (byte) 0x01);
						connect.sendPacket(message);
						chOnOff[0] = false;
					} else {
						byte[] message = packet.Ch_OnOff((byte) 0x01, (byte) 0x00);
						connect.sendPacket(message);
						chOnOff[0] = true;
					}
				} else if (jrbChannel[1].isSelected()) {
					if (chOnOff[1]) {
						byte[] message = packet.Ch_OnOff((byte) 0x02, (byte) 0x01);
						connect.sendPacket(message);
						chOnOff[1] = false;
					} else {
						byte[] message = packet.Ch_OnOff((byte) 0x02, (byte) 0x00);
						connect.sendPacket(message);
						chOnOff[1] = true;
					}
				} else if (jrbChannel[2].isSelected()) {
					if (chOnOff[2]) {
						byte[] message = packet.Ch_OnOff((byte) 0x03, (byte) 0x01);
						connect.sendPacket(message);
						chOnOff[2] = false;
					} else {
						byte[] message = packet.Ch_OnOff((byte) 0x03, (byte) 0x00);
						connect.sendPacket(message);
						chOnOff[2] = true;
					}
				}
			}
		});
		jbIntensity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = jtfIntensity.getText();
				byte intensity = toByte.ToHexByte(str);
				if (jrbChannel[0].isSelected()) {
					byte[] message = packet.Intensity((byte) 0x01, intensity);
					connect.sendPacket(message);
				} else if (jrbChannel[1].isSelected()) {
					byte[] message = packet.Intensity((byte) 0x02, intensity);
					connect.sendPacket(message);
				} else if (jrbChannel[2].isSelected()) {
					byte[] message = packet.Intensity((byte) 0x03, intensity);
					connect.sendPacket(message);
				}
				jtfIntensity.setText("");
			}
		});
		jbPWMDuty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String str1 = jtfPWM.getText();
				String str2 = jtfDuty.getText();
				byte PWM = toByte.ToHexByte(str1);
				byte duty = toByte.ToHexByte(str2);
				if (jrbChannel[0].isSelected()) {
					byte[] message = packet.PWM_Duty((byte) 0x01, PWM, duty);
					connect.sendPacket(message);
				} else if (jrbChannel[1].isSelected()) {
					byte[] message = packet.PWM_Duty((byte) 0x02, PWM, duty);
					connect.sendPacket(message);
				} else if (jrbChannel[2].isSelected()) {
					byte[] message = packet.PWM_Duty((byte) 0x03, PWM, duty);
					connect.sendPacket(message);
				}
				jtfPWM.setText("");
				jtfDuty.setText("");
			}
		});
		jbTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] str1 = jtfOnTime.getText().split(":");
				String[] str2 = jtfOffTime.getText().split(":");
				byte[] onTime = toByte.ToHexByteTime(str1);
				byte[] offTime = toByte.ToHexByteTime(str2);
				if (jrbChannel[0].isSelected()) {
					byte[] message = packet.Ch_OnOff_Time((byte) 0x01, onTime, offTime);
					connect.sendPacket(message);
				} else if (jrbChannel[1].isSelected()) {
					byte[] message = packet.Ch_OnOff_Time((byte) 0x02, onTime, offTime);
					connect.sendPacket(message);
				} else if (jrbChannel[2].isSelected()) {
					byte[] message = packet.Ch_OnOff_Time((byte) 0x03, onTime, offTime);
					connect.sendPacket(message);
				}
				jtfOnTime.setText("");
				jtfOffTime.setText("");
			}
		});

		// Function
		jbLight[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lightCheck[0]) {
					lightOnOff[0] = true;
					func1 = new Function1();
					func1.start();
					lightCheck[0] = false;
					jbLight[0].setText("Off");
				} else {
					lightOnOff[0] = false;
					lightCheck[0] = true;
					byte[] message = packet.PWM_Duty((byte) 0x01, (byte) 0xFF, (byte) 0xFF);
					connect.sendPacket(message);
					jbLight[0].setText("On");
				}
			}
		});
		jbLight[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lightCheck[1]) {
					lightOnOff[1] = true;
					func2 = new Function2();
					func2.start();
					lightCheck[1] = false;
					jbLight[1].setText("Off");
				} else {
					lightOnOff[1] = false;
					lightCheck[1] = true;
					jbLight[1].setText("On");
				}
			}
		});

		// SensorBoard
		jbSensorData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtaLog.append("SensorBoard Update\n");
				byte[] message = packet.Sensor_Check();
				connect.sendPacket(message);
			}
		});

		// MainBoard
		jbMainData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtaLog.append("MainBoard Update\n");
				byte[] message;
				message = packet.Main_Check();
				connect.sendPacket(message);
				sleep();
				message = packet.PWM_Duty_Check();
				connect.sendPacket(message);
				sleep();
				message = packet.OnOff_Time_Check();
				connect.sendPacket(message);
			}
		});
	}

	void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
	}
}
