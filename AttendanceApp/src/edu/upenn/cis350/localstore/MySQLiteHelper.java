package edu.upenn.cis350.localstore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_STUDENTS = "student";
	public static final String COL_STUDENT_ID = "_id";
	public static final String COL_STUDENT_NAME = "studentName";

	public static final String TABLE_ENROLLMENT = "enrollment";
	public static final String COL_ACTIVITY_ID ="activity_id";
	
	public static final String TABLE_ACTIVITIES = "activity";
	public static final String COL_ACTIVITY_NAME = "activityName";
	
	public static final String TABLE_CHECKINS = "checkin";
	static final String COL_CHECKIN_ID = "checkin_id";
	public static final String COL_SESSION_ID = "session_id";
	public static final String COL_CHECKIN_TIME = "checkin_time";
	public static final String COL_CHECKOUT_TIME = "checkout_time";
	public  static final String COL_CHECKIN_COMMENT = "checkin_comment";
	
	
	
	private static final String DATABASE_NAME = "attendance.db";
	private static final int DATABASE_VERSION = 35;
	

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("create table "
				+ TABLE_STUDENTS + "( " + COL_STUDENT_ID
				+ " integer primary key autoincrement, " + COL_STUDENT_NAME
				+ " TEXT);");
		
		database.execSQL("create table "
				+ TABLE_ENROLLMENT + "( " + COL_STUDENT_ID
				+ " integer, " + COL_ACTIVITY_ID
				+ " integer, PRIMARY KEY("+COL_STUDENT_ID+","+COL_ACTIVITY_ID+") );");
		
		database.execSQL("create table "
				+ TABLE_ACTIVITIES + "( " + COL_ACTIVITY_ID
				+ " integer primary key autoincrement, " + COL_ACTIVITY_NAME
				+ " TEXT);");
		
		database.execSQL("create table "
				+ TABLE_CHECKINS + "( " 
				+ COL_CHECKIN_ID + " integer primary key autoincrement,"
				+ COL_SESSION_ID + " integer, " 
				+ COL_ACTIVITY_ID + " integer, " 
				+ COL_STUDENT_ID + " integer, "
				+ COL_CHECKIN_TIME + " integer, "
				+ COL_CHECKOUT_TIME + " integer, "
				+ COL_CHECKIN_COMMENT + " TEXT" 
				+ ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKINS);
		onCreate(db);
	}


}
