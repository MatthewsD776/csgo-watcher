package dev.darrenmatthews.csgo;

import java.util.Date;
import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEvent extends MongoConnector {
	
	@Expose
	@SerializedName("created")
	private Date createdTimeStamp;
	
	@Expose
	@SerializedName("sent")
	private Date sentTimeStamp;
	
	@Expose
	@SerializedName("properties")
	private HashMap<String, Object> properties;
	
	protected UserEvent() {
		super("data", "userEvents");
		this.createdTimeStamp = Helper.getDate();
	}

	public void recordEvent() {
		this.sentTimeStamp = Helper.getDate();
		this.insert();
	}
}
