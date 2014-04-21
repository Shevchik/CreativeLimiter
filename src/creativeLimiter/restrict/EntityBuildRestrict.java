package creativeLimiter.restrict;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class EntityBuildRestrict implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (!event.getPlayer().hasPermission("CreativeLimiter.bypass")) {
				Block b = event.getBlockPlaced();
				World world = b.getWorld();
				//block witherboss creation
				if (b.getType() == Material.SKULL) {
					if (
						world.getBlockAt(b.getX(), b.getY() - 1, b.getZ()).getType() == Material.SOUL_SAND &&
						world.getBlockAt(b.getX(), b.getY() - 2, b.getZ()).getType() == Material.SOUL_SAND &&
						world.getBlockAt(b.getX() + 1, b.getY() - 1, b.getZ()).getType() == Material.SOUL_SAND &&
						world.getBlockAt(b.getX() - 1, b.getY() - 1, b.getZ()).getType() == Material.SOUL_SAND ||
						world.getBlockAt(b.getX(), b.getY() - 1, b.getZ() - 1).getType() == Material.SOUL_SAND &&
						world.getBlockAt(b.getX(), b.getY() - 1, b.getZ() + 1).getType() == Material.SOUL_SAND
					) {
						event.setCancelled(true);
						return;
					}
				}
				//block snowgolem creation
				if (b.getType() == Material.PUMPKIN || b.getType() == Material.JACK_O_LANTERN) {
					if (
						world.getBlockAt(b.getX(), b.getY() - 1, b.getZ()).getType() == Material.SNOW_BLOCK &&
						world.getBlockAt(b.getX(), b.getY() - 2, b.getZ()).getType() == Material.SNOW_BLOCK
					) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

}
