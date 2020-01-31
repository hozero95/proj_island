package Project;

public class ToByte {

	byte ToHexByte(String str) {
		int strToInt = Integer.parseInt(str);
		String intToHex = Integer.toString(((byte) strToInt & 0xff) + 0x100, 16).substring(1);
		byte result = hexStringToByteArray(intToHex);
		return result;
	}

	byte hexStringToByteArray(String str) {
		byte data = (byte) ((Character.digit(str.charAt(0), 16) << 4) + Character.digit(str.charAt(1), 16));
		return data;
	}

	byte intToHexByte(int value) {
		String intToHex = Integer.toString(((byte) value & 0xff) + 0x100, 16).substring(1);
		byte result = hexStringToByteArray(intToHex);
		return result;
	}

	byte[] ToHexByteTime(String[] str) {
		byte[] time = new byte[str.length];
		for (int i = 0; i < time.length; i++) {
			time[i] = Byte.parseByte(toHex(str[i]));
		}
		return time;
	}

	String toHex(String str) {
		int digit = Integer.parseInt(str);
		int hex = (digit / 10) * 16 + digit % 10;
		return String.valueOf(hex);
	}
}
