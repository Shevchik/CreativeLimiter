package creativeLimiter.ongmswitch.inventory.close;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class InventoryClose implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onGameModeChange(PlayerGameModeChangeEvent event) {
		if (!event.getPlayer().hasPermission("CreativeLimiter.bypass")) {
			event.getPlayer().closeInventory();
		}
	}

}
