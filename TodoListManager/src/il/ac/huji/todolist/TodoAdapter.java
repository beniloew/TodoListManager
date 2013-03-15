package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TodoAdapter extends ArrayAdapter<String> {
	
	private List<String> items;
	private Context context;
	
	public TodoAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		items = new ArrayList<String>();
		this.context = context;
	}

	public TodoAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		items = objects;
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
	      LayoutInflater inflater = LayoutInflater.from(context);
	      TextView txt = (TextView) inflater.inflate(R.layout.list_item, parent, false);
	      txt.setText(items.get(position));
	      txt.setTextColor(position % 2 == 0 ? Color.RED : Color.BLUE);
	      return txt;
	}
	
	public void add(String str) {
		items.add(str);
	}
}