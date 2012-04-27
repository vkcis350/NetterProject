package edu.upenn.cis350.localstore;

import java.util.ArrayList;
import java.util.List;

import edu.upenn.cis350.models.FrequentActivity;
import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.SchoolActivity;

import edu.upenn.cis350.models.Student;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SchoolActivityDataSource extends DataSource {
	final static String[] TABLES = { MySQLiteHelper.TABLE_ACTIVITIES };
	final static String ID_COLUMN = MySQLiteHelper.COL_ACTIVITY_ID;
	
	public SchoolActivityDataSource(Context context)
	{
		super(context);
	}
	
	protected Model cursorToModel(Cursor cursor) {
		SchoolActivity act = new SchoolActivity();
		act.setId(cursor.getLong(MySQLiteHelper.ACTIVITIES_ACTIVITY_ID_INDEX ));
		act.setName(cursor.getString(MySQLiteHelper.ACTIVITIES_ACTIVITY_NAME_INDEX));
		return act;
	}

	@Override
	protected String[] getTables() {
		return TABLES;
	}

	@Override
	protected String getIDColumn() {
		return ID_COLUMN;
	}
	
	public SchoolActivity create(String name)
	{
		return create(-1,name);
	}
	
	public SchoolActivity create(long id, String name)
	{
		ContentValues values = new ContentValues();
		if (id!=-1)
			values.put(MySQLiteHelper.COL_ACTIVITY_ID, id );
		values.put(MySQLiteHelper.COL_ACTIVITY_NAME, name );
		long insertId = database.insert(MySQLiteHelper.TABLE_ACTIVITIES, null,
				values);
		return (SchoolActivity) get(insertId);
	}
	
	public ArrayList<SchoolActivity> getAll()
	{
		return (ArrayList<SchoolActivity>) getAll(MySQLiteHelper.COL_ACTIVITY_NAME);
	}
	
	//also requires Frequent Activities table
	public ArrayList<SchoolActivity> getFrequentSchoolActivities(long userId)
	{
		final String MY_QUERY = "SELECT * FROM " +MySQLiteHelper.TABLE_ACTIVITIES
				+ " INNER JOIN " 
				+ MySQLiteHelper.TABLE_FREQUENT_ACTIVITIES
				+" ON "+MySQLiteHelper.TABLE_ACTIVITIES+"."+MySQLiteHelper.COL_ACTIVITY_ID
				+"="+MySQLiteHelper.TABLE_FREQUENT_ACTIVITIES+"."+MySQLiteHelper.COL_ACTIVITY_ID
				+" WHERE "+MySQLiteHelper.TABLE_FREQUENT_ACTIVITIES+"."+MySQLiteHelper.COL_USER_ID+"=?";
		Log.d("FrequentActivityDataSource",MY_QUERY);
		
		Cursor cursor = database.rawQuery(MY_QUERY, new String[]{userId+""});
		 
		return (ArrayList<SchoolActivity>)getAllModels(cursor);
	}
	
	
}
