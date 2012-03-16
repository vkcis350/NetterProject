package edu.upenn.cis350.models;

import junit.framework.TestCase;

public class StudentTest extends TestCase {
	private Student test,test2;
	private final long id1=25, id2=36;
	private final String n1="first",n2="second";
	
	protected void setUp() throws Exception {
		super.setUp();
		test=new Student();
		test2=new Student(n2);
		test.setName(n1);
	}

	public void testGetSetID() {
		assertEquals(test.getID(),-1);
		test.setID(id1);
		assertEquals(test.getID(),id1);
		test.setID(id2);
		assertEquals(test.getID(),id2);
		
		assertEquals(test2.getID(),-1);
		test.setID(id2);
		assertEquals(test2.getID(),id2);
		test.setID(id1);
		assertEquals(test2.getID(),id1);
	}

	public void testGetSetName() {
		assertEquals(test.getName(),n1);
		test.setName(n2);
		assertEquals(test.getName(),n2);
		
		assertEquals(test2.getName(),n2);
	}

	public void testToString() {
		assertEquals(test.toString(),n1);
		assertEquals(test.toString(),test.getName());
	}

	public void testCompareTo() { //should match up on id primarily, right?
		test.setID(id1);
		test2.setID(id2);
		test2.setName(n1);
		assertFalse(test2.equals(test));
		test2.setID(id1);
		assertTrue(test2.equals(test));
		test2.setName(n2);
		assertFalse(test2.equals(test));
	}
	

}
