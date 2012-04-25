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
    	studentData.create(1, "Darwin", "Charles", "215-MONKEYS", "Thomas Huxley", 
     		   "bull-dog", 0, 0, 1831, 12, "Some address");
    	
    	Student student = (Student) studentData.get(1);//retrieve from DB
        assertEquals(student.getFirstName(), "Charles");
        assertEquals(student.getPhone(), "215-MONKEYS");
    }
    
    public void testGetAllStudents()
    {
    	studentData.create(2,"Smith","John","123-456","John Doe",
    			"friend",0,1,2012,5,"Fake st.");
    	studentData.create(3,"Doe","John","223-456","John Smith",
    			"friend",0,1,2012,4,"Other st.");
    	ArrayList<Student> students = studentData.getAll();
    	
    	Student doe = null;
    	Student smith = null;
    	for ( Student student : students )
    	{
    		if ( student.getLastName().equals("Doe") )
    			doe = student;
    		if ( student.getLastName().equals("Smith") )
    			smith = student;
    	}
    	assertNotNull(doe);
    	assertNotNull(smith);
    	assertTrue(students.indexOf(smith)>students.indexOf(doe));
    }
    
    public void tearDown()
    {
    	studentData.close();
    }
}
