package edu.upenn.cis350.tests;

import edu.upenn.cis350.models.SchoolActivity;
import junit.framework.TestCase;

public class SchoolActivityTest extends TestCase {
	private SchoolActivity test,test2;
	private final long id1=25, id2=36;
	private final String n1="first",n2="second";
	
	protected void setUp() throws Exception {
		super.setUp();
		test=new SchoolActivity();
		test2=new SchoolActivity();
		test.setId(id1);
		test.setName(n1);
	}

	public void testSetID() {
		assertEquals(test.getId(),id1);
		test.setId(id2);
		assertEquals(test.getId(),id2);
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

}
