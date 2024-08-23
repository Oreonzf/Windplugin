package br.com.windplugin;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WindChargeListener implements Listener {

    private final JavaPlugin plugin;
    private double explosionPower;
    private double projectileSpeed;
    private double particleScale;
    private final boolean enableParticles;

    public WindChargeListener(JavaPlugin plugin) {
        this.plugin = plugin;
        reloadConfig();
        this.enableParticles = plugin.getConfig().getBoolean("enable-particles", true);
        plugin.getLogger().info("WindChargeListener initialized");
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        this.explosionPower = config.getDouble("explosion-power", 8.0);
        this.projectileSpeed = config.getDouble("projectile-speed", 4.0);
        this.particleScale = config.getDouble("particle-scale", 1.0); // Default scale is 1.0
        plugin.getLogger().info("Config reloaded: Explosion power set to: " + this.explosionPower);
        plugin.getLogger().info("Config reloaded: Projectile speed set to: " + this.projectileSpeed);
        plugin.getLogger().info("Config reloaded: Particle scale set to: " + this.particleScale);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();

        plugin.getLogger().info("Player interact event triggered. Action: " + action);
        if (item != null && item.getType() == Material.WIND_CHARGE) {
            plugin.getLogger().info("Player is holding a Wind Charge.");
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true); // Cancel the default event

                WindCharge windCharge = player.launchProjectile(WindCharge.class);
                if (windCharge != null) {
                    windCharge.setCustomName("wind_charge");
                    Vector direction = player.getLocation().getDirection().normalize();
                    Vector velocity = direction.multiply(projectileSpeed);
                    windCharge.setVelocity(velocity);

                    plugin.getLogger().info("Wind Charge created. Custom name: " + windCharge.getCustomName());
                    plugin.getLogger().info("Wind Charge initial velocity set to: " + velocity);

                    // Remove one Wind Charge from the player's inventory
                    if (item.getAmount() > 1) {
                        item.setAmount(item.getAmount() - 1);
                    } else {
                        player.getInventory().setItemInMainHand(null);
                    }
                } else {
                    plugin.getLogger().info("Failed to launch Wind Charge.");
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();

        if (projectile instanceof WindCharge) {
            plugin.getLogger().info("Wind Charge hit something. Location: " + projectile.getLocation());
            plugin.getLogger().info("Projectile type: " + projectile.getClass().getSimpleName());
            plugin.getLogger().info("Projectile custom name: " + projectile.getCustomName());
            plugin.getLogger().info("Projectile velocity at impact: " + projectile.getVelocity());

            if (enableParticles) {
                // Custom particle effect with adjustable scale
                for (int i = 0; i < 10 * particleScale; i++) {
                    projectile.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, projectile.getLocation(), 0, (Math.random() - 0.5) * particleScale, (Math.random() - 0.5) * particleScale, (Math.random() - 0.5) * particleScale, 0.1);
                }
            }

            plugin.getLogger().info("Creating explosion with power: " + explosionPower);
            projectile.getWorld().createExplosion(projectile.getLocation(), (float) explosionPower, false, false);

            projectile.remove();
        }
    }
}
