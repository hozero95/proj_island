public class IntToByteArray {

	String IntToByteArray(int a) {
 
		String temp = new String("000000000000000000000000"); // 24�ڸ� 0���� ä��
																// string
		String ba = Long.toBinaryString(a); // ������
		String s = (temp.substring(0, 24 - ba.length())) + ba;
		// �ڸ�����ŭ
		// 0�����ͼ�
		// ���̱�
		String s2 = "";
		for (int i = 0; i < 3; i++) {
			s2 += s.substring(8 * i, (i + 1) * 8) + " ";// 2������ 8���� �������� ������ ����
		}
		System.out.println(s2);
		return s2;
	}

}
