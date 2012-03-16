package edu.upenn.cis350.localstore;

import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.Student;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class CheckinDataSource extends DataSource {
	final static String[] TABLES = { MySQLiteHelper.TABLE_CHECKINS};
	final static String ID_COLUMN = MySQLiteHelper.COL_CHECKIN_ID;

	public CheckinDataSource(Context context) {
		super(context);
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
	protected Model cursorToModel(Cursor c) {
		Checkin checkin = new Checkin();
		checkin.setID(c.getLong(0));
		checkin.setSessionID( c.getLong(1) );
		checkin.setActivityID( c.getLong(2) );
		checkin.setStudentID( c.getLong(3) );
		
		checkin.setInTime(c.getLong(4));
		checkin.setOutTime(c.getLong(5));
		
		checkin.setComment(c.getString(6) );
		return checkin;
		
	}

	@Override
	public void create(Model model) {
		
	}
	
	public Model create(long sessionID, long activityID, long studentID, String comment)
	{
		Checkin checkin = new Checkin();
		checkin.setSessionID(sessionID);
		checkin.setActivityID(activityID);
		checkin.setStudentID(studentID);
		
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_SESSION_ID, sessionID );
		values.put(MySQLiteHelper.COL_ACTIVITY_ID, activityID );
		values.put(MySQLiteHelper.COL_STUDENT_ID, studentID );	
		values.put(MySQLiteHelper.COL_CHECKIN_TIME, -1 );	
		values.put(MySQLiteHelper.COL_CHECKOUT_TIME, -1 );
		values.put(MySQLiteHelper.COL_CHECKIN_COMMENT, comment );
		
		long insertId = database.insert(MySQLiteHelper.TABLE_CHECKINS, null,
				values);
		
		return this.get(insertId);

	}
	
	public Checkin get(long sessionID, long activityID, long studentID)
	{
		Cursor c=database.query(MySQLiteHelper.TABLE_CHECKINS, 
				null, 
				MySQLiteHelper.COL_SESSION_ID+"=?"+" and "+MySQLiteHelper.COL_ACTIVITY_ID+"=?"+" and "+MySQLiteHelper.COL_STUDENT_ID+"=?",
				new String[]{sessionID+"",activityID+"",studentID+""}, null, null, null);
		c.moveToFirst();
		return (Checkin) cursorToModel(c);
	}
	
	public void save(Checkin checkin)
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_CHECKIN_TIME, checkin.getInTime() );
		values.put(MySQLiteHelper.COL_CHECKOUT_TIME, checkin.getOutTime() );
		
		database.update(MySQLiteHelper.TABLE_CHECKINS, 
				values, 
				MySQLiteHelper.COL_SESSION_ID+"=?"+" and "+MySQLiteHelper.COL_ACTIVITY_ID+"=?"+" and "+MySQLiteHelper.COL_STUDENT_ID+"=?",
				new String[]{checkin.getSessionID()+"",checkin.getActivityID()+"",checkin.getStudentID()+""});
	}

}
