package br.com.windplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class WindPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        WindChargeListener listener = new WindChargeListener(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getLogger().info("WindPlugin has been enabled!");
        getLogger().info("Loaded configuration: ");
        getLogger().info("Explosion power: " + getConfig().getDouble("explosion-power", 8.0));
        getLogger().info("Projectile speed: " + getConfig().getDouble("projectile-speed", 1.0));
        getLogger().info("Enable particles: " + getConfig().getBoolean("enable-particles", true));
    }

    @Override
    public void onDisable() {
        getLogger().info("WindPlugin disabled!");
    }
}
