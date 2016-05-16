package creativeLimiter.restrict;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import creativeLimiter.core.Config;

public class ItemUseRestrict implements Listener {

	private Config config;
	public ItemUseRestrict(Config config) {
		this.config = config;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onJukeBoxInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("CreativeLimiter.bypass")) {
			return;
		}
		if (player.getGameMode() == GameMode.CREATIVE) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.JUKEBOX) {
				event.setCancelled(true);
			}
		}
	}


	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("CreativeLimiter.bypass")) {
			return;
		}
		if (player.getGameMode() == GameMode.CREATIVE) {
			if (config.restrictedMaterials.contains(getMaterialOrNull(event.getItem()))) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("CreativeLimiter.bypass")) {
			return;
		}
		if (player.getGameMode() == GameMode.CREATIVE) {
			Material clicked = Material.AIR;
			if (event.getHand() == EquipmentSlot.HAND) {
				clicked = getMaterialOrNull(player.getInventory().getItemInMainHand());
			} else {
				clicked = getMaterialOrNull(player.getInventory().getItemInOffHand());
			}
			if (config.restrictedMaterials.contains(clicked)) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("CreativeLimiter.bypass")) {
			return;
		}
		if (player.getGameMode() == GameMode.CREATIVE) {
			if (config.restrictedMaterials.contains(event.getBlockPlaced().getType())) {
				event.setCancelled(true);
			}
		}
	}

	private Material getMaterialOrNull(ItemStack itemstack) {
		return itemstack != null ? itemstack.getType() : null;
	}

}
