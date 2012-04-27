package edu.upenn.cis350.tests;

import java.util.ArrayList;

import junit.framework.Assert;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import edu.upenn.cis350.localstore.SchoolActivityDataSource;
import edu.upenn.cis350.localstore.StudentDataSource;
import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.Student;

public class SchoolActivityDataTest extends AbstractDataTest {
	private ArrayList<SchoolActivity> activities;
	private SchoolActivityDataSource actData;
	private StudentDataSource studentData;

	@Override
	public void setUp() throws Exception
	{
		super.setUp();

        actData = new SchoolActivityDataSource(context);
        actData.open();        
	}

    public void testGetSchoolActivity()
	{
		SchoolActivity activity = actData.create("Software Testing",1);
		actData.get(activity.getId());
		assertEquals(activity.getName(),"Software Testing");
	}

    
    public void tearDown()
    {
    	actData.close();
    }
}
