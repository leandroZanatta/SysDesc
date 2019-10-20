package br.com.sysdesc.util.classes;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import br.com.sysdesc.util.vo.IPVO;

public class IPUtil {

	private final static String REGEX = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

	public static boolean isIpValid(String ipPDV) {

		Pattern pattern = Pattern.compile(REGEX);

		return pattern.matcher(ipPDV).matches();
	}

	public static Boolean isNetworkMatch(IPVO ipLocal, String iptestar) {

		String[] mascara = ipLocal.getMascara().split("\\.");
		String[] ip1 = ipLocal.getIp().split("\\.");
		String[] ip2 = iptestar.split("\\.");

		if (mascara[0].equals("255") && !ip1[0].equals(ip2[0])) {

			return false;
		}
		if (mascara[1].equals("255") && !ip1[1].equals(ip2[1])) {

			return false;
		}
		if (mascara[2].equals("255") && !ip1[2].equals(ip2[2])) {

			return false;
		}
		if (mascara[3].equals("255") && !ip1[3].equals(ip2[3])) {

			return false;
		}

		return true;
	}

	public static List<IPVO> getIps() {

		List<IPVO> listaIPs = new ArrayList<>();

		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {

				NetworkInterface intf = en.nextElement();

				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress address = enumIpAddr.nextElement();

					if (!address.isLoopbackAddress() && address.isSiteLocalAddress()) {

						String endereco = address.toString();

						listaIPs.add(new IPVO(endereco.substring(1, endereco.length()), getSubnet(address), null));
					}

				}
			}
		} catch (SocketException e) {

		}

		return listaIPs;
	}

	private static String getSubnet(InetAddress host) throws SocketException {

		NetworkInterface networkInterface = NetworkInterface.getByInetAddress(host);

		int shft = 0xffffffff << (32 - networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength());

		int oct1 = ((byte) ((shft & 0xff000000) >> 24)) & 0xff;
		int oct2 = ((byte) ((shft & 0x00ff0000) >> 16)) & 0xff;
		int oct3 = ((byte) ((shft & 0x0000ff00) >> 8)) & 0xff;
		int oct4 = ((byte) (shft & 0x000000ff)) & 0xff;

		return oct1 + "." + oct2 + "." + oct3 + "." + oct4;
	}

	public static void main(String[] args) {
		getIps();
	}

	public static boolean isOnlyNetworkMatch(String ipPDV) {

		for (IPVO ipVO : getIps()) {

			if (isNetworkMatch(ipVO, ipPDV)) {
				return true;
			}
		}

		return false;
	}

}
