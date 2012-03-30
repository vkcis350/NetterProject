package edu.upenn.cis350.localstore;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.util.Log;
import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.Student;

public class TemporaryDbInsert {
	
	/**Populate activities and students if database is empty
	 * Used for now since we're not transferring the database
	 * Database is reset when DATABASE_VERSION in MySQLiteHelper is changed*/
	
	static final int SESSION_ID = 0;
	
	
	public static void insert(Context context)
	{

		
		SchoolActivityDataSource actData = new SchoolActivityDataSource(context);
		StudentDataSource studentData = new StudentDataSource(context);
		CheckinDataSource checkinData = new CheckinDataSource(context);
		
		actData.open();
		studentData.open();
		checkinData.open();
		
		if ( actData.getAll().size()<1 )
		{
			
			SchoolActivity a1 = new SchoolActivity("Android Programming");
			SchoolActivity a2 = new SchoolActivity("Zambian Cultural Festival");
			SchoolActivity a3 = new SchoolActivity("Napoleonic Wars");
			SchoolActivity a4 = new SchoolActivity("Homework");
		
			Student s1 = studentData.create(1, "Sun", "Yat-sen", "1-800-PETMEDS", 
					"Chiang Kai-shek", "eventual successor", 0, 0,
					1911, 12, "3600 Walnut Street , Nanjing");
			Student s2 = studentData.create(2, "Bonaparte", "Napoleon", "411", 
					"Duke of Wellington", "Arch Nemesis", 0, 0,
					1800, 6, "Versailles?");
			Student s3 = studentData.create(3177956, "Simms", "Julianna", "215-123-1234", 
					" ", "Aunt", 0, 0,
					1810, 5, "123 Some Street, Philadelphia");
			Student s4 = studentData.create(6165380, "Boyles", "Keisha", "215-123-1234", 
					"Mara Boyles", "Mother", 0, 0,
					2011, 5, "123 Some Street, Philadelphia");
			
			Student s5 = studentData.create(9136389, "Pomeroy", "Shanita", "215-123-1234", 
					"?", "Neighbor", 0, 0,
					2011, 3, "123 Some Street, Philadelphia");
			
			actData.create(a1);
			actData.create(a2);
			actData.create(a3);
			actData.create(a4);
			
			studentData.addStudentToActivity(s1,a1);
			studentData.addStudentToActivity(s2,a1);
			studentData.addStudentToActivity(s3,a1);
		
			ArrayList<Student> students = studentData.getAll();
			
			for (Student s : students)
			{
				Log.d("Student", s.toString() +" Contact: "+s.getContact());
			}
			
			Calendar cal = Calendar.getInstance();
			
			
		}
		
		studentData.close();
		actData.close();
		checkinData.close();
	}

}
