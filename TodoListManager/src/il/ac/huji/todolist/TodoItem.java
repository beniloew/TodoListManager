package il.ac.huji.todolist;

import java.io.Serializable;
import java.util.Date;

public class TodoItem implements Comparable<TodoItem>, Serializable{
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
		return dueTo.toString();
	}

	@Override
	public int compareTo(TodoItem another) {
		if (dueTo == null) return 1;
		return(dueTo.compareTo(another.getDueTo()));
	}
	
	public boolean equals(TodoItem other) {
		return title.equals(other.title) && dueTo.equals(other.dueTo);
	}
}
