package edu.upenn.cis350.localstore;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
		
		String[] COMMENT_ARRAY = {"Behaved today.", 
				"Fought well at Waterloo", 
				"Lost Wuchang because of him today.", 
				"Couldn't find Zambia on a map. Unbelievable."};

		
		SchoolActivityDataSource actData = new SchoolActivityDataSource(context);
		StudentDataSource studentData = new StudentDataSource(context);
		CheckinDataSource checkinData = new CheckinDataSource(context);
		UserDataSource userData = new UserDataSource(context);
		FrequentActivityDataSource frequentData = new FrequentActivityDataSource(context);
		
		actData.open();
		studentData.open();
		checkinData.open();
		userData.open();
		frequentData.open();
		
		if ( actData.getAll().size()<1 )
		{
			try {
				userData.create("user","1");
				userData.create("admin","");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			SchoolActivity a1 = actData.create("Android Programming",0);
			SchoolActivity a2 = actData.create("Zambian Cultural Festival",0);
			SchoolActivity a3 =  actData.create("Napoleonic Wars",0);
			SchoolActivity a4 =  actData.create("Homework",0);
			SchoolActivity a5 =  actData.create(192,"Whatever",1);
			
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
			
			Student s6 = studentData.create(99, "Darwin", "Charles", "1-888-EVOLVE", 
					"Australopithecus", "Ancestor", 0, 0,
					2011, 4, "Tortoise's back, Galapagos Islands");
			
			Student s7 = studentData.create(100, "Turing", "Alan", "215-MACHINE", 
					"Someone", "Fellow human...or AI?", 0, 0,
					2011, 6, "Bletchley Park");
			
			frequentData.create(1,1);
			frequentData.create(2,1);
			frequentData.create(2,2);
			
			studentData.addStudentToActivity(s1,a1);
			studentData.addStudentToActivity(s2,a1);
			studentData.addStudentToActivity(s3,a1);
		
			ArrayList<Student> students = studentData.getAll();
			
			for (Student s : students)
			{
				Log.d("Student", s.toString() +" Contact: "+s.getContact());
			}
			
			Calendar cal = Calendar.getInstance();
			
			Checkin c0 = (Checkin) checkinData.create(0, 1, s1.getId());
			c0.setComment("Lost Wuchang because of him today.");
			long time = System.currentTimeMillis();
			long someTime = (long) (662688000*1e3);
			c0.setInTime(someTime);
			c0.setLastChangeTime(someTime);
			checkinData.save(c0);
			
			Checkin c1 = (Checkin) checkinData.create(0, 1, s2.getId());
			c1.setComment("Fought well at Waterloo.");
			checkinData.save(c1);		
			
		}
		
		studentData.close();
		actData.close();
		checkinData.close();
		userData.close();
		frequentData.close();
	}

}
