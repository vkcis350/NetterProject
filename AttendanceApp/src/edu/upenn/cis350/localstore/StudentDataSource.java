package edu.upenn.cis350.localstore;


import java.util.ArrayList;
import java.util.List;

import edu.upenn.cis350.models.SchoolActivity;
import edu.upenn.cis350.models.Student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StudentDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COL_STUDENT_ID,
			MySQLiteHelper.COL_STUDENT_NAME };

	public StudentDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Student createStudent(String name) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_STUDENT_NAME, name);
		long insertId = database.insert(MySQLiteHelper.TABLE_STUDENTS, null,
				values);
		// To show how to query
		Cursor cursor = database.query(MySQLiteHelper.TABLE_STUDENTS,
				allColumns, MySQLiteHelper.COL_STUDENT_ID+ " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		return cursorToStudent(cursor);
	}
	
	/*Note that Activities table is not implemented yet! Students just
	 * get associated with an activity id.
	 */
	public void addStudentToActivity(Student student, SchoolActivity act) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_STUDENT_ID, student.getID());
		values.put(MySQLiteHelper.COL_ACTIVITY_ID, act.getID());
		database.insert(MySQLiteHelper.TABLE_ENROLLMENT, null,
				values);
	}

	public void deleteStudent(Student student) {
		long id = student.getID();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_STUDENTS, MySQLiteHelper.COL_STUDENT_ID
				+ " = " + id, null);
	}

	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_STUDENTS,
				allColumns, null, null, null, null, 
				MySQLiteHelper.COL_STUDENT_NAME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Student student = cursorToStudent(cursor);
			students.add(student);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return students;
	}

	private Student cursorToStudent(Cursor cursor) {
		Student student = new Student();
		student.setId(cursor.getLong(0));
		student.setName(cursor.getString(1));
		return student;
	}
	

	
	public List<Student> getStudentsByActivity(SchoolActivity act)
	{
		List<Student> students = new ArrayList<Student>();
		final String MY_QUERY = "SELECT * FROM " +MySQLiteHelper.TABLE_STUDENTS 
				+ " student INNER JOIN " 
				+ MySQLiteHelper.TABLE_ENROLLMENT
				+" enroll ON student."+MySQLiteHelper.COL_STUDENT_ID
				+"=enroll."+MySQLiteHelper.COL_STUDENT_ID
				+" WHERE enroll."+MySQLiteHelper.COL_ACTIVITY_ID+"=?";

		Cursor cursor = database.rawQuery(MY_QUERY, new String[]{String.valueOf(act.getID())});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Student student = cursorToStudent(cursor);
			students.add(student);
			cursor.moveToNext();
		}
		return students;
	}
	
	/*
	 * Temporary method that creates some students if there aren't any.
	 * This may be used for convenience for now
	 * since db is not transferred across emulators along with project.
	 */
	public void tempPopulateStudents()
	{
		if ( this.getAllStudents().size()==0 )
		{
			createStudent("Duke of Wellington");
			createStudent("Chiang Kai-shek");
			createStudent("Mrs. Eclipse");
		}
	}
}

			