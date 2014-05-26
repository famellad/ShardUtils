package com.gmail.famellad.shardutils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.command.defaults.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeSync extends BukkitRunnable {
	private final JavaPlugin plugin;
	
	public TimeSync (JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	private long getCurrentTime() {
		Calendar now = GregorianCalendar.getInstance();
		
		// Calculate the length of the day
		int dayOfYear = now.get(Calendar.DAY_OF_YEAR);
		int lengthOfDay = (int)(720d + 130d*Math.sin((2d/365d) * Math.PI * (101d + (double)dayOfYear)));
		
		// And both the sunrise and sunset times for that day, in minutes
		int sunrise = (int)((720d - (double)lengthOfDay/2d) * 1000d/60d);
		int sunset = (int)((double)sunrise + lengthOfDay * 1000d/60d);
		
		//plugin.getLogger().info("sr: " + sunrise + ", ss: " + sunset );
		
		//plugin.getLogger().info("Sunrise for day " + dayOfYear + " is at minute " + sunrise);		
		double h = (double)now.get(Calendar.HOUR_OF_DAY);
		double m = (double)now.get(Calendar.MINUTE);
		double s = (double)now.get(Calendar.SECOND);
		
		// Calculate the current REAL minute in 0..24000 format
		int realTime = (int)(10*(h*100 + m) + s/6);
		long time = 0;
		
		if (realTime > sunrise && realTime < sunset) { // Should be daytime!
			// Line through (sunrise, 6000) and (12000, 12000)
			double a = 6000d/((double)sunrise-12000d);
			double b = -(12000d*((double)sunrise-6000d))/((double)sunrise-12000);
			time = (long)(a*(double)realTime+b);
		}
		else if (realTime <= sunrise) {
			// Line through (0, 0) and (sunrise, 6000)
			double a = 6000d/(double)sunrise;
			time = (long)(a*(double)realTime);
		}
		else if (realTime >= sunset) {
			double a = 6000d/(double)sunrise;
			double b = (24000d*((double)sunrise-18000d))/((double)sunrise-24000);
			time = (long)(a*(double)realTime+b);
		}
		//Bukkit.getLogger().info("realTime: " + realTime + ", svTime: " + time );
		
		time -= 6000;
		
		time = time < 24000 ? time : time - 24000; // Time shouldn't be greater than 24000
		time = time > 0 ? time : time + 24000; // Nor less than 0
		
		return time;
	}
	
	@Override
	public void run() {
		//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set 600");
		
		Bukkit.getWorld("world").setTime(getCurrentTime());
	}
}
