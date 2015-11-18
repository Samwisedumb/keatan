package shared.transferClasses;


public class CreateGameResponse {
	private String title;
	private int id;
	
	public CreateGameResponse(String title, int id) {
		setTitle(title);
		setId(id);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
