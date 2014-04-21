package creativeLimiter.inventory.deathclear;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDeath(PlayerDeathEvent event) {
		if (event.getEntity().getGameMode() == GameMode.CREATIVE) {
			if (!event.getEntity().hasPermission("CreativeLimiter.bypass")) {
				event.getDrops().clear();
			}
		}
	}

}
