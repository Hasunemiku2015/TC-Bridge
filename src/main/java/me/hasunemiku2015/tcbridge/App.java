package me.hasunemiku2015.tcbridge;

import me.hasunemiku2015.tcbridge.Convert.ConvertCmd;
import me.hasunemiku2015.tcbridge.Convert.TCSignToggler;
import me.hasunemiku2015.tcbridge.Convert.VanillaToTCSign;
import me.hasunemiku2015.tcbridge.HandPlace.HandPlaceEvent;
import me.hasunemiku2015.tcbridge.HandPlace.PlayerData;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class App extends JavaPlugin {
    public static App plugin;

    @Override
    public void onEnable() {
        plugin = this;
        PluginManager pm = plugin.getServer().getPluginManager();

        pm.registerEvents(new HandPlaceEvent(), this);
        Objects.requireNonNull(plugin.getServer().getPluginCommand("place")).setExecutor(new PlayerData());
        Objects.requireNonNull(plugin.getServer().getPluginCommand("convert")).setExecutor(new ConvertCmd());

        pm.registerEvents(new VanillaToTCSign(),this);
        TCSignToggler.init();

        plugin.getLogger().info("TC-Bridge is Loaded");
    }

    @Override
    public void onDisable(){
        TCSignToggler.deinit();
    }
}
