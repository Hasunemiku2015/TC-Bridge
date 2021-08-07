package me.hasunemiku2015.tcbridge.Convert;

import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.events.SignActionEvent;
import com.bergerkiller.bukkit.tc.events.SignChangeActionEvent;
import com.bergerkiller.bukkit.tc.signactions.SignAction;
import com.bergerkiller.bukkit.tc.signactions.SignActionType;
import com.bergerkiller.bukkit.tc.utils.SignBuildOptions;
import me.hasunemiku2015.tcbridge.App;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.util.Vector;

public class TCToVanillaSign extends SignAction {
    @Override
    public boolean match(SignActionEvent info) {
        return info.isType("tcbridge");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void execute(SignActionEvent event) {
        int delay = 10;

        try{
            delay = Math.max(Integer.parseInt(event.getLine(1).split(" ")[1]), 10);
        } catch (Exception ignored) {}

        if(event.isAction(SignActionType.GROUP_ENTER,SignActionType.REDSTONE_ON) && event.isPowered()){
            if(event.hasGroup()){
                Vector velocity = event.getCartEnterDirection().normalize();
                velocity.multiply(event.getGroup().getAverageForce());
                double maxSpeed = event.getGroup().getProperties().getSpeedLimit();

                //Hold Train
                event.getGroup().getActions().clear();
                event.getGroup().getActions().addActionWaitForever();

                int cumaDelay = delay;
                for(MinecartMember m : event.getMembers()){
                    Bukkit.getScheduler().runTaskLater(App.plugin,() -> {
                        Entity ent = m.getEntity().getEntity();

                        Bukkit.getScheduler().runTaskLater(App.plugin,() -> {
                            Conversion.ConvertToVanilla(ent);
                            ent.setVelocity(velocity);
                            ((Minecart) ent).setMaxSpeed(maxSpeed);
                        },5);
                    },cumaDelay);

                    cumaDelay +=20;
                }
            }
        }
    }

    @Override
    public boolean build(SignChangeActionEvent info) {
        SignBuildOptions opt = SignBuildOptions.create().setName("Train_Carts to Vanilla Converter")
                .setDescription("convert Train_Carts cart into its Vanilla variant");

        info.setLine(2, ChatColor.BOLD + "Conversion Sign");
        info.setLine(3, ChatColor.DARK_PURPLE +  "Train_Carts"+ ChatColor.RESET +  " -> " + ChatColor.GREEN + "Vanilla");

        return opt.handle(info.getPlayer());
    }
}
