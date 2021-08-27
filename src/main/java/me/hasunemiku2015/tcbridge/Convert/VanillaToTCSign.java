package me.hasunemiku2015.tcbridge.Convert;

import java.util.Objects;

import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.properties.TrainProperties;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

public class VanillaToTCSign implements Listener {
    @EventHandler
    public void onConversionSignBuild(SignChangeEvent event) {
        if (Objects.requireNonNull(event.getLine(0)).equalsIgnoreCase("[TC-Bridge]")) {
            event.setLine(0, "[TC-Bridge]");
            event.setLine(1, "-----------------------");
            event.setLine(2, ChatColor.BOLD + "Conversion Sign");
            event.setLine(3,
                    ChatColor.GREEN + "Vanilla" + ChatColor.RESET + " -> " + ChatColor.DARK_PURPLE + "Train_Carts");
        }
    }

    @EventHandler
    @SuppressWarnings("rawtypes")
    public void onCartConvert(VehicleMoveEvent event) {
        if (!(event.getVehicle() instanceof Minecart)) return;

        for (int x = -1; x < 2; x++) {
            for (int y = -2; y < 3; y++) {
                for (int z = -1; z < 2; z++) {
                    Location loc = event.getVehicle().getLocation();
                    loc.add(new Vector(x, y, z));
                    if (!(loc.getBlock().getState() instanceof Sign)) continue;

                    if (((Sign) loc.getBlock().getState()).getLine(0).equalsIgnoreCase("[TC-Bridge]")) {
                        double Maxspeed = ((Minecart) event.getVehicle()).getMaxSpeed();
                        Vector Velocity = event.getVehicle().getVelocity();

                        MinecartMember mem = Conversion.ConvertToTrainCarts(event.getVehicle());
                        TrainProperties prop = mem.getGroup().getProperties();
                        prop.setSpeedLimit(Maxspeed);
                        mem.getGroup().setProperties(prop);
                        mem.getGroup().setForwardForce(Velocity.length());
                    }
                }
            }
        }
    }
}
