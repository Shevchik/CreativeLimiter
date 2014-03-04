package creativeLimiter.misc.gamemodechanger;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitGamemodeChanger implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (!e.getPlayer().hasPermission("CreativeLimiter.misc.noquitgmchange")) {
			e.getPlayer().setGameMode(GameMode.SURVIVAL);
		}
	}
}
