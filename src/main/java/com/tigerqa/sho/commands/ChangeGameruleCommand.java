package com.tigerqa.sho.commands;


import com.tigerqa.sho.DiscordWebhook;
import com.tigerqa.sho.ShoPlugin;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class ChangeGameruleCommand implements CommandExecutor {
    FileConfiguration config;
    ShoPlugin plugin;

    public ChangeGameruleCommand(ShoPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String playerName = sender.getName();
        if (Objects.equals(config.getString("law_minister"), playerName)) {
            GameRule<?> rule = GameRule.getByName(args[1]);
            if (rule == null) {
                sender.spigot().sendMessage(new TextComponent("Rule is not a real rule."));
                return true;
            }
            org.bukkit.World world = Bukkit.getWorlds().get(Integer.parseInt(args[0]));
            if (world == null) {
                sender.spigot().sendMessage(new TextComponent("Rule is not a real rule."));
                return true;
            }
            System.out.println(rule.getType().getName());
            if (rule.getType().getName().equals("java.lang.Boolean")) {
                GameRule<Boolean> boolRule = (GameRule<Boolean>) rule;
                boolean b = Boolean.parseBoolean(args[2]);
                try {
                    Integer t = Integer.parseInt(args[2]);
                    sender.spigot().sendMessage(new TextComponent("Enter true or false, not a number."));
                } catch(Exception e) {
                    if (!world.setGameRule(boolRule, Boolean.parseBoolean(args[2]))) {
                        sender.spigot().sendMessage(new TextComponent("Failed to change."));
                    } else {
                        sender.spigot().sendMessage(new TextComponent("Changed successfully"));
                        DiscordWebhook.SendMessage(String.format("# %s changed %s to %s", sender.getName(), args[1], args[2]), config.getString("webhook_url"));
                    }
                }
            } else if (rule.getType().getName().equals("java.lang.Integer")) {
                GameRule<Integer> intRule = (GameRule<Integer>) rule;
                if (!world.setGameRule(intRule, Integer.parseInt(args[2]))) {
                    sender.spigot().sendMessage(new TextComponent("Failed to change."));
                } else {
                    sender.spigot().sendMessage(new TextComponent("Changed successfully"));
                    DiscordWebhook.SendMessage(String.format("# %s changed %s to %s", sender.getName(), args[1], args[2]), config.getString("webhook_url"));
                }

            } else {
                sender.spigot().sendMessage(new TextComponent("Not a number or true or false."));
            }
        } else {
            sender.spigot().sendMessage(new TextComponent("You don't have permission to run this command. :("));
        }
        return true;
    }
}
