package creativeLimiter.placeprotect;

import java.util.Arrays;
import java.util.HashSet;

import org.bukkit.Material;

public class Materials {
	
	private static HashSet<Material> materials = new HashSet<Material>(
		Arrays.asList(
			new Material[] {
				Material.DIAMOND_ORE, Material.DIAMOND_BLOCK,
				Material.IRON_ORE, Material.IRON_BLOCK,
				Material.EMERALD_ORE, Material.EMERALD_BLOCK, 
				Material.GOLD_ORE, Material.GOLD_BLOCK,
				Material.REDSTONE_ORE, Material.GLOWING_REDSTONE_ORE, Material.REDSTONE_BLOCK,
				Material.COAL_ORE, Material.COAL_BLOCK,
				Material.LAPIS_ORE, Material.LAPIS_BLOCK
			}
		)
	);

	public static boolean isMaterialProtected(Material mat) {
		return materials.contains(mat);
	}

}
