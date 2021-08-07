package me.hasunemiku2015.tcbridge.HandPlace;

import me.hasunemiku2015.tcbridge.Convert.Conversion;
import me.hasunemiku2015.tcbridge.App;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.Rail;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class HandPlaceEvent implements Listener {
    @EventHandler
    public void onCartPlace(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(!(Objects.requireNonNull(event.getClickedBlock()).getBlockData() instanceof Rail)) {
                return;
            }

            if(!PlayerData.TCAsDefault.contains(event.getPlayer())){
                Location var2 = Objects.requireNonNull(event.getClickedBlock()).getLocation();
                Bukkit.getScheduler().runTaskLater(App.plugin,() -> {
                    for(Entity ent : Objects.requireNonNull(var2.getWorld()).getNearbyEntities(var2,1,1,1)){
                        Conversion.ConvertToVanilla(ent);
                    }
                },1);
            }
        }
    }
}
