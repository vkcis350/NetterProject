package edu.upenn.cis350.tests;

import edu.upenn.cis350.models.Student;
import junit.framework.TestCase;

public class StudentTest extends TestCase {
	private Student test,test2;
	private final long id1=25, id2=36;
	private final String n1="first",n2="second";
	
	
	protected void setUp() throws Exception {
		super.setUp();
		test=new Student();
		test.setFirstName(n1);
		test2 = new Student();
		test2.setFirstName(n2);
	}

	public void testGetSetID() {
		assertEquals(test.getId(),0);
		test.setId(id1);
		assertEquals(test.getId(),id1);
		test.setId(id2);
		assertEquals(test.getId(),id2);
		
		assertEquals(test2.getId(),0);
		test2.setId(id2);
		assertEquals(test2.getId(),id2);
		test2.setId(id1);
		assertEquals(test2.getId(),id1);
	}

	public void testGetSetName() {
		assertEquals(test.getFirstName(),n1);
		test.setFirstName(n2);
		assertEquals(test.getFirstName(),n2);
		
	}

	public void testToString() {
		test.setFirstName("tester");
		test.setLastName("aaa");
		test.setGrade(32);
		assertEquals(test.toString(),"32  aaa, tester");
	}

	

}

