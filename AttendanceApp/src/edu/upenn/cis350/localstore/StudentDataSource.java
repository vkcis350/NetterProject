package edu.upenn.cis350.localstore;

import java.util.ArrayList;
import java.util.List;

import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.Student;
import edu.upenn.cis350.models.Model;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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
		student.setID(c.getLong(0));
		student.setName(c.getString(1));
		return student;
	}

	@Override
	public void create(Model student) {
		Student s = (Student)student ;
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_STUDENT_NAME, s.getName() );
		long insertId = database.insert(MySQLiteHelper.TABLE_STUDENTS, null,
				values);
		s.setID(insertId);
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
				+" ORDER BY "+MySQLiteHelper.TABLE_STUDENTS+"."+MySQLiteHelper.COL_STUDENT_NAME ;

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
		
		return (ArrayList<Student>) getAll(MySQLiteHelper.COL_STUDENT_NAME);
	}
}
