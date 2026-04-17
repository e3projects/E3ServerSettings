package ru.euj3ne.e3serversettings;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class E3ServerSettingsMain extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new PlayerEventListener(this), this);

        BlockEventListener blockEventListener = new BlockEventListener(this);
        Bukkit.getPluginManager().registerEvents(blockEventListener, this);
        blockEventListener.applyWorldSettings();

        getLogger().info("Plugin has been enabled!");
        getLogger().info("Plugin developed by: " + String.join(", ", getPluginMeta().getAuthors()));
        getLogger().info("Website: " + getPluginMeta().getWebsite());
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled!");
    }
}
