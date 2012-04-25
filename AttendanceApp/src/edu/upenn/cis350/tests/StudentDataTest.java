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

public class StudentDataTest extends AbstractDataTest {
	private ArrayList<SchoolActivity> activities;
	private SchoolActivityDataSource actData;
	private StudentDataSource studentData;

	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		final SQLiteDatabase db = SQLiteDatabase.create(null);
        studentData = new StudentDataSource(context);
        studentData.open();
	}
	
    public void testCreateStudent() throws Throwable {
      
       
       Student s1 = studentData.create(0, "Turing", "Alan", "123-456-7890", "von Neumann", 
    		   "another famous computer scientist", 0, 0, 1945, 12, "1 Computer St.");
       
       assertEquals(s1.getLastName(), "Turing");
       assertEquals(s1.getSchoolYear(),1945);
    }
    
    public void testGetStudent() 
    {
    	studentData.create(0, "Turing", "Alan", "123-456-7890", "von Neumann", 
     		   "another famous computer scientist", 0, 0, 1945, 12, "1 Computer St.");
    	
    	Student student = (Student) studentData.get(0);//retrieve from DB
        assertEquals(student.getFirstName(), "Alan");
        assertEquals(student.getPhone(), "123-456-7890");
    }
    
    public void tearDown()
    {
    	studentData.close();
    }
}
