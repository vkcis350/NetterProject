package edu.upenn.cis350;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CommentFormActivity extends SyncableActivity {
	
	String STUDENT_NAME = "Descartes, Rene";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commentform);

		Bundle extras = getIntent().getExtras();
	}
	
	public void onSubmitComment(View view)
    {
    	String text = ((EditText)findViewById(R.id.commentbox)).getText().toString();

    	Context context = getApplicationContext();
    	
		Toast.makeText(getApplicationContext(),"You said: "+text+". Unfortunately, this does not save yet.",
				Toast.LENGTH_SHORT).show();
    }
	
	protected void onStart()
	{
		super.onStart();
		TextView name = (TextView) findViewById(R.id.student_name);
		name.setText(STUDENT_NAME);
	}

	
	public void onCancelClick(View v){
		//back to last activity
		setResult(RESULT_CANCELED);
		finish();
	}
}
