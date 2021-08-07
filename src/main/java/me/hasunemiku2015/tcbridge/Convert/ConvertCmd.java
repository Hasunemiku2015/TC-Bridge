package me.hasunemiku2015.tcbridge.Convert;

import com.bergerkiller.bukkit.common.entity.CommonEntity;
import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConvertCmd implements CommandExecutor, TabCompleter {

    @Override
    @SuppressWarnings("rawtypes")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            String s;
            if(args.length < 1){
                //Detect if Traincarts
                Entity ent = player.getVehicle();

                if(ent == null){
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.AQUA + "[TC-Bridge] " + ChatColor.DARK_RED + "Error: You are not riding a MineCart!"));
                }

                if(Objects.requireNonNull(CommonEntity.get(ent)).getController() instanceof MinecartMember){
                    s = "vanilla";
                } else {
                    s = "traincarts";
                }
            } else {
                s = args[0];
            }

            switch(s){
                case "vanilla": {
                    Entity ent = player.getVehicle();

                    if(Objects.requireNonNull(CommonEntity.get(ent)).getController() instanceof MinecartMember){
                        MinecartMember m = (MinecartMember) Objects.requireNonNull(CommonEntity.get(ent)).getController();
                        if(m.getGroup().size() == 1){
                            Conversion.ConvertToVanilla(ent);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.AQUA + "[TC-Bridge] Successfully converted cart into " + ChatColor.GREEN + "Vanilla " + ChatColor.AQUA + "cart!"));
                            return true;
                        } else {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.AQUA + "[TC-Bridge] " + ChatColor.DARK_RED + "Error: Cannot convert cart, the cart is in a train!"));
                        }
                    } else {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.AQUA + "[TC-Bridge] " + ChatColor.DARK_RED + "Error: The entity is not Train_Carts cart!"));
                    }
                    return true;
                }
                case "traincarts":{
                    Entity ent = player.getVehicle();
                    if(ent instanceof Minecart){
                        Conversion.ConvertToTrainCarts(ent);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.AQUA + "[TC-Bridge] Successfully converted cart into " + ChatColor.DARK_PURPLE + "Train_Carts " + ChatColor.AQUA + "cart!"));
                    } else {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.AQUA + "[TC-Bridge] " + ChatColor.DARK_RED + "Error: The entity is not Vanilla cart!"));
                        return true;
                    }
                }
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
            return str;
        }
        return null;
    }
}
