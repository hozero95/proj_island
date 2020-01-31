public class IntToByteArray {

	String IntToByteArray(int a) {
 
		String temp = new String("000000000000000000000000"); // 24자리 0으로 채운
																// string
		String ba = Long.toBinaryString(a); // 이진수
		String s = (temp.substring(0, 24 - ba.length())) + ba;
		// 자릿수만큼
		// 0가져와서
		// 붙이기
		String s2 = "";
		for (int i = 0; i < 3; i++) {
			s2 += s.substring(8 * i, (i + 1) * 8) + " ";// 2진수를 8개씩 공백으로 나누어 묶기
		}
		System.out.println(s2);
		return s2;
	}

}
