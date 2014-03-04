package creativeLimiter.ongmswitch.inventory.close;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class InventoryClose implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onGameModeChange(PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPermission("CreativeLimiter.bypass")) {
			player.closeInventory();
		}
	}

}
