package creativeLimiter.inventory.creativeitem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LoreMarker {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public void markItem(String playername, ItemStack item, String keyword) {
		ItemMeta imeta = Bukkit.getItemFactory().getItemMeta(item.getType());
		List<String> lore = new ArrayList<String>();
		if (item.hasItemMeta()) {
			imeta = item.getItemMeta();
			if (imeta.hasLore()) {
				lore.addAll(imeta.getLore());
			}
		}
		lore.add(keyword);
		lore.add(ChatColor.GOLD + "This item is created");
		lore.add(ChatColor.GOLD + "by " + playername);
		lore.add(ChatColor.GOLD + "at " + sdf.format(Long.valueOf(System.currentTimeMillis())));
		imeta.setLore(lore);
		item.setItemMeta(imeta);
	}

}
