package creativeLimiter.core.misc;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import creativeLimiter.core.CreativeLimiter;
import fr.xephi.authme.events.LoginEvent;

public class JoinGamemodeChanger implements Listener {

	private CreativeLimiter plugin;
	public JoinGamemodeChanger(CreativeLimiter plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled=true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (Bukkit.getPluginManager().getPlugin("AuthMe") != null) {
			return;
		}
		processPlayer(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled=true)
	public void onLogin(LoginEvent event) {
		processPlayer(event.getPlayer());
	}

	private void processPlayer(final Player player) {
		if (player.getGameMode() == GameMode.SURVIVAL) {
			return;
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(
			plugin, 
			new Runnable() {
				@Override
				public void run() {
					if (!player.hasPermission("CreativeLimiter.keepgamemode")) {
						player.setGameMode(GameMode.SURVIVAL);
					}
				}
			}
		);
	}

}
