package edu.upenn.cis350.localstore;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.upenn.cis350.models.Checkin;
import edu.upenn.cis350.models.Model;
import edu.upenn.cis350.models.SchoolActivity;

import edu.upenn.cis350.models.Student;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

	@Override
	@Deprecated
	public void create(Model model) {
		SchoolActivity activity = (SchoolActivity)model;
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COL_ACTIVITY_NAME, activity.getName() );
		long insertId = database.insert(MySQLiteHelper.TABLE_ACTIVITIES, null,
				values);
		activity.setId(insertId);
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
	
	
	public void populateFromList(List<SchoolActivity> objList){
		for(SchoolActivity act : objList){
			create(act.getId(), act.getName());
		}
	}
	
	public List<SchoolActivity> convertJson(String s){
		Gson gson=new Gson();
		//String json = gson.toJson(s);
		Type collectionType = new TypeToken<List<SchoolActivity>>(){}.getType();
		List<SchoolActivity> deserialized = gson.fromJson(s, collectionType);
		return deserialized;
	}
	
	/**assumes database has been emptied**/
	@SuppressWarnings("unchecked")
	public void importFromjson(String json){
		populateFromList((List<SchoolActivity>)convertJson(json));
	}
	
}
