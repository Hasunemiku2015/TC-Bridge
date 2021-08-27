package me.hasunemiku2015.tcbridge.Convert;

import com.bergerkiller.bukkit.common.controller.DefaultEntityController;
import com.bergerkiller.bukkit.common.controller.EntityNetworkController;
import com.bergerkiller.bukkit.common.entity.CommonEntity;
import com.bergerkiller.bukkit.tc.controller.MinecartMember;
import com.bergerkiller.bukkit.tc.controller.MinecartMemberStore;
import com.bergerkiller.bukkit.tc.properties.CartProperties;
import me.hasunemiku2015.tcbridge.App;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Conversion {
    @SuppressWarnings("rawtypes")
    public static void ConvertToVanilla(Entity ent){
        if(CommonEntity.get(ent).getController() instanceof MinecartMember){
            CommonEntity.get(ent).setController(new DefaultEntityController());
            CommonEntity.get(ent).setNetworkController(new EntityNetworkController() {});

            ent.setVelocity(new Vector(0,0,0));
        }
    }

    @SuppressWarnings("rawtypes")
    public static MinecartMember ConvertToTrainCarts(Entity entity){
        if(entity instanceof Minecart){
            Minecart m = (Minecart) entity;
            MinecartMember mem = MinecartMemberStore.convert(m);

            if(!entity.getPassengers().isEmpty()){
                if(entity.getPassengers().get(0) instanceof Player){
                    Bukkit.getScheduler().runTaskLater(App.plugin,() -> {
                        if(entity.getPassengers().size() > 0 && entity.getPassengers().get(0) instanceof Player)
                            CartProperties.setEditing((Player) entity.getPassengers().get(0),mem.getProperties());
                    },1);
                }
            }
            return mem;
        }
        return null;
    }
}
