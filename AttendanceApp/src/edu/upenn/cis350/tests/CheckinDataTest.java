package edu.upenn.cis350.tests;

import java.util.ArrayList;

import edu.upenn.cis350.localstore.CheckinDataSource;
import edu.upenn.cis350.localstore.StudentDataSource;
import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.Student;

public class CheckinDataTest extends AbstractDataTest {
	Checkin checkin;
	private CheckinDataSource checkinData;
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
        checkinData = new CheckinDataSource(context);
        checkinData.open();
	}
	
	public void testCreateCheckin()
	{
		Checkin checkin = checkinData.create(0, 1, 1);
		checkin = (Checkin) checkinData.get(checkin.getId());//get it from the DB again, to make sure DB is updated
		assertTrue(checkin.neverCheckedIn());
		assertTrue(checkin.defaultState());
		assertFalse(checkin.checkedIn());
		assertFalse(checkin.checkedOut());
		assertFalse(checkin.absent());
	}
	
	public void testMarkAbsent()
	{
		long time = System.currentTimeMillis();
		Checkin checkin = checkinData.create(0, 1, 1);
		checkin.markAbsent(time);
		checkinData.save(checkin);
		checkin = (Checkin) checkinData.get(checkin.getId());//get it from the DB again, to make sure DB is updated
		assertTrue(checkin.absent());
		assertTrue(checkin.neverCheckedIn());
		assertFalse(checkin.checkedIn());
		assertFalse(checkin.checkedOut());
		assertFalse(checkin.defaultState());
	}
	
	public void testCheckin()
	{
		long time = System.currentTimeMillis();
		Checkin checkin = checkinData.create(0, 1, 1);
		checkin.checkIn(time);
		checkinData.save(checkin);
		checkin = (Checkin) checkinData.get(checkin.getId());//get it from the DB again, to make sure DB is updated
		assertTrue(checkin.checkedIn());
		assertFalse(checkin.absent());
		assertFalse(checkin.neverCheckedIn());
		assertFalse(checkin.checkedOut());
		assertFalse(checkin.defaultState());
	}
	
	public void testCheckout() throws InterruptedException
	{
		Checkin checkin = checkinData.create(0, 1, 1);
		long time = System.currentTimeMillis();
		checkin.checkIn(time);
		Thread.sleep(10);
		time = System.currentTimeMillis();
		checkin.checkOut(time);
		checkinData.save(checkin);
		checkin = (Checkin) checkinData.get(checkin.getId());//get it from the DB again, to make sure DB is updated
		assertTrue(checkin.checkedOut());
		assertFalse(checkin.absent());
		assertFalse(checkin.neverCheckedIn());
		assertFalse(checkin.checkedIn());
		assertFalse(checkin.defaultState());
	}
	
	
	public void testSetComment() 
	{
		final String COMMENT = "Test comment";
		Checkin checkin = checkinData.create(0, 1, 1);
		checkin.setComment(COMMENT);
		checkinData.save(checkin);
		checkin = (Checkin) checkinData.get(checkin.getId());//get it from the DB again, to make sure DB is updated
		assertEquals(checkin.getComment(),COMMENT);
	}
	
	public void testGetForDay() throws InterruptedException
	{
		long time = System.currentTimeMillis();
		Checkin checkin = checkinData.create(time, 2, 2);
		checkin = (Checkin) checkinData.get(checkin.getId());//get it from the DB again, to make sure DB is updated
		Thread.sleep(50);
		long newTime = System.currentTimeMillis();
		checkin = checkinData.getForDay(newTime,2,2);
		assertEquals(checkin.getLastChangeTime(),time);
		
	}
	
	public void tearDown()
    {
    	checkinData.close();
    }
	
	public void testGetAllForDay()
	{
		long time = System.currentTimeMillis();
		Checkin checkin0 = checkinData.create(time, 3, 3);
		Checkin checkin1 = checkinData.create(time, 3,4);
		Checkin checkin2 = checkinData.create(12030123, 2,5);
		
		Checkin checkin0copy=null;
		Checkin checkin1copy=null;
		Checkin checkin2copy = null;
		ArrayList<Checkin> checkins = checkinData.getAllForDay(time);
		for ( Checkin checkin : checkins )
    	{
    		if ( checkin.getId()==checkin0.getId() )
    			checkin0copy = checkin;
    		if ( checkin.getId()==checkin1.getId() )
    			checkin1copy = checkin;
    		if ( checkin.getId()==checkin2.getId() )
    			checkin2copy = checkin;
    	}
		assertNotNull(checkin0copy);
		assertNotNull(checkin1copy);
		assertNull(checkin2copy);
		
	}

}
