package ru.euj3ne.e3serversettings;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;


public class BlockEventListener implements Listener {

    private final E3ServerSettings plugin;
    
    private final boolean noBreak;
    private final boolean noPlace;
    private final boolean noExplosions;
    private final boolean noFire;
    private final boolean fixedTime;
    private final long fixedTimeMeaning;
    
    public BlockEventListener(E3ServerSettings plugin) {
        this.plugin = plugin;
        
        var config = plugin.getConfig();
        this.noBreak = config.getBoolean("settings.noBreak");
        this.noPlace = config.getBoolean("settings.noPlace");
        this.noExplosions = config.getBoolean("settings.noExplosions");
        this.noFire = config.getBoolean("settings.noFire");
        this.fixedTime = config.getBoolean("settings.fixedTime.enabled");
        this.fixedTimeMeaning = config.getLong("settings.fixedTime.meaning");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (noBreak) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        if (noBreak) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (noPlace) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (noExplosions) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (noFire && event.toWeatherState()) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onLightning(LightningStrikeEvent event) {
        if (noFire) {
            event.setCancelled(true);
        }
    }
    
    public void setFixedTime() {
        if (fixedTime) {
            for (World world : Bukkit.getWorlds()) {
                world.setTime(fixedTimeMeaning);
            }
        }
    }

}