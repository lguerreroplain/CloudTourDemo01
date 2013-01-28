import com.microsoft.windowsazure.services.table.client.TableServiceEntity;


public class TwitterItem extends TableServiceEntity {
	public TwitterItem(){
		
	}
	
	
	public java.util.Date getCreatedAt() {
		return CreatedAt;
	}
	public void setCreatedAt(java.util.Date createdAt) {
		CreatedAt = createdAt;
	}

	public String getFromUser() {
		return FromUser;
	}

	public void setFromUser(String fromUser) {
		FromUser = fromUser;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getProfileImage() {
		return ProfileImage;
	}

	public void setProfileImage(String profileImage) {
		ProfileImage = profileImage;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	java.util.Date CreatedAt;
	String FromUser;
	String FromUserName;
	String ProfileImage;
	String Text;
	String Id;
}
