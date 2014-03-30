package creativeLimiter.inventory.creativeitem;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpecialEffectsRemover {

	public void removeInvalidEnchants(ItemStack item) {
		for (Enchantment ench : item.getEnchantments().keySet()) {
			if ((item.getEnchantmentLevel(ench) > ench.getMaxLevel()) || (!ench.canEnchantItem(item))) {
				item.removeEnchantment(ench);
			}
		}
		int count = 1;
		for (Enchantment ench : item.getEnchantments().keySet()) {
			if (count > 4) {
				item.removeEnchantment(ench);
			}
			count++;
		}
	}

	public void removeInvalidDisplayName(ItemStack item) {
		if (item.hasItemMeta()) {
			ItemMeta im = item.getItemMeta();
			if (im.hasDisplayName()) {
				if (im.getDisplayName().length() > 32) {
					im.setDisplayName(im.getDisplayName().substring(0, 32));
					item.setItemMeta(im);
				}
			}
		}
	}

	public void removeInvalidLore(ItemStack item) {
		if (item.hasItemMeta()) {
			ItemMeta im = item.getItemMeta();
			if (im.hasLore()) {
				im.setLore(null);
				item.setItemMeta(im);
			}
		}
	}

	public void removeInvalidAttributes(ItemStack item) {
	}

}
