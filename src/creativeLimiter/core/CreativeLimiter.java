package creativeLimiter.core;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import creativeLimiter.creativeitems.CreativeItemListener;
import creativeLimiter.inventory.deathclear.DeathListener;
import creativeLimiter.inventory.separate.InventorySwitch;
import creativeLimiter.misc.JoinGamemodeChanger;
import creativeLimiter.placeprotect.RemoveDropFromPlaced;
import creativeLimiter.playerprotect.VoidDamageListener;
import creativeLimiter.restrict.BedrockBreakRestrict;
import creativeLimiter.restrict.CommandRestrict;
import creativeLimiter.restrict.DropRestrict;
import creativeLimiter.restrict.EntityBuildRestrict;
import creativeLimiter.restrict.EntityDamageRestrict;
import creativeLimiter.restrict.EntityUseResrict;
import creativeLimiter.restrict.ItemUseRestrict;
import creativeLimiter.restrict.InvOpenRestrict;
import creativeLimiter.restrict.PickupRestrict;

public class CreativeLimiter extends JavaPlugin {

	private Config config;

	private RemoveDropFromPlaced removeDropFromPlaced;
	private InventorySwitch invswitch;

	@Override
	public void onEnable() {
		//config
		config = new Config(this);
		config.loadConfig();
		//commands
		getCommand("creativelimiter").setExecutor(new Commands(config));
		//get pluginmanager to write less code
		PluginManager pm = getServer().getPluginManager();
		//inv
		invswitch = new InventorySwitch(this);
		invswitch.load();
		pm.registerEvents(invswitch, this);
		//death
		pm.registerEvents(new DeathListener(), this);
		//restrict
		pm.registerEvents(new EntityDamageRestrict(), this);
		pm.registerEvents(new InvOpenRestrict(), this);
		pm.registerEvents(new DropRestrict(), this);
		pm.registerEvents(new PickupRestrict(), this);
		pm.registerEvents(new ItemUseRestrict(config), this);
		pm.registerEvents(new CommandRestrict(config), this);
		pm.registerEvents(new BedrockBreakRestrict(), this);
		pm.registerEvents(new EntityBuildRestrict(), this);
		pm.registerEvents(new EntityUseResrict(), this);
		//block nodrop
		removeDropFromPlaced = new RemoveDropFromPlaced(this);
		pm.registerEvents(removeDropFromPlaced, this);
		//creative items checker
		pm.registerEvents(new CreativeItemListener(), this);
		//void damage protection
		pm.registerEvents(new VoidDamageListener(), this);
		//gamemode changer
		pm.registerEvents(new JoinGamemodeChanger(this), this);
	}

	@Override
	public void onDisable() {
		removeDropFromPlaced.saveBlocks();
		invswitch.save();
		config = null;
	}

}
