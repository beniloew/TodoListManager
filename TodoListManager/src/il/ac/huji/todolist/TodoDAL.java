package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class TodoDAL {
	
	private SQLiteDatabase db;
	
	public TodoDAL(Context context) {
		db = new TodoDBHelper(context).getWritableDatabase();
	}
	
	public boolean insert(ITodoItem todoItem) {
		if (todoItem == null) return false;
		String title = todoItem.getTitle();
		Date dueTo = todoItem.getDueDate();
		if (title == null) return false;
		
		ContentValues todoItemCV = new ContentValues();
		todoItemCV.put("title", title);
		if (dueTo == null) {
			todoItemCV.putNull("due");
		} else {
			todoItemCV.put("due", dueTo.getTime());
		}
		if (db.insert("todo", null, todoItemCV) < 0) return false;
		return true;  
	}
	
	public boolean update(ITodoItem todoItem) {
		if (todoItem == null) return false;
		String title = todoItem.getTitle();
		Date dueTo = todoItem.getDueDate();
		if (title == null) return false;
		
		ContentValues todoItemCV = new ContentValues();
		if (dueTo == null) {
			todoItemCV.putNull("due");
		} else {
			todoItemCV.put("due", dueTo.getTime());
		}
		if (db.update("todo", todoItemCV , "title=?", new String[]{title}) < 0) return false;
		return true;
	}
	
	public boolean delete(ITodoItem todoItem) {
		if (todoItem == null) return false;
		String title = todoItem.getTitle();
		if (title == null) return false;
		
		if (db.delete("todo", "title=?", new String[]{title}) < 0) return false;
		return true;
	}
	
	public List<ITodoItem> all() {
		Cursor cursor = db.query("todo", new String[] {"title", "due"}, null, null, null, null, "due asc");
		List<ITodoItem> result = new ArrayList<ITodoItem>();
		if (cursor.moveToFirst()) {
			do {
				String title = cursor.getString(0);
				long due = cursor.getLong(1);
				TodoItem item = new TodoItem(title, new Date(due));
				result.add(item);
			} while (cursor.moveToNext());
		}
		return result; 
	}
	
	public void closeDB() {
		db.close();
	}
}
