package creativeLimiter.ongmswitch.inventory.separate;

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

	private File datafolder;
	private HashMap<String, InventoryStore> playerstore = new HashMap<String, InventoryStore>();

	public InventorySwitch(CreativeLimiter plugin) {
		this.datafolder = new File(plugin.getDataFolder() + File.separator + "inventory" + File.separator);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onGameModeChange(PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPermission("CreativeLimiter.bypass")) {
			GameMode oldgm = player.getGameMode();
			GameMode newgm = event.getNewGameMode();
			String playername = event.getPlayer().getName();
			if (!playerstore.containsKey(event.getPlayer().getName())) {
				playerstore.put(playername, new InventoryStore(playername, datafolder));
			}
			playerstore.get(playername).saveInventory(player, oldgm);
			playerstore.get(playername).loadInventory(player, newgm);
		}
	}

}
