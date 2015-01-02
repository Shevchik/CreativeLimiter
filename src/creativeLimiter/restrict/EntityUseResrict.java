package creativeLimiter.restrict;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityUseResrict implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onFrameUse(PlayerInteractEntityEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (!e.getPlayer().hasPermission("CreativeLimiter.bypass")) {
				if (e.getRightClicked() instanceof ItemFrame) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onArmorStandUse(PlayerInteractAtEntityEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (!e.getPlayer().hasPermission("CreativeLimiter.bypass")) {
				if (e.getRightClicked() instanceof ArmorStand) {
					e.setCancelled(true);
				}
			}
		}
	}

}
