package creativeLimiter.placeprotect;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class RemoveDropFromPlaced implements Listener {

	private HashSet<Material> materials;

	public RemoveDropFromPlaced() {
		materials = new HashSet<Material>(
			Arrays.asList(
				new Material[] {
					Material.DIAMOND_ORE, Material.DIAMOND_BLOCK,
					Material.IRON_ORE, Material.IRON_BLOCK,
					Material.EMERALD_ORE, Material.EMERALD_BLOCK, 
					Material.GOLD_ORE, Material.GOLD_BLOCK
				}
			)
		);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent e) {
		if (!e.getPlayer().hasPermission("CreativeLimiter.bypass")) {
			if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
				Block block = e.getBlockPlaced();
				if (this.materials.contains(block.getType())) {
					block.setData((byte) 15);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		Block block = e.getBlock();
		if (this.materials.contains(block.getType()) && block.getData() == 15) {
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onExplode(EntityExplodeEvent e) {
		List<Block> blocks = e.blockList();
		Iterator<Block> it = blocks.iterator();
		while (it.hasNext()) {
			Block block = it.next();
			if (this.materials.contains(block.getType()) && block.getData() == 15) {
				it.remove();
				block.setType(Material.AIR);
			}
		}
	}

}
