package creativeLimiter.inventory.creativeitem;

import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class SpecialEffectsRemover {

	public int getValidSum() {
		return 4;
	}

	public int isEnchantsValid(ItemStack item) {
		Map<Enchantment, Integer> enchants = item.getEnchantments();
		if (enchants.size() > 4) {
			return 0;
		}
		for (Enchantment ench : enchants.keySet()) {
			if ((enchants.get(ench) > ench.getMaxLevel()) || (!ench.canEnchantItem(item))) {
				return 0;
			}
		}
		return 1;
	}

	public int isDisplayNameValid(ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().length() > 32) {
			return 0;
		}
		return 1;
	}

	public int isLoreValid(ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
			return 0;
		}
		return 1;
	}

	public int isAttributesValid(ItemStack item) {
		return 1;
	}

}
