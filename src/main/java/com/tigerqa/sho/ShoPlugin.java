package com.tigerqa.sho;

import com.tigerqa.sho.commands.ChangeGameruleCommand;
import com.tigerqa.sho.commands.StopCommand;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ShoPlugin extends JavaPlugin implements Listener {
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.addDefault("webhook_url", "https://discord.com/api/webhooks/1173372564291211376/CwUcq1TJhsboIWdBIkJwsZE8xje3d-lyTX_q16j9S0uqBrvpHkA8S_AgC_YBczg1YcGK");
        config.addDefault("law_minister", "takesako_sho");
        config.addDefault("stop_access", new String[]{"takesako_sho"});
        config.options().copyDefaults(true);
        saveConfig();
        Objects.requireNonNull(this.getCommand("stopServer")).setExecutor(new StopCommand(this));
        Objects.requireNonNull(this.getCommand("changeGamerule")).setExecutor(new ChangeGameruleCommand(this));
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void commandRun(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        String playerName = event.getPlayer().getName();
        DiscordWebhook.SendMessage(String.format("%s ran command %s", playerName, message), config.getString("webhook_url"));
    }

    public FileConfiguration getConfiguration() {
        return config;
    }
}
