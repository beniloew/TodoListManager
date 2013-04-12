package il.ac.huji.todolist;

import java.util.Date;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;



public class TodoListManagerActivity extends Activity {
	public static final int ADD_INTENT = 40531;
	private TodoAdapter adapter;
	private TodoDAL dal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		dal = new TodoDAL(this);
		adapter = new TodoAdapter(this, R.layout.list_item, dal.all());
		ListView list = (ListView)findViewById(R.id.lstTodoItems);
		list.setAdapter(adapter);
		registerForContextMenu(list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);
		return true;
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info) {
		super.onCreateContextMenu(menu, v, info);
		getMenuInflater().inflate(R.menu.delete_ctx_menu, menu);
		AdapterContextMenuInfo ctxInfo = (AdapterContextMenuInfo) info;
		String title = adapter.get(ctxInfo.position).getTitle();
		menu.setHeaderTitle(title);
		if (title.startsWith("Call ")) {
			menu.findItem(R.id.menuItemCall).setTitle(title);
		} else {
			menu.removeItem(R.id.menuItemCall);
		}
	}
	
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo ctxInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		switch (item.getItemId())
		{
			case R.id.menuItemDelete:
				dal.delete(adapter.get(ctxInfo.position));
				adapter.remove(ctxInfo.position);
				break;

			case R.id.menuItemCall:
				String callTo = item.getTitle().subSequence(5, item.getTitle().length()).toString();
				Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+callTo));
				startActivity(dial);
				break;
		}
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
		dal.delete(item);
	}

	private void addToList() {
		Intent addItem = new Intent(this, AddNewTodoItemActivity.class);
		startActivityForResult(addItem, ADD_INTENT);
	}
	
	protected void onActivityResult(int reqCode, int resCode, Intent data) {
		switch (reqCode) {
		case ADD_INTENT: {
			if (resCode != RESULT_OK) return;
			String title = data.getStringExtra("title");
			Date dueTo = (Date) data.getSerializableExtra("dueDate");
			TodoItem item = new TodoItem(title, dueTo);
			adapter.add(item);
			dal.insert(item);
		}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dal.closeDB();
	}
}
