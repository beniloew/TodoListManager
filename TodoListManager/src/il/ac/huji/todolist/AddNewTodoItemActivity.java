package il.ac.huji.todolist;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class AddNewTodoItemActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_item);
		Button cancel = (Button) findViewById(R.id.btnCancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent res = new Intent();
				setResult(RESULT_CANCELED, res);
				finish();
			}
		});
		Button ok = (Button) findViewById(R.id.btnOK);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText titleView = (EditText) findViewById(R.id.edtNewItem);
				DatePicker dateView = (DatePicker) findViewById(R.id.datePicker);
				@SuppressWarnings("deprecation")
				Intent res = new Intent();
				res.putExtra("dueDate", new Date(dateView.getYear() - 1900, dateView.getMonth(), dateView.getDayOfMonth()));
				res.putExtra("title", titleView.getText().toString());
				setResult(RESULT_OK, res);
				finish();
			}
		});
	}

}
