package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TodoAdapter extends ArrayAdapter<TodoItem> {
	
	private List<TodoItem> items;
	
	public TodoAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		items = new ArrayList<TodoItem>();
	}

	public TodoAdapter(Context context, int textViewResourceId, List<TodoItem> objects) {
		super(context, textViewResourceId, objects);
		items = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
	      LayoutInflater inflater = LayoutInflater.from(getContext());
	      TodoItem item = items.get(position);
	      View listItem = inflater.inflate(R.layout.list_item, parent, false);
	      TextView titleView = (TextView) listItem.findViewById(R.id.txtTodoTitle);
	      TextView dueToView = (TextView) listItem.findViewById(R.id.txtTodoDueDate);
	      titleView.setText(item.getTitle());
	      dueToView.setText(item.getDueToStr());
	      Date now = new Date();
	      Date itemDate = item.getDueTo();
	      if (itemDate != null && now.compareTo(itemDate) > 1) {
	    	  titleView.setTextColor(Color.RED);
	    	  dueToView.setTextColor(Color.RED);
	      }
	      return listItem;
	}
	
	public void add(TodoItem item) {
		items.add(item);
		Collections.sort(items);
		notifyDataSetChanged();
	}
	
	public void remove(TodoItem item) {
		items.remove(item);
		notifyDataSetChanged();
	}
}
