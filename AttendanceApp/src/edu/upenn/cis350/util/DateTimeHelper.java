package edu.upenn.cis350.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeHelper {
	/**
	 * 
	 * @return the Unix time in milliseconds of today's date
	 */
	public static int ONE_DAY_MILLIS=86400000;
	public static long todayStart()
	{
		GregorianCalendar now = new GregorianCalendar();
		GregorianCalendar todayStart = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
		return todayStart.getTimeInMillis();
	}
	
	/**
	 * 
	 * @param time - time in millis
	 * @return time in millis plus one day
	 */
	public static long plusOneDay(long time)
	{
		return time+=ONE_DAY_MILLIS;
	}
	
	
	/**TO DO, POSSIBLY
	public static long dayStart(Date?)
	{
	}
	**/
	
	

}
