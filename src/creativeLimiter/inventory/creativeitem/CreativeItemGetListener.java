package creativeLimiter.inventory.creativeitem;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class CreativeItemGetListener implements Listener {

	@EventHandler
	public void onCreativeItemGet(InventoryCreativeEvent e) {
		if (!e.getWhoClicked().hasPermission("CreativeLimiter.bypass")) {
			ItemStack item = e.getCursor();
			if (item != null && item.getType() != Material.AIR) {
				e.setCursor(generateCleanItem(item));
			}
		}
	}

	public ItemStack generateCleanItem(ItemStack oldItem) {
		//handle base
		ItemStack newItem = new ItemStack(oldItem.getType());
		newItem.setAmount(oldItem.getAmount());
		newItem.setDurability(oldItem.getDurability());
		//handle books
		if (oldItem.getType() == Material.ENCHANTED_BOOK) {
			//book may contain only 1 enchantment so we just check and apply it if it is valid
			Map<Enchantment, Integer> enchants = oldItem.getEnchantments();
			if (enchants.size() > 0) {
				Enchantment ench = enchants.keySet().iterator().next();
				if (enchants.get(ench) <= ench.getMaxLevel()) {
					newItem.addEnchantment(ench, enchants.get(ench));
				}
			}
		}
		//handle heads
		if (oldItem.getType() == Material.SKULL_ITEM && oldItem.getDurability() == 3) {
			if (oldItem.hasItemMeta()) {
				SkullMeta oldSkullMeta = (SkullMeta) oldItem.getItemMeta();
				if (oldSkullMeta.hasOwner()) {
					SkullMeta newSkullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
					newSkullMeta.setOwner(oldSkullMeta.getOwner());
				}
			}
		}
		//return newly generated item
		return newItem;
	}

}
