package com.gmail.famellad.shardutils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import com.gmail.famellad.shardutils.*;

public class PortalListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerPortal(PlayerPortalEvent event) {
		GameMode g = event.getPlayer().getGameMode();
		long time = Bukkit.getWorld("world").getTime();
		if ( time > 0 && time < 12500 )
			if (g != GameMode.CREATIVE)
				event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityPortal(EntityPortalEvent event) {
		long time = Bukkit.getWorld("world").getTime();
		if ( time > 0 && time < 12500 )
			event.setCancelled(true);
	}
}
