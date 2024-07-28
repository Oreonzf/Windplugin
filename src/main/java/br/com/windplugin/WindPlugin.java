package br.com.windplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class WindPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new WindchargeListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
