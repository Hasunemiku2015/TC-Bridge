package me.hasunemiku2015.tcbridge.HandPlace;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerData implements CommandExecutor, TabCompleter {
    protected static List<Player> TCAsDefault = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length < 1){
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.AQUA + "[TC-Bridge] " + ChatColor.DARK_RED + "Error: Invalid hand-place mode!"));
                return true;
            }

            switch(args[0]){
                case "traincarts": {
                    TCAsDefault.add(player);
                    break;
                }

                case "vanilla" :{
                    TCAsDefault.remove(player);
                    break;
                }

                case "toggle" : {
                    if(TCAsDefault.contains(player)){
                        TCAsDefault.remove(player);
                    } else {
                        TCAsDefault.add(player);
                    }
                    break;
                }
            }

            if(TCAsDefault.contains(player)){
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.AQUA + "[TC-Bridge] New carts placed will be " + ChatColor.DARK_PURPLE + "Train_Carts" + ChatColor.AQUA + " carts."));
            } else {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.AQUA + "[TC-Bridge] New carts placed will be " + ChatColor.GREEN + "Vanilla" + ChatColor.AQUA + " carts."));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            List<String> str = new ArrayList<>();
            str.add("traincarts");
            str.add("vanilla");
            str.add("toggle");
            return str;
        }
        return null;
    }

}
