package dev.darrenmatthews.csgo;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Helper {
	
	public static Date getDate() {
		OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
		return Date.from(utc.toInstant());
	}

}
