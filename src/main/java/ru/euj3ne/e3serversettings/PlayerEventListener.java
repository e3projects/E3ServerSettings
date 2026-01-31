package ru.euj3ne.e3serversettings;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.PacketType;

import java.util.List;

public class PlayerEventListener implements Listener {

    private final E3ServerSettings plugin;
    private final ProtocolManager protocolManager;

    private final boolean noPlayer;
    private final boolean noMessage;
    private final boolean noDamage;
    private final boolean noHunger;
    private final boolean noChat;
    private final boolean noCommands;
    private final String gameModeStr;
    private final List<String> allowedCommands;

    public PlayerEventListener(E3ServerSettings plugin) {
        this.plugin = plugin;
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        
        var config = plugin.getConfig();
        this.noPlayer = config.getBoolean("settings.noPlayer");
        this.noMessage = config.getBoolean("settings.noMessage");
        this.noDamage = config.getBoolean("settings.noDamage");
        this.noHunger = config.getBoolean("settings.noHunger");
        this.noChat = config.getBoolean("settings.noChat");
        this.noCommands = config.getBoolean("settings.noCommands.enabled");
        this.allowedCommands = config.getStringList("settings.noCommands.allowed_commands");
        this.gameModeStr = config.getString("settings.gameMode");


        if (noPlayer) {
            registerPlayerInfoPacketListener();
        }
    }

    private void registerPlayerInfoPacketListener() {
        protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.PLAYER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.setCancelled(true);
            }
        });
    }

    private void setPlayerGameMode(Player player) {
        try {
            GameMode gameMode = GameMode.valueOf(gameModeStr.toUpperCase());
            player.setGameMode(gameMode);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Неверный игровой режим в конфиге: " + gameModeStr);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (noMessage) {
            event.setJoinMessage(null);
        }
        setPlayerGameMode(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (noMessage) {
            event.setQuitMessage(null);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (noMessage) {
            event.setDeathMessage(null);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (noMessage) {
            event.setLeaveMessage(null);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (noDamage) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (noHunger) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (noChat) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (noCommands) {
            String command = event.getMessage().split(" ")[0].substring(1);
            if (!allowedCommands.contains(command)) {
                event.setCancelled(true);
            }
        }
    }
}