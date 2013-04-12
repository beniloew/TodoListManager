package il.ac.huji.todolist;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem implements Comparable<TodoItem>, Serializable, ITodoItem{
	private static final long serialVersionUID = 1L;
	private String title;
	private Date dueTo;
	
	public TodoItem(String title, Date dueTo) {
		this.title = title;
		this.dueTo = dueTo;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Date getDueTo() {
		return dueTo;
	}
	
	public String getDueToStr() {
		if (dueTo == null) return "No due date";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(dueTo);
	}

	@Override
	public int compareTo(TodoItem another) {
		if (dueTo == null ) return 1;
		if (another.dueTo == null) return -1;
		return(dueTo.compareTo(another.dueTo));
	}
	
	public boolean equals(TodoItem other) {
		return title.equals(other.title) && dueTo.equals(other.dueTo);
	}

	@Override
	public Date getDueDate() {
		return dueTo;
	}
}
