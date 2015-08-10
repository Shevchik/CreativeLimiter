package creativeLimiter.creativeitems;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;

public class CreativeItemListener implements Listener {

	@EventHandler(ignoreCancelled=true)
	public void onCreativeItem(InventoryCreativeEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (player.hasPermission("creativeitemscleaner.ignore")) {
			return;
		}
		ItemStack item = event.getCursor();
		if (item != null) {
			event.setCursor(CreativeItems.generateCleanItem(item));
		}
	}

}
