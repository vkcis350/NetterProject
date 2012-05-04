package edu.upenn.cis350.tests;

import android.test.AndroidTestCase;
import edu.upenn.cis350.models.FrequentActivity;

public class FrequentActivityTest extends AndroidTestCase {
	public void testSetUserId()
	{
		long USER_ID = 123;
		FrequentActivity freqAct = new FrequentActivity();
		freqAct.setUserId(USER_ID);
		assertEquals(freqAct.getUserId(),USER_ID);
	}
	
	public void testSetActivityId()
	{
		long ACTIVITY_ID = 456;
		FrequentActivity freqAct = new FrequentActivity();
		freqAct.setActivityId(ACTIVITY_ID);
		assertEquals(freqAct.getActivityId(),ACTIVITY_ID);
	}

}
