package creativeLimiter.restrict;

import org.bukkit.GameMode;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class FrameUseResrict implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onFrameUse(PlayerInteractEntityEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (!event.getPlayer().hasPermission("CreativeLimiter.bypass")) {
				if (event.getRightClicked() instanceof ItemFrame) {
					event.setCancelled(true);
				}
			}
		}
	}
	
}
