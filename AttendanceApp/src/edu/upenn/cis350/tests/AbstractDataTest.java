package edu.upenn.cis350.tests;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

public abstract class AbstractDataTest extends AndroidTestCase {
	Context context;
	
	public void setUp() throws Exception
	{
		super.setUp();
		makeDBContext();
	}
	
	/**
	 * Supplies object with a MockContext capable of returning a temporary in-memory SQLite database.
	 */
	public void makeDBContext() 
	{
		final SQLiteDatabase db = SQLiteDatabase.create(null);
        context = new MockContext() {
            @Override
            public SQLiteDatabase openOrCreateDatabase(String file, int mode, SQLiteDatabase.CursorFactory factory) {
                return db;
            };
            public SQLiteDatabase openOrCreateDatabase(String file, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
                	return db;
            }
        };
        
	}

}
