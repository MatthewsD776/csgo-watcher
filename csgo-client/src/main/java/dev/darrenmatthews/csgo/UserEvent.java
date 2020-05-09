package dev.darrenmatthews.csgo;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
		this.createdTimeStamp = getDate();
	}

	public void recordEvent() {
		this.sentTimeStamp = getDate();
		this.insert();
	}
	
	private Date getDate() {
		OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
		return Date.from(utc.toInstant());
	}
}
