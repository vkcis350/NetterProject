package edu.upenn.cis350;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import edu.upenn.cis350.R;

public class StudentDataActivity extends Activity{

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentdata);

	}
	
	public void onSaveDataClick(View v)
	{
		//Save data
		//...
		//...
		
		
		setResult(RESULT_OK);
		finish();
	}
	
	public void onCancelDataClick(View v)
	{
		setResult(RESULT_CANCELED);
		finish();
	}
}