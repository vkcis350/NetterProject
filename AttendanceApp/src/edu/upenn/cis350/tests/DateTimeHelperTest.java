package edu.upenn.cis350.tests;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.upenn.cis350.util.DateTimeHelper;
import android.test.AndroidTestCase;

public class DateTimeHelperTest extends AndroidTestCase {
	public void setUp() throws Exception
	{
		super.setUp();
	}
	public void testTodayStart()
	{
		long time = DateTimeHelper.todayStart();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(time);
		assertEquals(0,calendar.get(Calendar.HOUR_OF_DAY));
	}
	
	public void testPlusOneDay()
	{
		long time = 1000;
		long newTime = DateTimeHelper.plusOneDay(time);
		assertEquals(newTime,86401000);
		
	}

}
