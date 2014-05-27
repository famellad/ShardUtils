package com.gmail.famellad.shardutils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.event.Listener;

public class ShardUtils extends JavaPlugin {
	private JavaPlugin plugin =  null;
	private Server server;
	
	@Override
	public void onEnable() {
		plugin = this;
		server = plugin.getServer();
		
		// Check for config
		
		//getLogger().info("onEnable() invoked! Please comment this thank");
		
		// Make sure DaylightCycle is false
		//getServer().getWorld("world").setGameRuleValue("doDaylightCycle", "false");
		
		// Calculate the sunrise and sunset for current day
		Ephemeris.updateDay();
		this.getLogger().info("Sunrise: " + Ephemeris.getSunrise());
		this.getLogger().info("Sunset:  " + Ephemeris.getSunset());
		
		// Create a scheduler to sync time
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		TimeSync timeTask = new TimeSync(this);
		
		// Call the task every 10 seconds (200 ticks)
		scheduler.scheduleSyncRepeatingTask(this, timeTask, 0L, 200L);
		
		// Register the portal listener
		registerListener(new PortalListener());
	}
	
	@Override
	public void onDisable() {
		
	}
	
	// Listener register function
	Listener[] listenerList;
	private void registerListener(Listener... listeners) {
		PluginManager manager = getServer().getPluginManager();
		listenerList = listeners;
		for (Listener listener : listeners) {
			manager.registerEvents(listener, this);
		}
	}
}
