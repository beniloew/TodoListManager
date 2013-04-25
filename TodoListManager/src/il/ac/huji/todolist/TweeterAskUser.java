package il.ac.huji.todolist;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TweeterAskUser extends Activity {
	private Button ok, cancel;
	private TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweeter_ask_user);
		ok = (Button) findViewById(R.id.askOkBtn);
		cancel = (Button) findViewById(R.id.askCnclBtn);
		text = (TextView) findViewById(R.id.askTxtVw);
		ok.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent res = new Intent();
				setResult(RESULT_OK, res);
				finish();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent res = new Intent();
				setResult(RESULT_CANCELED, res);
				finish();
			}
		});
		Intent params = getIntent();
		boolean ok = params.getBooleanExtra("ok", false);
		if (!ok) {
			displayError();
		} else {
			int count = params.getIntExtra("count", 0);
			askUser(count);
		}
	}

	private void askUser(int count) {
		String str;
		if (count > 0) {
			str = count + " new tweets available.\nAdd them to the list?";
		} else {
			str = "Nothing new on Tweeter...";
		}
		text.setText(str);
		
	} 

	private void displayError() {
		ok.setVisibility(View.GONE);
		cancel.setText("Close");
		text.setText("Error: cannot get Tweets!");
	}
}
