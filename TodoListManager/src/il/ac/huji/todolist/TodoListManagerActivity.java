package il.ac.huji.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;



public class TodoListManagerActivity extends Activity {
	public static final int ADD_INTENT = 40531;
	private TodoAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		adapter = new TodoAdapter(this, R.layout.list_item);
		ListView list = (ListView)findViewById(R.id.lstTodoItems);
		list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menuItemAdd:
			addToList();
			break;
		case R.id.menuItemDelete:
			deleteSelectedItem();
		default:
		}
		return true;
	}

	private void deleteSelectedItem() {
		ListView list = (ListView)findViewById(R.id.lstTodoItems);
		TodoItem item = (TodoItem)list.getSelectedItem();
		adapter.remove(item);
	}

	private void addToList() {
		EditText edtText = (EditText)findViewById(R.id.edtNewItem);
		Intent addItem = new Intent(this, AddNewTodoItemActivity.class);
		startActivityForResult(addItem, ADD_INTENT);
//		TodoItem item = new TodoItem("beni", null);
//		adapter.add(item);
	}
	
	protected void onActivityResult(int reqCode, int resCode, Intent data) {
		switch (reqCode) {
		case ADD_INTENT: {
			if (resCode != RESULT_OK) return;
			TodoItem item = (TodoItem) data.getSerializableExtra("result");
			adapter.add(item);
		}
		}
	}
}
