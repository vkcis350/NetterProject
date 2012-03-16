package edu.upenn.cis350.localstore;

import java.util.Calendar;

import android.content.Context;
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
			SchoolActivity a2 = new SchoolActivity("Homework");
			SchoolActivity a3 = new SchoolActivity("Napoleonic Wars");
			SchoolActivity a4 = new SchoolActivity("Xinhai Revolution");
		
			Student s1 = new Student("Jordan");
			Student s2 = new Student("Jose");
			Student s3 = new Student("Vayu");
			Student s4 = new Student("Xiao");
			Student s5 = new Student("Sun Yat-sen");
			Student s6 = new Student("Admiral Nelson");
			

			studentData.create( s1 );
			studentData.create( s2 );
			studentData.create( s3 );
			studentData.create( s4 );
			studentData.create( s5 );
			studentData.create( s6 );
			
			actData.create(a1);
			actData.create(a2);
			actData.create(a3);
			actData.create(a4);
			
			studentData.addStudentToActivity(s1,a1);
			studentData.addStudentToActivity(s2,a1);
			studentData.addStudentToActivity(s3,a1);
			studentData.addStudentToActivity(s4,a1);
			studentData.addStudentToActivity(s5,a1);
			
			studentData.addStudentToActivity(s5,a2);
			studentData.addStudentToActivity(s6,a2);
			
			Checkin c1 = (Checkin)checkinData.create(SESSION_ID, a1.getID(), s1.getID(), "was here");
			Checkin c2 = (Checkin)checkinData.create(SESSION_ID, a1.getID(), s2.getID(), "was here");
			Checkin c3 = (Checkin)checkinData.create(SESSION_ID, a1.getID(), s3.getID(), "was here");
			Checkin c4 = (Checkin)checkinData.create(SESSION_ID, a1.getID(), s4.getID(), "was here");
			Checkin c5 = (Checkin)checkinData.create(SESSION_ID, a1.getID(), s5.getID(), "disruptive");
			
			Calendar cal = Calendar.getInstance();
			
			c5.setInTime(cal.getTimeInMillis());
			checkinData.save(c5);
			
		}
		
		studentData.close();
		actData.close();
		checkinData.close();
	}

}
