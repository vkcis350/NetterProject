package edu.upenn.cis350.localstore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.Student;
import edu.upenn.cis350.models.Model;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

public class StudentDataSource extends DataSource {
	final static String[] TABLES = { MySQLiteHelper.TABLE_STUDENTS, MySQLiteHelper.TABLE_ENROLLMENT};
	final static String ID_COLUMN = MySQLiteHelper.COL_STUDENT_ID;
	
	public StudentDataSource(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String[] getTables() {
		return TABLES;
	}

	@Override
	protected String getIDColumn() {
		return ID_COLUMN;
	}

	@Override
	protected Student cursorToModel(Cursor c) {
		Student student = new Student();
		student.setID(c.getLong(MySQLiteHelper.STUDENT_STUDENT_ID_INDEX));
		student.setLastName(c.getString(MySQLiteHelper.STUDENT_STUDENT_LAST_NAME_INDEX));
		student.setFirstName(c.getString(MySQLiteHelper.STUDENT_STUDENT_FIRST_NAME_INDEX));
		student.setPhone(c.getString(MySQLiteHelper.STUDENT_STUDENT_PHONE_INDEX));
		student.setContact(c.getString(MySQLiteHelper.STUDENT_STUDENT_CONTACT_INDEX));
		student.setContactRelation(c.getString(MySQLiteHelper.STUDENT_STUDENT_CONTACT_RELATION_INDEX));
		student.setGrade(c.getInt(MySQLiteHelper.STUDENT_STUDENT_GRADE_INDEX));
		student.setAddress(c.getString(MySQLiteHelper.STUDENT_STUDENT_ADDRESS_INDEX));
		student.setSchoolID(c.getLong(MySQLiteHelper.STUDENT_SCHOOL_ID_INDEX));
		student.setSchoolYear(c.getLong(MySQLiteHelper.STUDENT_SCHOOLYEAR_INDEX));
		student.setSiteID(c.getLong(MySQLiteHelper.STUDENT_SITE_ID_INDEX));
		
		return student;
	}

	@Override
	@Deprecated
	public void create(Model student) {
		Student s = (Student)student ;
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_STUDENT_LAST_NAME, s.getLastName() );
		long insertId = database.insert(MySQLiteHelper.TABLE_STUDENTS, null,
				values);
		s.setID(insertId);
	}
	
	public Student create(long id, String lastName, String firstName, String phone, 
			String contact, String contactRelation, long schoolID, long siteID,
			int schoolYear, int grade, String address){
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_STUDENT_ID, id);
		values.put(MySQLiteHelper.COL_STUDENT_LAST_NAME, lastName );
		values.put(MySQLiteHelper.COL_STUDENT_FIRST_NAME, firstName);
		values.put(MySQLiteHelper.COL_STUDENT_PHONE, phone);
		values.put(MySQLiteHelper.COL_STUDENT_CONTACT, contact);
		values.put(MySQLiteHelper.COL_STUDENT_CONTACT_RELATION, contactRelation);
		values.put(MySQLiteHelper.COL_SCHOOL_ID, schoolID);
		values.put(MySQLiteHelper.COL_SITE_ID, siteID);
		values.put(MySQLiteHelper.COL_STUDENT_SCHOOLYEAR, schoolYear);
		values.put(MySQLiteHelper.COL_STUDENT_GRADE, grade);
		values.put(MySQLiteHelper.COL_STUDENT_ADDRESS, address);
		
		long insertId = database.insert(MySQLiteHelper.TABLE_STUDENTS, null,
				values);

		Cursor c=database.query(getTables()[PRIMARY_TABLE_INDEX], 
				null, 
				getIDColumn()+"=?",
				new String[]{id+""}, null, null, null);
		c.moveToFirst();
		
		Student s = cursorToModel(c);
		
		c.close();
		return s;
		
	}
	
	/**
	 * Get all students for a particular activity, sorted alphabetically.
	 * @param act
	 * @return list of students in the activity
	 */
	public List<Student> getStudentsByActivity(SchoolActivity act)
	{
		List<Student> students = new ArrayList<Student>();
		final String MY_QUERY = "SELECT * FROM " +MySQLiteHelper.TABLE_STUDENTS 
				+ " INNER JOIN " 
				+ MySQLiteHelper.TABLE_ENROLLMENT
				+" enroll ON "+MySQLiteHelper.TABLE_STUDENTS+"."+MySQLiteHelper.COL_STUDENT_ID
				+"=enroll."+MySQLiteHelper.COL_STUDENT_ID
				+" WHERE enroll."+MySQLiteHelper.COL_ACTIVITY_ID+"=?"
				+" ORDER BY "+MySQLiteHelper.TABLE_STUDENTS+"."+MySQLiteHelper.COL_STUDENT_LAST_NAME ;

		Cursor cursor = database.rawQuery(MY_QUERY, new String[]{String.valueOf(act.getID())});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Student student = cursorToModel(cursor);
			students.add(student);
			cursor.moveToNext();
		}
		cursor.close();
		return students;
	}
	
	/**Adds a student to an activity.
	 * 
	 * @param student
	 * @param act
	 */
	public void addStudentToActivity(Student student, SchoolActivity act) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_STUDENT_ID, student.getID());
		values.put(MySQLiteHelper.COL_ACTIVITY_ID, act.getID());
		database.insert(MySQLiteHelper.TABLE_ENROLLMENT, null,
				values);
	}
	
	public ArrayList<Student> getAll()
	{
		return (ArrayList<Student>) getAll(MySQLiteHelper.COL_STUDENT_LAST_NAME);
	}
	
	public ArrayList<Student> getAllByGrade()
	{
		return (ArrayList<Student>) getAll(MySQLiteHelper.COL_STUDENT_GRADE
				+","+MySQLiteHelper.COL_STUDENT_LAST_NAME);
	}
	
	public void toCSV(String filename) throws IOException
	{
		ArrayList<Student> students = getAll();
		Writer output = null;
		File file = new File("/data/data/edu.upenn.cis350/students.csv");
		output = new BufferedWriter(new FileWriter(file));
		output.write("student id, last name, first name, phone, contact, contact relationship,school id, site id, schoolyear, grade, address\n");
		for (Student student:students)
		{
			output.write( student.getID()
					+","+student.getLastName()
					+","+student.getFirstName()
					+","+student.getPhone()
					+","+ student.getContact()
					+","+ student.getContactRelation()
					+","+student.getSchoolID()
					+","+student.getSiteID()
					+","+student.getSchoolYear()
					+","+ student.getGrade()
					+","+ student.getAddress()+"\n");
		}
		output.close();	
	}
}
