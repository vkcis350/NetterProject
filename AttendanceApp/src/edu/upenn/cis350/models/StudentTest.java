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
		test.setFirstName(n1);
	}

	public void testGetSetID() {
		assertEquals(test.getId(),-1);
		test.setId(id1);
		assertEquals(test.getId(),id1);
		test.setId(id2);
		assertEquals(test.getId(),id2);
		
		assertEquals(test2.getId(),-1);
		test.setId(id2);
		assertEquals(test2.getId(),id2);
		test.setId(id1);
		assertEquals(test2.getId(),id1);
	}

	public void testGetSetName() {
		assertEquals(test.getFirstName(),n1);
		test.setFirstName(n2);
		assertEquals(test.getFirstName(),n2);
		
		assertEquals(test2.getFirstName(),n2);
	}

	public void testToString() {
		assertEquals(test.toString(),n1);
		assertEquals(test.toString(),test.getFirstName());
	}

	public void testCompareTo() { //should match up on id primarily, right?
		test.setId(id1);
		test2.setId(id2);
		test2.setFirstName(n1);
		assertFalse(test2.equals(test));
		test2.setId(id1);
		assertTrue(test2.equals(test));
		test2.setFirstName(n2);
		assertFalse(test2.equals(test));
	}
	

}

