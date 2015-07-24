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

	private final File playerfile;
	private final HashMap<GameMode, ItemStack[]> invdata = new HashMap<GameMode, ItemStack[]>();
	private final HashMap<GameMode, ItemStack[]> armordata = new HashMap<GameMode, ItemStack[]>();

	public InventoryStore(String playername, File datafolder) {
		this.playerfile = new File(datafolder, playername);
	}

	@SuppressWarnings("unchecked")
	public void load() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(playerfile);
		for (String gm : config.getKeys(false)) {
			List<ItemStack> inv = new ArrayList<ItemStack>();
			if (config.contains(gm.toString() + ".inv")) {
				inv = (List<ItemStack>) config.get(gm + ".inv");
			}
			invdata.put(GameMode.valueOf(gm), inv.toArray(new ItemStack[36]));
			List<ItemStack> armor = new ArrayList<ItemStack>();
			if (config.contains(gm.toString() + ".armor")) {
				armor = (List<ItemStack>) config.get(gm + ".armor");
			}
			armordata.put(GameMode.valueOf(gm), armor.toArray(new ItemStack[4]));
		}
	}

	public void save() {
		YamlConfiguration config = new YamlConfiguration();
		for (GameMode gm : GameMode.values()) {
			if (invdata.containsKey(gm) && armordata.containsKey(gm)) {
				config.set(gm.toString()+".inv", Arrays.asList(invdata.get(gm)));
				config.set(gm.toString()+".armor", Arrays.asList(armordata.get(gm)));
			}
		}
		try {
			config.save(playerfile);
		} catch (IOException e) {
		}
	}

	public void switchInventory(Player player, GameMode oldgm, GameMode newgm) {
		invdata.put(oldgm, player.getInventory().getContents());
		armordata.put(oldgm, player.getInventory().getArmorContents());
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[4]);
		if (invdata.containsKey(newgm) && armordata.containsKey(newgm)) {
			player.getInventory().setContents(invdata.get(newgm));
			player.getInventory().setArmorContents(armordata.get(newgm));
		}
	}

}
