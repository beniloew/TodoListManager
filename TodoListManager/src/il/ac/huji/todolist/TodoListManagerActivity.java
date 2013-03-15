package il.ac.huji.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TodoListManagerActivity extends Activity {

	private TodoAdapter adapter;
	private ArrayAdapter<String> adapter2;
	ArrayList<String> strs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		strs = new ArrayList<String>();
		strs.add("Beni");
		String[] bla = {"dsadsa", "dsadsa"};
		adapter = new TodoAdapter(this, R.layout.list_item, strs);
		adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bla);
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
		TextView item = (TextView) list.getSelectedView();
		if (item == null) return;
		String itemText = item.getText().toString();
		strs.remove(itemText);
		adapter.notifyDataSetChanged();
	}

	private void addToList() {
		EditText edtText = (EditText)findViewById(R.id.edtNewItem);
		String text = edtText.getText().toString();
		strs.add(text);
		adapter.notifyDataSetChanged();
	}
}
