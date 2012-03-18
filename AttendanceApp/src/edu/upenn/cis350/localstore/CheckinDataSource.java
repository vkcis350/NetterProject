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
		checkin.setID(c.getLong(MySQLiteHelper.CHECKINS_CHECKIN_ID_INDEX));
		checkin.setSessionID( c.getLong(MySQLiteHelper.CHECKINS_SESSION_ID_INDEX) );
		checkin.setActivityID( c.getLong(MySQLiteHelper.CHECKINS_ACTIVITY_ID_INDEX) );
		checkin.setStudentID( c.getLong(MySQLiteHelper.CHECKINS_STUDENT_ID_INDEX) );
		
		checkin.setInTime(c.getLong(MySQLiteHelper.CHECKINS_CHECKIN_TIME_INDEX));
		checkin.setOutTime(c.getLong(MySQLiteHelper.CHECKINS_CHECKOUT_TIME_INDEX ));
		
		checkin.setComment(c.getString(MySQLiteHelper.CHECKINS_CHECKIN_COMMENT_INDEX ) );
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
	
	/**
	 * returns the Checkin or returns null if none that match the parameters can be found
	 * @param sessionID
	 * @param activityID
	 * @param studentID
	 * @return
	 */
	public Checkin get(long sessionID, long activityID, long studentID)
	{
		Cursor c=database.query(MySQLiteHelper.TABLE_CHECKINS, 
				null, 
				MySQLiteHelper.COL_SESSION_ID+"=?"+" and "+MySQLiteHelper.COL_ACTIVITY_ID+"=?"+" and "+MySQLiteHelper.COL_STUDENT_ID+"=?",
				new String[]{sessionID+"",activityID+"",studentID+""}, null, null, null);
		c.moveToFirst();
		Checkin checkin;
		if (c.getCount()==0)
			checkin = null;
		else
		{
			checkin = (Checkin) cursorToModel(c);
		}
		c.close();
		return checkin;
	}
	
	/*
	 * If Checkin witch matching parameters exists, get it. If it doesn't create one with -1 and -1
	 * as check-in and check-out times (i.e., absent).
	 */
	public Checkin getOrCreate(long sessionID, long activityID, long studentID)
	{
		Cursor c=database.query(MySQLiteHelper.TABLE_CHECKINS, 
				null, 
				MySQLiteHelper.COL_SESSION_ID+"=?"+" and "+MySQLiteHelper.COL_ACTIVITY_ID+"=?"+" and "+MySQLiteHelper.COL_STUDENT_ID+"=?",
				new String[]{sessionID+"",activityID+"",studentID+""}, null, null, null);
		
		Checkin checkin;
		if (c.getCount()>0)
		{
			c.moveToFirst();
			checkin = (Checkin) cursorToModel(c);
		}
		else
		{
			checkin = (Checkin)create(sessionID,activityID,studentID,"");
		}
		c.close();
		return checkin;
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
