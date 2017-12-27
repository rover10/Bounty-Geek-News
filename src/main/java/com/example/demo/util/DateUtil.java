package com.example.demo.util;

import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
	public static final long SEC_TO_MILLI = 1000L;
	public static final String STANDARD_MISSING_PART = "T00:00:00Z";
	public static final  long INDEX_BASE_EPOC = 1160418111;
	public static final long SEC_IN_A_DAY = 3600;
	public static final long  MIL_TO_SEC = 1000L;
			
	static public long convertDateToSec(String date) {
		Instant c = Instant.parse(date.length() < 10?date+STANDARD_MISSING_PART : date);
		//System.out.println(" Mili = " + c.getEpochSecond()*SEC_TO_MILLI);
		return c.getEpochSecond();
	}
}
