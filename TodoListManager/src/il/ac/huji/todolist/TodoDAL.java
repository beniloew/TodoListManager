package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class TodoDAL {
	
	private SQLiteDatabase db;
	public final static String PARSE_CLASS = "todo";
	public final static String DB_TABLE = "todo";
	public final static String TITLE = "title";
	public final static String DUE = "due";
	
	
	public TodoDAL(Context context) {
		db = new TodoDBHelper(context).getWritableDatabase();
		Resources rs = context.getResources();
		Parse.initialize(context, rs.getString(R.string.parseApplication), rs.getString(R.string.clientKey));
		ParseUser.enableAutomaticUser();
	}
	
	public boolean insert(ITodoItem todoItem) {
		try {
			if (todoItem == null) return false;
			String title = todoItem.getTitle();
			Date dueTo = todoItem.getDueDate();
			if (title == null) return false;
			
			boolean ret = true;
			
			ContentValues todoItemCV = new ContentValues();
			ParseObject todoItemPO = new ParseObject(PARSE_CLASS);
			
			todoItemCV.put(TITLE, title);
			todoItemPO.put(TITLE, title);
			
			if (dueTo == null) {
				todoItemCV.putNull(DUE);
				todoItemPO.put(DUE, JSONObject.NULL);
			} else {
				long time = dueTo.getTime();
				todoItemCV.put(DUE, time);
				todoItemPO.put(DUE, time);
			}
			
			if (db.insert(DB_TABLE, null, todoItemCV) < 0) ret = false;
			todoItemPO.saveInBackground();
			
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}  
	}
	
	public boolean update(ITodoItem todoItem) {
		try {
			boolean ret = true;
			if (todoItem == null) return false;
			String title = todoItem.getTitle();
			final Date dueTo = todoItem.getDueDate();
			if (title == null) return false;
			
			ContentValues todoItemCV = new ContentValues();
			if (dueTo == null) {
				todoItemCV.putNull(DUE);
			} else {
				todoItemCV.put(DUE, dueTo.getTime());
			}
			if (db.update(DB_TABLE, todoItemCV , TITLE + "=?", new String[]{title}) < 0) ret = false;
			
			ParseQuery query = new ParseQuery(PARSE_CLASS);
			query.whereEqualTo(TITLE, title);
			query.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if (e == null) {
			            for (ParseObject parseObject : objects) {
								parseObject.put(DUE, dueTo.getTime());
								parseObject.saveInBackground();
						}
			        } else {
			            e.printStackTrace();
			        }
				}
			});
			
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delete(ITodoItem todoItem) {
		try {
			boolean ret = true;
			if (todoItem == null) return false;
			String title = todoItem.getTitle();
			if (title == null) return false;
			
			if (db.delete(DB_TABLE, TITLE + "=?", new String[]{title}) < 0) ret = false;
			
			ParseQuery query = new ParseQuery(PARSE_CLASS);
			query.whereEqualTo(TITLE, title);
			query.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if (e == null) {
			            for (ParseObject parseObject : objects) {
							try {
								parseObject.delete();
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
			        } else {
			            e.printStackTrace();
			        }
				}
			});
			
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ITodoItem> all() {
		Cursor cursor = db.query(DB_TABLE, new String[] {TITLE, DUE}, null, null, null, null, "case when " + DUE +" is null then 1 else 0 end, " + DUE);
		List<ITodoItem> result = new ArrayList<ITodoItem>();
		if (cursor.moveToFirst()) {
			do {
				String title = cursor.getString(0);
				long due = cursor.getLong(1);
				TodoItem item = new TodoItem(title, cursor.isNull(1) ? null : new Date(due));
				result.add(item);
			} while (cursor.moveToNext());
		}
		return result; 
	}
	
	public void closeDB() {
		db.close();
	}
}
