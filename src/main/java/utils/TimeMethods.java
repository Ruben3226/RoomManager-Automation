package main.java.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

/**
 * This class manages Time elements
 * @author Eliana Navia
 *
 */
public class TimeMethods {
	static Calendar calendar = new GregorianCalendar();
	static DateTime dateTime = new DateTime();

	/**
	 * [YA]This method changes a date to String.
	 * @param date
	 * @param formatter
	 * @return String
	 */
	private static String dateToString(Date date, String formatter) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
		return simpleDateFormat.format(date);
	}

	/**
	 * [YA]This method gets a new time based on the current time and minutes to add or subtract
	 * @param minutes
	 * @return new time
	 */
	private static Date getNewTime(int minutes) {
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, minutes); 
		return calendar.getTime();
	}

	/**
	 * [YA]This method returns a new time as a String based on current time and minutes to add or 
	 * subtract
	 * @param value
	 * @return String
	 */
	public static String getTime(int value, String formatter) {
		return dateToString(getNewTime(value), formatter);
	}

	/**
	 * [YA] This method gets a new date based on current time and days to add or subtract
	 * @param days
	 * @return Date
	 */
	private static Date getNewDate(int days) {
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

	/**
	 * [YA] This method returns a new date as a String based on current time and days to add or 
	 * subtract
	 * @param value
	 * @param formatter
	 * @return String
	 */
	public static String getDate(int value, String formatter) {
		String date = dateToString(getNewDate(value), formatter);

		/*This lines should be removed when the calendar lets the user create Out Of Orders on 
		weekends*/
		if(value != 0) {
			String day = dateToString(getNewDate(value), "EEE");
			if(day.equals("Sat") || day.equals("Sun")) {
				date = dateToString(getNewDate(2), formatter);
			} 
		}
		return date;
	}

	/**
	 * [YA]This element returns a time element: hours, minutes or meridian
	 * @param time
	 * @param element
	 * @return String
	 */
	public static String getTimeElement(String time, String element) {
		String[] splittedTime = time.split(" ");
		String[] timeElement = splittedTime[0].split(":");
		String elementValue = null;
		switch(element) {	
		case "hours": elementValue = Integer.parseInt(timeElement[0]) + "";
		break;
		case "minutes": elementValue = timeElement[1];
		break;
		case "meridian": elementValue = splittedTime[1];
		break;

		//This case set the hour to 24 h format
		case "Hour": elementValue = (splittedTime[1].equals("pm")) ?  
				(Integer.parseInt(timeElement[0]) + 12) + "" : Integer.parseInt(timeElement[0]) + "";
		break;

		//This case set the value to hh:mm
		case "hourMin": elementValue = splittedTime[0];
		break;
		default: break;
		}
		return elementValue;
	}

	/**
	 * [YA]This method returns the following methods of a date: year, month or day
	 * @param date
	 * @param element
	 * @return String
	 */
	public static String getDateElement(String date, String element) {
		String dateElement = null;
		switch(element) {
		case "year": dateElement = date.split(" ")[2];
		break;
		case "month": dateElement = date.split(" ")[0];
		break;
		case "day" : dateElement = date.split(" ")[1];
		break;
		default : break;
		}
		return dateElement;
	}

	/**
	 * [YA] This method returns the current date or time in any format
	 * @param formatter
	 * @return String
	 */
	public static String getCurrentDate(String formatter) {
		return dateToString(new Date(),formatter);
	}

	/**
	 * [YA] This method returns the next half hour period for a time
	 * @param time
	 * @return String
	 */
	public static String getNextHalfHourPeriod(String time) {
		int hours = Integer.parseInt(getTimeElement(time, "hours"));
		int minutes = Integer.parseInt(getTimeElement(time, "minutes"));
		String strMinutes;
		if(minutes < 30) {
			strMinutes = "30";
		} else {
			strMinutes = "00";
			hours = hours + 1;
			if (hours >= 13) {
				hours = hours - 12;
			}
		}
		String newTime = hours + ":" + strMinutes;
		if(hours < 10) {
			newTime = "0" + newTime;
		}
		return newTime;
	}

	/**
	 * [YA] This method changes the date format 
	 * @param date
	 * @param oldFormat
	 * @param newFormat
	 * @return String
	 */
	public static String changeDateFormat(String date, String oldFormat, String newFormat) {
		String newDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
			Date oldDate = sdf.parse(date);
			sdf.applyPattern(newFormat);
			newDate = sdf.format(oldDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDate;
	}

	/**
	 * [EN] This method does the subtraction between timeEnd and timeIni.
	 * @param timeIni HH:mm
	 * @param timeEnd HH:mm
	 * @return String hh:mm timeEnd - timeIni 
	 */
	public static String differenceBetweenTimes(String timeIni, String timeEnd) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String newTime = "";
		Date init = new Date();
		Date end = new Date();
		try {
			init = format.parse(timeIni);
			end = format.parse(timeEnd);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		DateTime dtInit = new DateTime(init);
		DateTime dtEnd = new DateTime(end);
		int hoursDiff = Hours.hoursBetween(dtInit, dtEnd).getHours() % 24;
		int minutesDiff = Minutes.minutesBetween(dtInit, dtEnd).getMinutes() % 60;

		String complementHours = (hoursDiff < 10) ? "0" : "";
		String complementMinutes = (minutesDiff < 10) ? "0" : "";

		newTime = complementHours + hoursDiff + ":" + complementMinutes + minutesDiff;
		return newTime;
	}
}