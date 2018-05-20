package queue.blocking.priority;

public class Task implements Comparable<Task> {
	
	private int id ;
	private String name;
	private String content;
	
	public Task(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Task(int id, String name, String content) {
		this.id = id;
		this.name = name;
		this.content = content;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int compareTo(Task task) {
		return this.id - task.id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", content=" + content + "]";
	}
	
}
