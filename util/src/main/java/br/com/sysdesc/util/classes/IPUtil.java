package br.com.sysdesc.util.classes;

import java.util.regex.Pattern;

public class IPUtil {

	private final static String REGEX = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

	public static boolean isIpValid(String ipPDV) {

		Pattern pattern = Pattern.compile(REGEX);

		return pattern.matcher(ipPDV).matches();
	}

	public static boolean isNetworkMatch(String ipPDV) {

		return true;
	}

	public static String getIp() {

		return "";
	}

	public static String getSubnet() {

		return "";
	}

}
