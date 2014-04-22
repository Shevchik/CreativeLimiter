package creativeLimiter.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	private File configfile;
	public Config(CreativeLimiter plugin) {
		configfile = new File(plugin.getDataFolder(), "config.yml");
	}

	public HashSet<Material> restrictedMaterials = new HashSet<Material>();
	public HashSet<String> restrictedCommands = new HashSet<String>();

	public void loadConfig() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configfile);
		restrictedMaterials.clear();
		for (String string : config.getStringList("restrictedMaterials")) {
			Material material = Material.getMaterial(string);
			if (material != null) {
				restrictedMaterials.add(material);
			}
		}
		restrictedCommands = new HashSet<String>(config.getStringList("restrictedCommands"));
	}

	public void saveConfig() {
		YamlConfiguration config = new YamlConfiguration();
		ArrayList<String> list = new ArrayList<String>();
		for (Material material : restrictedMaterials) {
			list.add(material.toString());
		}
		config.set("restrictedMaterials", list);
		config.set("restrictedCommands", new ArrayList<String>(restrictedCommands));
		try {config.save(configfile);} catch (IOException e) {}
	}
	
}
