package creativeLimiter.restrict;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import creativeLimiter.core.Config;

public class ItemUseRestrict implements Listener {

	private Config config;
	public ItemUseRestrict(Config config) {
		this.config = config;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onJukeBoxInteract(PlayerInteractEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.JUKEBOX) {
				if (!event.getPlayer().hasPermission("CreativeLimiter.bypass")) {
					event.setCancelled(true);
				}
			}
		}
	}


	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockInteract(PlayerInteractEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (config.restrictedMaterials.contains(event.getPlayer().getItemInHand().getType())) {
				if (!event.getPlayer().hasPermission("CreativeLimiter.bypass")) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (config.restrictedMaterials.contains(event.getPlayer().getItemInHand().getType())) {
				if (!event.getPlayer().hasPermission("CreativeLimiter.bypass")) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (config.restrictedMaterials.contains(event.getBlockPlaced().getType())) {
				if (!event.getPlayer().hasPermission("CreativeLimiter.bypass")) {
					event.setCancelled(true);
				}
			}
		}
	}

}
