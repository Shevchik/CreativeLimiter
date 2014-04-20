package creativeLimiter.restrict;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import creativeLimiter.core.Config;

public class CommandRestrict implements Listener {

	private Config config;
	public CommandRestrict(Config config) {
		this.config = config;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (!event.getPlayer().hasPermission("CreativeLimiter.bypass")) {
				final String[] cmds = event.getMessage().toLowerCase().split("\\s+");
				if (cmds.length > 0 && config.restrictedCommands.contains(cmds[0])) {
					event.setCancelled(true);
				}
			}
		}
	}

}
