package creativeLimiter.ongmswitch.inventory.separate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryStore {

	private File playerfile;
	private HashMap<GameMode, List<ItemStack>> invcache = new HashMap<GameMode, List<ItemStack>>();
	private HashMap<GameMode, List<ItemStack>> armorcache = new HashMap<GameMode, List<ItemStack>>();

	public InventoryStore(String playername, File datafolder) {
		this.playerfile = new File(datafolder, playername);
	}

	public void saveInventory(Player player, GameMode oldgm) {
		List<ItemStack> inv = Arrays.asList(player.getInventory().getContents());
		List<ItemStack> armor = Arrays.asList(player.getInventory().getArmorContents());
		invcache.put(oldgm, inv);
		armorcache.put(oldgm, armor);
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[4]);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(playerfile);
		config.set(oldgm.toString() + ".inv", inv);
		config.set(oldgm.toString() + ".armor", armor);
		try {config.save(playerfile);} catch (IOException e) {}
	}

	@SuppressWarnings("unchecked")
	public void loadInventory(Player player, GameMode newgm) {
		if (!invcache.containsKey(newgm) || !armorcache.containsKey(newgm)) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(playerfile);
			List<ItemStack> inv = new ArrayList<ItemStack>();
			if (config.contains(newgm.toString() + ".inv")) {
				inv = (List<ItemStack>) config.get(newgm.toString() + ".inv");
			}
			invcache.put(newgm, inv);
			List<ItemStack> armor = new ArrayList<ItemStack>();
			if (config.contains(newgm.toString() + ".armor")) {
				armor = (List<ItemStack>) config.get(newgm.toString() + ".armor");
			}
			armorcache.put(newgm, armor);
		}
		ItemStack[] inv = invcache.get(newgm).toArray(new ItemStack[invcache.get(newgm).size()]);
		ItemStack[] armor = armorcache.get(newgm).toArray(new ItemStack[armorcache.get(newgm).size()]);
		player.getInventory().setContents(inv);
		player.getInventory().setArmorContents(armor);
	}

}
