package nl.commerquell.adressen.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;

public final class Utils {
	private Utils() {}
	
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return "";
		}
		
		String theFormat = format;
		if (theFormat == null || theFormat.equals("")) {
			theFormat = "DD-MM-YYYY";
		}
		boolean fauxFormat = checkDateFormat(theFormat);
		if (fauxFormat) {
			throw new RuntimeException("Format \"" + theFormat + " is not fit for a date");
		}
		StringBuffer buf = new StringBuffer(theFormat);
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		doReplace(buf, "D", day);
		doReplace(buf, "M", month);
		doReplace(buf, "YY", year);

		return buf.toString();
	}
	
	public static Date formatDate(String date, String format) {
		if (date == null || date.equals("")) {
			return null;
		}
		
		String theFormat = format;
		if (theFormat == null || theFormat.equals("")) {
			theFormat = "DD-MM-YYYY";
		}
		boolean fauxFormat = checkDateFormat(theFormat);
		if (fauxFormat) {
			throw new RuntimeException("Format \"" + theFormat + " is not fit for a date");
		}
		int day = getIntFromString(date, format, 'D') + 1;
		int month = getIntFromString(date, format, 'M') - 1;
		int year  = getIntFromString(date, format, 'Y');
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
		cal.clear();
		cal.set(year, month, day);

		return cal.getTime();
	}
	
	private static int getIntFromString(String s, String format, char placeholder) {
		int retval = -1;
		int idxo = format.indexOf(placeholder);
		if (idxo < 0) {
			return retval;
		}
		int idxe = idxo + 1;
		char c = format.charAt(idxe);
		while (c == placeholder) {
			idxe++;
			if (idxe >= format.length()) {
				break;
			}
			c = format.charAt(idxe);
		}
		char sep = '\0';
		if (idxe < format.length()) {
			sep = format.charAt(idxe);
		}
		int cnt = 0;
		if (sep != '\0') {
			int idx = format.indexOf(sep);
			cnt = (idx == idxe ? 1 : 2);
		}
		int i = 0;
		switch (cnt) {
		case 1:
			i = s.indexOf(sep);
			break;
		case 2:
			i = s.lastIndexOf(sep);
			break;
		default:
			i = s.length();
			break;
		}
		StringBuffer buf = new StringBuffer();
		i--;
		boolean numeric = (i >= 0 && s.charAt(i) >= '0' && s.charAt(i) <= '9');
		while (numeric) {
			buf.insert(0, s.charAt(i));
			i--;
			numeric = (i >= 0 && s.charAt(i) >= '0' && s.charAt(i) <= '9');
		}
		if (buf.length() > 0) {
			retval = Integer.valueOf(buf.toString()).intValue();
		}
		return retval;
	}
	
	private static void doReplace(StringBuffer buf, String placeholder, int value) {
		if (buf.indexOf(placeholder) >= 0) {
			int idx = buf.indexOf(placeholder);
			int len = placeholder.length();
			String s = Integer.valueOf(value).toString();
			String doublePlaceholder = placeholder + placeholder;
			if (buf.indexOf(doublePlaceholder) >= 0) {
				len = doublePlaceholder.length();
			}
			int i = s.length();
			while (i < len) {
				s = "0" + s;
				i++;
			}
			buf.replace(idx, idx + len, s);
		}
	}
	
	private static boolean checkDateFormat(String theFormat) {
		int idxDs = theFormat.indexOf("D");
		int idxDe = theFormat.lastIndexOf("D");
		int idxMs = theFormat.indexOf("M");
		int idxMe = theFormat.lastIndexOf("M");
		int idxYs = theFormat.indexOf("YY");
		int idxYe = theFormat.lastIndexOf("YY");
		int idxDd = idxDe - idxDs;
		int idxMd = idxMe - idxMs;
		int idxYd = idxYe - idxYs;
		
		boolean fauxFormat = false;
		if (idxDs < 0 || idxMs < 0 || idxYs < 0) {
			fauxFormat = true;
		}
		if (idxDs >= 0 && idxDd > 1) {
			fauxFormat = true;
		}
		if (idxMs >= 0 && idxMd > 1) {
			fauxFormat = true;
		}
		if (idxYs > 0 && idxYd > 0 && idxYd != 2) {
			fauxFormat = true;
		}

		return fauxFormat;
	}
	
	public static Object nvl(Object value, Object valueIfNull) {
		if (valueIfNull == null) {
			throw new IllegalArgumentException("Een NVL-functie mag geen NULL voor een NULL substitueren");
		}
		Object retval = value;
		if (retval == null) {
			retval = valueIfNull;
		}
		return retval;
	}
	
	public static boolean isBlankOrEmpty(String s) {
		boolean retval = false;
		if (s == null) {
			retval = true;
		} else if (s.trim().length() < 1) {
			retval = true;
		}
		return retval;
	}
	
	public static User getUserFromSession(HttpSession session) {
		SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		User theUser = (User) context.getAuthentication().getPrincipal();
		return theUser;
	}
	
	public static String like(String s) {
		if (s == null || s.length() < 1) {
			return "%";
		}
		String retval = s;
		if (s.charAt(s.length() - 1) != '%') {
			retval = s + "%";
		}
		return retval;
	}
}
