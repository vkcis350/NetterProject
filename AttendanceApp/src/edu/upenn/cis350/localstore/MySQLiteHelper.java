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
	
	private static final String DATABASE_NAME = "commments.db";
	private static final int DATABASE_VERSION = 4;
	

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
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENT);
		onCreate(db);
	}

}
