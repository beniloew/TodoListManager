package il.ac.huji.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {

	private TodoAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		adapter = new TodoAdapter(this, R.layout.list_item, new ArrayList<String>());
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
		String itemText = (String)list.getSelectedItem();
		adapter.remove(itemText);
	}

	private void addToList() {
		EditText edtText = (EditText)findViewById(R.id.edtNewItem);
		String text = edtText.getText().toString();
		adapter.add(text);
	}
}
