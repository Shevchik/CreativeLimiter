package creativeLimiter.restrict;

import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EntityDamageRestrict implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Player damager = null;
		Entity edamager = event.getDamager();
		if (edamager instanceof Player) {
			damager = (Player) edamager;
		} else if (edamager instanceof Arrow) {
			ProjectileSource source = ((Arrow) edamager).getShooter();
			if (source instanceof Player) {
				damager = (Player) source;
			}
		}
		if (damager != null && damager.getGameMode() == GameMode.CREATIVE) {
			if (!damager.hasPermission("CreativeLimiter.bypass")) {
				event.setCancelled(true);
			}
		}
	}

}
