package creativeLimiter.creativeitems;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class CreativeItemsCleaner {

	//Generates new itemstack based on the properties of the old
	public static ItemStack generateCleanItem(ItemStack oldItem) {
		//handle base
		ItemStack newItem = new ItemStack(oldItem.getType());
		//handle possibly invalid count (never seen this, but who knows what can possibly happen?)
		if (oldItem.getAmount() > oldItem.getMaxStackSize()) {
			newItem.setAmount(oldItem.getMaxStackSize());
		} else {
			newItem.setAmount(oldItem.getAmount());
		}
		newItem.setDurability(oldItem.getDurability());
		//handle books
		if (oldItem.getType() == Material.ENCHANTED_BOOK) {
			//handle enchants
			if (oldItem.hasItemMeta()) {
				EnchantmentStorageMeta oldEnchBookMeta = (EnchantmentStorageMeta) oldItem.getItemMeta();
				if (oldEnchBookMeta.hasStoredEnchants()) {
					//just add the first found enchants if it is valid
					Map<Enchantment, Integer> enchants = oldEnchBookMeta.getStoredEnchants();
					if (enchants.size() > 0) {
						Entry<Enchantment, Integer> entry = enchants.entrySet().iterator().next();
						if (entry.getValue() <= entry.getKey().getMaxLevel()) {
							EnchantmentStorageMeta newEnchBookMeta = (EnchantmentStorageMeta) Bukkit.getItemFactory().getItemMeta(Material.ENCHANTED_BOOK);
							newEnchBookMeta.addStoredEnchant(entry.getKey(), entry.getValue(), false);
							newItem.setItemMeta(newEnchBookMeta);
						}
					}
				}
			}
			//now handle name
			ItemMeta newItemMeta = null;
			if (newItem.hasItemMeta()) {
				newItemMeta = newItem.getItemMeta();
			} else {
				newItemMeta = Bukkit.getItemFactory().getItemMeta(Material.ENCHANTED_BOOK);
			}
			newItemMeta.setDisplayName(ChatColor.YELLOW + "Enchanted Book");
			newItem.setItemMeta(newItemMeta);
		}
		//handle banners
		else if (oldItem.getType() == Material.BANNER) {
			if (oldItem.hasItemMeta()) {
				DyeColor color = ((BannerMeta) oldItem.getItemMeta()).getBaseColor();
				BannerMeta newBannerMeta = (BannerMeta) Bukkit.getItemFactory().getItemMeta(Material.BANNER);
				newBannerMeta.setBaseColor(color);
				newItem.setItemMeta(newBannerMeta);
			}
		}
		//return newly generated item
		return newItem;
	}

}
