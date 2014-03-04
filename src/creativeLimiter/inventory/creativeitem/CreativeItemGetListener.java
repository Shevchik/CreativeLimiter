package creativeLimiter.inventory.creativeitem;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;

public class CreativeItemGetListener implements Listener {

	private SpecialEffectsRemover eremover = new SpecialEffectsRemover();
	private LoreMarker lmarker = new LoreMarker();
	private String markkeyword = ChatColor.GOLD + "CreativeLimiter";

	@EventHandler
	public void onCreativeItemGet(InventoryCreativeEvent e) {
		if (!e.getWhoClicked().hasPermission("CreativeLimiter.bypass")) {
			ItemStack item = e.getCursor();
			if (item != null && item.getType() != Material.AIR && isNotMarked(item)) {
				eremover.removeInvalidEnchants(item);
				eremover.removeInvalidDisplayName(item);
				eremover.removeInvalidLore(item);
				eremover.removeInvalidAttributes(item);
				lmarker.markItem(e.getWhoClicked().getName(), item, markkeyword);
			}
		}
	}

	private boolean isNotMarked(ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
			List<String> lore = item.getItemMeta().getLore();
			if (lore.contains(markkeyword)) {
				return false;
			}
		}
		return true;
	}

}
