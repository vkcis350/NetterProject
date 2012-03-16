package edu.upenn.cis350.models;

import junit.framework.TestCase;

public class SchoolActivityTest extends TestCase {
	private SchoolActivity test,test2;
	private final long id1=25, id2=36;
	private final String n1="first",n2="second";
	
	protected void setUp() throws Exception {
		super.setUp();
		test=new SchoolActivity();
		test2=new SchoolActivity();
		test.setID(id1);
		test.setName(n1);
	}

	public void testSetID() {
		assertEquals(test.getID(),id1);
		test.setID(id2);
		assertEquals(test.getID(),id2);
	}

	public void testSetName() {
		assertEquals(test.getName(),n1);
		test.setName(n2);
		assertEquals(test.getName(),n2);
	}

	public void testToString() {
		assertEquals(test.toString(),n1);
		assertEquals(test.toString(),test.getName());
	}

	public void testCompareTo() {

		test2.setID(id2);
		test2.setName(n1);
		assertFalse(test2.equals(test));
		test2.setID(id1);
		assertTrue(test2.equals(test));
		test2.setName(n2);
		assertFalse(test2.equals(test));
	}

}
