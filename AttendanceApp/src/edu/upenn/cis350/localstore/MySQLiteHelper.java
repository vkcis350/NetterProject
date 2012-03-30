package edu.upenn.cis350.localstore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_STUDENTS = "student";
	public static final String COL_STUDENT_ID = "_id";
	public static final String COL_STUDENT_LAST_NAME = "last_name";
	public static final String COL_STUDENT_FIRST_NAME = "first_name";
	public static final String COL_STUDENT_PHONE = "phone";
	public static final String COL_STUDENT_CONTACT = "contact";
	public static final String COL_STUDENT_CONTACT_RELATION = "contact_relation";
	public static final String COL_STUDENT_SCHOOLYEAR = "schoolyear";

	public static final String COL_SCHOOL_ID = "school_id";
	public static final String COL_SITE_ID = "site_id";
	
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
	
	public static final int STUDENT_STUDENT_ID_INDEX = 0;
	public static final int STUDENT_STUDENT_LAST_NAME_INDEX = 1;
	public static final int STUDENT_STUDENT_FIRST_NAME_INDEX = 2;
	public static final int STUDENT_STUDENT_PHONE_INDEX = 3;
	public static final int STUDENT_STUDENT_CONTACT_INDEX = 4;
	public static final int STUDENT_STUDENT_CONTACT_RELATION_INDEX = 5;
	public static final int STUDENT_SCHOOL_ID_INDEX = 6;
	public static final int STUDENT_SITE_ID_INDEX = 7;
	public static final int STUDENT_SCHOOLYEAR_INDEX = 8;
	
	public static final int ACTIVITIES_ACTIVITY_ID_INDEX = 0;
	public static final int ACTIVITIES_ACTIVITY_NAME_INDEX = 1;
	
	public static final int CHECKINS_CHECKIN_ID_INDEX = 0;
	public static final int CHECKINS_SESSION_ID_INDEX = 1;
	public static final int CHECKINS_ACTIVITY_ID_INDEX = 2;
	public static final int CHECKINS_STUDENT_ID_INDEX = 3;
	public static final int CHECKINS_CHECKIN_TIME_INDEX = 4;
	public static final int CHECKINS_CHECKOUT_TIME_INDEX = 5;
	public static final int CHECKINS_CHECKIN_COMMENT_INDEX = 6;
	
	private static final String DATABASE_NAME = "attendance.db";
	private static final int DATABASE_VERSION = 71;
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("create table "
				+ TABLE_STUDENTS + "( " 
				+ COL_STUDENT_ID + " integer primary key, " 
				+ COL_STUDENT_LAST_NAME + " text,"
				+ COL_STUDENT_FIRST_NAME + " text,"
				+ COL_STUDENT_PHONE + " text,"
				+ COL_STUDENT_CONTACT + " text,"
				+ COL_STUDENT_CONTACT_RELATION + " text,"
				+ COL_SCHOOL_ID + " integer,"
				+ COL_SITE_ID + " integer,"
				+ COL_STUDENT_SCHOOLYEAR + " integer"
				+");");
		
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
