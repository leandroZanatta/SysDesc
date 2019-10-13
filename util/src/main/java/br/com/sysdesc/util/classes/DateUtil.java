package br.com.sysdesc.util.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String FORMATO_DD_MM_YYY = "dd/MM/yyy";
	public static final String FORMATO_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";

	public static Date parse(String formato, String valor) {

		try {
			return new SimpleDateFormat(formato).parse(valor);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String format(String formato, Date valor) {

		return new SimpleDateFormat(formato).format(valor);
	}
}
