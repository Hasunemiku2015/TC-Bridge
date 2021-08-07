package me.hasunemiku2015.tcbridge.Convert;

import com.bergerkiller.bukkit.tc.signactions.SignAction;

public class TCSignToggler {
    //Init and Deinits
    private static final TCToVanillaSign local = new TCToVanillaSign();
    public static void init(){
        SignAction.register(local);
    }
    public static void deinit(){
        SignAction.unregister(local);
    }
}
