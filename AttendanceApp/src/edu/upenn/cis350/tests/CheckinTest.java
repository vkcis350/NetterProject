package edu.upenn.cis350.tests;

import edu.upenn.cis350.models.Checkin;
import junit.framework.TestCase;

public class CheckinTest extends TestCase {
	private Checkin test;
	private final long s1=25, s2=36;
	private final long sid1=1, sid2=2;
	private final long aid1=3,aid2=4;
	private final String com1="first comment", com2="second comment";
	//private final boolean pres=true,notPres=false;
	private final long in1=1234, in2=5678;
	private final long out1=8765,out2=4321;
	
	public void setUp(){
		test=new Checkin();
		test.setStudentID(sid1);
		test.setActivityID(aid1);
		test.setInTime(in1);
		test.setOutTime(out1);
	}
	
	public void testCheckin(){
		//assertTrue(test.getComment().equals("None"));
	}
	
	public void testGetSetStudentID() {
		assertEquals(test.getStudentID(),sid1);
		test.setStudentID(sid2);
		assertEquals(test.getStudentID(),sid2);
	}


	public void testGetSetActivityID() {
		assertEquals(test.getActivityID(),aid1);
		test.setActivityID(aid2);
		assertEquals(test.getActivityID(),aid2);
	}

	/**
	public void testGetSetComment() {
		test.setComment(com1);
		assertEquals(test.getComment(),com1);
		test.setComment(com2);
		assertEquals(test.getComment(),com2);
	}
	**/
	
	public void testGetSetInTime() {
		assertEquals(test.getInTime(),in1);
		test.setInTime(in2);
		assertEquals(test.getInTime(),in2);
	}

	public void testGetSetOutTime() {
		assertEquals(test.getOutTime(),out1);
		test.setOutTime(out2);
		assertEquals(test.getOutTime(),out2);
	}
	

}
