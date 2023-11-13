package com.tigerqa.sho.commands;


import com.tigerqa.sho.DiscordWebhook;
import com.tigerqa.sho.ShoPlugin;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class StopCommand implements CommandExecutor {
    FileConfiguration config;
    ShoPlugin plugin;

    public StopCommand(ShoPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String playerName = sender.getName();
       if (config.getStringList("stop_access").contains(playerName)) {
           DiscordWebhook.SendMessage(String.format("# %s stopped the server", sender.getName()), config.getString("webhook_url"));
           try {
               Thread.sleep(100);
           } catch (InterruptedException e) {
               this.plugin.getServer().shutdown();
           }
           this.plugin.getServer().shutdown();
       } else {
           sender.spigot().sendMessage(new TextComponent("You don't have permission to run this command. :("));
       }
       return true;
    }
}
