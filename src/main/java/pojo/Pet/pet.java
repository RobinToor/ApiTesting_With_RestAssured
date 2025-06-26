package pojo.Pet;
import java.util.List;

public class pet {
	
	private int id;
	private category category;
	String name;
	private List<String> photoUrls;
	private List<tag> tags;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public category getCategory() {
		return category;
	}
	public void setCategory(category category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPhotoUrls() {
		return photoUrls;
	}
	public void setPhotoUrls(List<String> photoUrl) {
		this.photoUrls = photoUrl;
	}
	public List<tag> getTags() {
		return tags;
	}
	public void setTags(List<tag> tags) {
		this.tags = tags;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
