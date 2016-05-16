package creativeLimiter.inventory.separate;

import java.io.File;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import creativeLimiter.core.CreativeLimiter;

public class InventorySwitch implements Listener {

	private final File datafolder;
	private final HashMap<String, InventoryStore> playerstore = new HashMap<String, InventoryStore>();

	public InventorySwitch(CreativeLimiter plugin) {
		this.datafolder = new File(plugin.getDataFolder(), "inventory");
	}

	public void load() {
		File[] files = datafolder.listFiles();
		if (files != null) {
			for (File file : files) {
				InventoryStore store = new InventoryStore(file.getName(), datafolder);
				store.load();
				playerstore.put(file.getName(), store);
			}
		}
	}

	public void save() {
		for (InventoryStore store : playerstore.values()) {
			store.save();
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onGameModeChange(PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPermission("CreativeLimiter.bypass")) {
			event.getPlayer().closeInventory();
			GameMode oldgm = player.getGameMode();
			GameMode newgm = event.getNewGameMode();
			String playername = event.getPlayer().getName();
			if (!playerstore.containsKey(event.getPlayer().getName())) {
				playerstore.put(playername, new InventoryStore(playername, datafolder));
			}
			playerstore.get(playername).switchInventory(player, oldgm, newgm);
		}
	}

}
