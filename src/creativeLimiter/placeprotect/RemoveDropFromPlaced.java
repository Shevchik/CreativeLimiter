package creativeLimiter.placeprotect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.WorldInitEvent;

import creativeLimiter.core.CreativeLimiter;

public class RemoveDropFromPlaced implements Listener {

	private File saveDataFolder;

	public RemoveDropFromPlaced(CreativeLimiter plugin) {
		saveDataFolder = new File(plugin.getDataFolder(), "worldsdata");
		saveDataFolder.mkdirs();
		for (World world : Bukkit.getWorlds()) {
			protectedBlocks.put(world.getName(), loadBlocks(world));
		}
	}

	private final HashMap<String, HashSet<Block>> protectedBlocks = new HashMap<String, HashSet<Block>>();

	public void saveBlocks() {
		for (Entry<String, HashSet<Block>> entry : protectedBlocks.entrySet()) {
			YamlConfiguration config = new YamlConfiguration();
			ArrayList<String> data = new ArrayList<String>();
			for (Block block : entry.getValue()) {
				data.add(block.getX()+";"+block.getY()+";"+block.getZ());
			}
			config.set("blocks", data);
			try {
				config.save(new File(new File(saveDataFolder, entry.getKey()), "protectedblocks.yml"));
			} catch (IOException e) {
			}
		}
	}

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		World world = event.getWorld();
		protectedBlocks.put(world.getName(), loadBlocks(world));
	}

	private HashSet<Block> loadBlocks(World world) {
		File file = new File(new File(saveDataFolder, world.getName()), "protectedblocks.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		HashSet<Block> worldblocks = new HashSet<Block>();
		for (String blockinfo : config.getStringList("blocks")) {
			String[] split = blockinfo.split("[;]");
			if (split.length == 3) {
				worldblocks.add(world.getBlockAt(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
			}
		}
		return worldblocks;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (!e.getPlayer().hasPermission("CreativeLimiter.bypass")) {
				Block block = e.getBlockPlaced();
				protectedBlocks.get(block.getWorld().getName()).add(block);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPistonExtend(BlockPistonExtendEvent e) {
		HashSet<Block> worldblocks = protectedBlocks.get(e.getBlock().getWorld().getName());
		ArrayList<Block> blocksToAdd = new ArrayList<Block>();
		for (Block block : e.getBlocks()) {
			if (worldblocks.remove(block)) {
				blocksToAdd.add(block);
			}
		}
		for (Block block : blocksToAdd) {
			worldblocks.add(block.getRelative(e.getDirection()));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPistonRetract(BlockPistonRetractEvent e) {
		HashSet<Block> worldblocks = protectedBlocks.get(e.getBlock().getWorld().getName());
		ArrayList<Block> blocksToAdd = new ArrayList<Block>();
		for (Block block : e.getBlocks()) {
			if (worldblocks.remove(block)) {
				blocksToAdd.add(block);
			}
		}
		for (Block block : blocksToAdd) {
			worldblocks.add(block.getRelative(e.getDirection()));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		HashSet<Block> worldblocks = protectedBlocks.get(e.getBlock().getWorld().getName());
		Block block = e.getBlock();
		if (worldblocks.remove(block)) {
			e.setCancelled(true);
			block.setType(Material.AIR);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onExplode(EntityExplodeEvent e) {
		HashSet<Block> worldblocks = protectedBlocks.get(e.getLocation().getWorld().getName());
		List<Block> blocks = e.blockList();
		Iterator<Block> it = blocks.iterator();
		while (it.hasNext()) {
			Block block = it.next();
			if (worldblocks.remove(block)) {
				it.remove();
				block.setType(Material.AIR);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onExplode(BlockExplodeEvent e) {
		HashSet<Block> worldblocks = protectedBlocks.get(e.getBlock().getWorld().getName());
		List<Block> blocks = e.blockList();
		Iterator<Block> it = blocks.iterator();
		while (it.hasNext()) {
			Block block = it.next();
			if (worldblocks.remove(block)) {
				it.remove();
				block.setType(Material.AIR);
			}
		}
	}

}
