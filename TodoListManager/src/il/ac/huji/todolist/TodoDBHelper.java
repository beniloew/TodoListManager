package il.ac.huji.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDBHelper extends SQLiteOpenHelper{
	
	
	public final static String DB_TABLE = "todo";
	public final static String TITLE = "title";
	public final static String DUE = "due";
	public final static String SEEN_TWTS_TBL = "seenTweets";
	public final static String TWEET_ID = "tweetId";
	
	public TodoDBHelper(Context context) {
		super(context, "todo_db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// stores list items
		db.execSQL("create table " + DB_TABLE +"( " +
			       "  _id integer primary key autoincrement, " +
			       TITLE + " text, " +
			       DUE + " integer )");
		// stores seen twits
		db.execSQL("create table "+ SEEN_TWTS_TBL +" ( " +
			       "  _id integer primary key autoincrement, " +
			       TWEET_ID + " integer )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

}
