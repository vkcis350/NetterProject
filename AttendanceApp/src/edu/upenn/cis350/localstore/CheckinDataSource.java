package edu.upenn.cis350.localstore;

import java.util.ArrayList;
import java.util.List;

import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.Student;
import edu.upenn.cis350.util.DateTimeHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

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
		checkin.setId(c.getLong(MySQLiteHelper.CHECKINS_CHECKIN_ID_INDEX));
		checkin.setSessionID( c.getLong(MySQLiteHelper.CHECKINS_SESSION_ID_INDEX) );
		checkin.setActivityID( c.getLong(MySQLiteHelper.CHECKINS_ACTIVITY_ID_INDEX) );
		checkin.setStudentID( c.getLong(MySQLiteHelper.CHECKINS_STUDENT_ID_INDEX) );
		
		checkin.setInTime(c.getLong(MySQLiteHelper.CHECKINS_CHECKIN_TIME_INDEX));
		checkin.setOutTime(c.getLong(MySQLiteHelper.CHECKINS_CHECKOUT_TIME_INDEX ));
		
		checkin.setLastChangeTime(c.getLong(MySQLiteHelper.CHECKINS_LAST_CHANGE_INDEX ) );
		return checkin;
	}

	@Override
	@Deprecated
	public void create(Model model) {
		
	}
	
	public Model create(long time, long activityID, long studentID)
	{
		Checkin checkin = new Checkin();
		checkin.setLastChangeTime(time);
		checkin.setSessionID(0);
		checkin.setActivityID(activityID);
		checkin.setStudentID(studentID);
		checkin.setComment("");
		
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_SESSION_ID, 0 );
		values.put(MySQLiteHelper.COL_ACTIVITY_ID, activityID );
		values.put(MySQLiteHelper.COL_STUDENT_ID, studentID );	
		values.put(MySQLiteHelper.COL_CHECKIN_TIME, 0 );	
		values.put(MySQLiteHelper.COL_CHECKOUT_TIME, 0 );
		values.put(MySQLiteHelper.COL_LAST_CHANGE, time );
		values.put(MySQLiteHelper.COL_COMMENT, "" );
		
		long insertId = database.insert(MySQLiteHelper.TABLE_CHECKINS, null,
				values);
		
		return this.get(insertId);

	}
	
	/**
	 * Get all Checkins where last change time was on the same day as the current time
	 * @param time
	 * @param activityID
	 * @param studentID
	 * @return
	 */
	public Checkin getForDay(long time, long activityID, long studentID)
	{
		long beginTime=DateTimeHelper.todayStart();
		long endTime=DateTimeHelper.plusOneDay(beginTime);
		Cursor c=database.query(MySQLiteHelper.TABLE_CHECKINS, 
				null, 
				MySQLiteHelper.COL_LAST_CHANGE+" between ? and ?"+" and "+MySQLiteHelper.COL_ACTIVITY_ID+"=?"+" and "+MySQLiteHelper.COL_STUDENT_ID+"=?",
				new String[]{beginTime+"",endTime+"",activityID+"",studentID+""}, null, null, null);
		return (Checkin)getFirstModel(c);

	}
	
	
	public void save(Checkin checkin)
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_CHECKIN_TIME, checkin.getInTime() );
		values.put(MySQLiteHelper.COL_CHECKOUT_TIME, checkin.getOutTime() );
		values.put(MySQLiteHelper.COL_LAST_CHANGE, checkin.getLastChangeTime());
		values.put(MySQLiteHelper.COL_COMMENT, checkin.getComment());
		database.update(MySQLiteHelper.TABLE_CHECKINS, 
				values, 
				MySQLiteHelper.COL_SESSION_ID+"=?"+" and "+MySQLiteHelper.COL_ACTIVITY_ID+"=?"+" and "+MySQLiteHelper.COL_STUDENT_ID+"=?",
				new String[]{checkin.getSessionID()+"",checkin.getActivityID()+"",checkin.getStudentID()+""});
	}


	public ArrayList<Checkin> getByStudent(long studentID) {
		Cursor cursor=recentByStudentCursor(studentID);
		ArrayList<Checkin> checkins = new ArrayList<Checkin>();
		while (cursor.moveToNext())
		{
			checkins.add( (Checkin)cursorToModel(cursor) );
		}
		cursor.close();
		return checkins;
	}
	
	
	public Checkin getMostRecentForStudent(long studentID) {
		Cursor cursor=recentByStudentCursor(studentID);
		Checkin checkin = null;
		Log.d("CheckinDatasource","Found "+cursor.getCount()+" Checkins for student "+studentID);
		return (Checkin)getFirstModel(cursor);
	}
	
	
	public Cursor recentByStudentCursor(long studentID) {
		return database.query(MySQLiteHelper.TABLE_CHECKINS, 
				null, 
				MySQLiteHelper.COL_STUDENT_ID+"=?",
				new String[]{studentID+""}, null, null, MySQLiteHelper.COL_LAST_CHANGE+" DESC");
	}

//Don't use the following methods for now- the usage of create and save doesn't properly handle the id field
	public void populateFromList(List<Checkin> objList){
		for(Checkin obj : objList){
			create(obj.getSessionID(),obj.getActivityID(),obj.getStudentID());
			save(obj);
		}
	}
	/**assumes database has been emptied**/
	@SuppressWarnings("unchecked")
	public void importFromjson(String json){
		populateFromList((List<Checkin>)convertJson(json));
	}
}
