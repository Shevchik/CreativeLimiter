package creativeLimiter.interact;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class HorseRestrict implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		if (!e.getPlayer().hasPermission("CreativeLimiter.bypass")) {
			if (e.getPlayer().getGameMode() == GameMode.CREATIVE && e.getRightClicked().getType() == EntityType.HORSE) {
				Horse horse = (Horse) e.getRightClicked();
				if (horse.isCarryingChest()) {
					e.setCancelled(true);
				}
			}

		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onOpenEntityChest(InventoryOpenEvent e) {
		if (!e.getPlayer().hasPermission("CreativeLimiter.bypass.interact.horse")) {
			if (e.getPlayer().getGameMode() == GameMode.CREATIVE && e.getPlayer().isInsideVehicle() && e.getPlayer().getVehicle() instanceof Horse) {
				Horse horse = (Horse) e.getPlayer().getVehicle();
				if (horse.isCarryingChest()) {
					e.setCancelled(true);
				}
			}

		}
	}
}
