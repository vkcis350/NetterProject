package edu.upenn.cis350;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import edu.upenn.cis350.R;
import edu.upenn.cis350.models.Student;

public class StudentCommentActivity extends Activity{

	//FOR UI TESTING
	String[] COMMENT_ARRAY = {"Behaved today.", 
			"Fought well at Waterloo", 
			"Lost Wuchang because of him today.", 
			"Couldn't find Zambia on a map. Unbelievable."};
	
	String studentName;
	long studentID;
	ArrayList<String> comments;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leavecomment);
		
		Bundle extras = getIntent().getExtras();
		studentName = extras.getString("STUDENT_NAME");
		studentID = extras.getLong("STUDENT_ID");
		
		ListView lv = (ListView) findViewById(R.id.comment_list);
		lv.setTextFilterEnabled(true);
		lv.setChoiceMode(lv.CHOICE_MODE_NONE);
		reloadList();

	}
	
	public void onCommentBackClick(View view)
	{
		setResult(RESULT_CANCELED);
		finish();
	}
	
	public void onAddCommentClick(View view)
	{
		Toast.makeText(getApplicationContext(), "Okay. I would let you leave a comment, but it hasn't been implemented yet",
				Toast.LENGTH_SHORT).show();
	}
	
	public void reloadList()
	{
		ListView lv = (ListView) findViewById(R.id.comment_list);
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, COMMENT_ARRAY));
	}
}