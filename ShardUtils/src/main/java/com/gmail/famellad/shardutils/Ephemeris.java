package com.gmail.famellad.shardutils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Ephemeris {
	private static int sunrise = 0;
	private static int sunset = 0;
	
	public static void updateDay() {
		Calendar now = GregorianCalendar.getInstance();
		
		// Calculate the length of the day
		int dayOfYear = now.get(Calendar.DAY_OF_YEAR);
		int lengthOfDay = (int)(720d + 130d*Math.sin((2d/365d) * Math.PI * (101d + (double)dayOfYear)));
		
		// And both the sunrise and sunset times for that day, in minutes
		sunrise = (int)((720d - (double)lengthOfDay/2d) * 1000d/60d);
		sunset = (int)((double)sunrise + lengthOfDay * 1000d/60d);
		
	}
	
	public static int getSunrise() {
		return sunrise;
	}
	
	public static int getSunset() {
		return sunset;
	}
}
