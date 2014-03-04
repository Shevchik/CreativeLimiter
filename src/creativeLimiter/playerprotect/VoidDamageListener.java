package creativeLimiter.playerprotect;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class VoidDamageListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onVoidDamage(EntityDamageEvent event) {
		if (event.getCause() == DamageCause.VOID) {
			Entity ent = event.getEntity();
			if (ent instanceof Player) {
				Player player = (Player) ent;
				if (player.hasPermission("CreativeLimiter.bypass")) {
					return;
				}
				if (player.getGameMode() == GameMode.CREATIVE) {
					event.setCancelled(true);
				}
			}
		}
	}

}
