package edu.upenn.cis350;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import edu.upenn.cis350.R;

public class StudentCommentActivity extends Activity{

	
	Student thisStudent;
	String studentName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leavecomment);

	}
}