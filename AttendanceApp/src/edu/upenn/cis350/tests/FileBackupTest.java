
package edu.upenn.cis350.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.upenn.cis350.util.FileBackup;

import android.os.Environment;
import android.test.AndroidTestCase;

public class FileBackupTest extends AndroidTestCase {
	public void testCopyFile() throws IOException
	{
		final String FILENAME  = "/data/data/edu.upenn.cis350/somefile";
		final String BACKUP_FILENAME  = "/data/data/edu.upenn.cis350/backupfile";
		final String SOME_STRING = "Whatever.";
		FileWriter fstream = new FileWriter(FILENAME);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(SOME_STRING);
		out.close();
		
		FileBackup.copyFile(FILENAME,BACKUP_FILENAME);
		
		FileInputStream finstream = new FileInputStream(BACKUP_FILENAME);
		DataInputStream in = new DataInputStream(finstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = br.readLine();
		assertEquals(strLine,SOME_STRING);
	}
	
	

}
