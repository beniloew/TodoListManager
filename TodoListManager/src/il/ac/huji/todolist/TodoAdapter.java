package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TodoAdapter extends ArrayAdapter<ITodoItem> {
	
	private List<ITodoItem> items;
	
	public TodoAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		items = new ArrayList<ITodoItem>();
	}

	public TodoAdapter(Context context, int textViewResourceId, List<ITodoItem> objects) {
		super(context, textViewResourceId, objects);
		items = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
	      LayoutInflater inflater = LayoutInflater.from(getContext());
	      TodoItem item = (TodoItem)items.get(position);
	      View listItem = inflater.inflate(R.layout.list_item, parent, false);
	      TextView titleView = (TextView) listItem.findViewById(R.id.txtTodoTitle);
	      TextView dueToView = (TextView) listItem.findViewById(R.id.txtTodoDueDate);
	      titleView.setText(item.getTitle());
	      dueToView.setText(item.getDueToStr());
	      Date now = new Date();
	      Date itemDate = item.getDueTo();
	      if (itemDate != null && now.compareTo(itemDate) > 0) {
	    	  titleView.setTextColor(Color.RED);
	    	  dueToView.setTextColor(Color.RED);
	      }
	      return listItem;
	}
	
	public void add(TodoItem item) {
		items.add(item);
		Collections.sort(items, new Comparator<ITodoItem>() {
			public int compare(ITodoItem i1, ITodoItem i2) {
				Date date1 = i1.getDueDate();
				Date date2 = i2.getDueDate();
				if (date1 == null) return 1;
				if (date2 == null) return -1;
		        return date1.compareTo(date2);
		    }
		});
		notifyDataSetChanged();
	}
	
	public void remove(TodoItem item) {
		items.remove(item);
		notifyDataSetChanged();
	}
	
	public void remove(int index) {
		items.remove(index);
		notifyDataSetChanged();
	}
	
	public TodoItem get(int i) {
		return (TodoItem)items.get(i);
	}
}
