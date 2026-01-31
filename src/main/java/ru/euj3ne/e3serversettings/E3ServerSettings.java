package ru.euj3ne.e3serversettings;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class E3ServerSettings extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        Bukkit.getPluginManager().registerEvents(new PlayerEventListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockEventListener(this), this);
        
        getLogger().info("Plugin has been enabled!");
        getLogger().info("Plugin developed by: @euj3ne");
        getLogger().info("Website: " + getDescription().getWebsite());
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled!");
    }
}
