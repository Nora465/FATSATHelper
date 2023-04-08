package com.nora465.fatsathelper.Utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class SQLTypesConverter {
	@TypeConverter //Timestamp (Long) To Date
	public static Date fromTimestamp(Long value) {
		return value == null ? null : new Date(value);
	}
	
	@TypeConverter //Date to Timestamp (Long)
	public static Long dateToTimestamp(Date date) {
		return date == null ? null : date.getTime();
	}
}
