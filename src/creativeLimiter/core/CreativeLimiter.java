package creativeLimiter.core;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import creativeLimiter.inventory.creativeitem.CreativeItemGetListener;
import creativeLimiter.inventory.deathclear.DeathListener;
import creativeLimiter.misc.JoinGamemodeChanger;
import creativeLimiter.ongmswitch.inventory.close.InventoryClose;
import creativeLimiter.ongmswitch.inventory.separate.InventorySwitch;
import creativeLimiter.placeprotect.RemoveDropFromPlaced;
import creativeLimiter.playerprotect.VoidDamageListener;
import creativeLimiter.restrict.BedrockBreakRestrict;
import creativeLimiter.restrict.CommandRestrict;
import creativeLimiter.restrict.DropRestrict;
import creativeLimiter.restrict.EntityBuildRestrict;
import creativeLimiter.restrict.EntityDamageRestrict;
import creativeLimiter.restrict.FrameUseResrict;
import creativeLimiter.restrict.InteractRestrict;
import creativeLimiter.restrict.InvOpenRestrict;

public class CreativeLimiter extends JavaPlugin {

	private Config config;

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
		pm.registerEvents(new InventorySwitch(this), this);
		pm.registerEvents(new InventoryClose(), this);
		//death
		pm.registerEvents(new DeathListener(), this);
		//restrict
		pm.registerEvents(new EntityDamageRestrict(), this);
		pm.registerEvents(new InvOpenRestrict(), this);
		pm.registerEvents(new DropRestrict(), this);
		pm.registerEvents(new InteractRestrict(config), this);
		pm.registerEvents(new CommandRestrict(config), this);
		pm.registerEvents(new BedrockBreakRestrict(), this);
		pm.registerEvents(new EntityBuildRestrict(), this);
		pm.registerEvents(new FrameUseResrict(), this);
		//block nodrop
		pm.registerEvents(new RemoveDropFromPlaced(), this);
		//creative items checker
		pm.registerEvents(new CreativeItemGetListener(), this);
		//void damage protection
		pm.registerEvents(new VoidDamageListener(), this);
		//gamemode changer
		pm.registerEvents(new JoinGamemodeChanger(this), this);
	}

	@Override
	public void onDisable() {
		config = null;
	}

}
