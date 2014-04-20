package creativeLimiter.core;

import org.bukkit.plugin.java.JavaPlugin;

import creativeLimiter.core.misc.JoinGamemodeChanger;
import creativeLimiter.inventory.creativeitem.CreativeItemGetListener;
import creativeLimiter.ongmswitch.inventory.close.InventoryClose;
import creativeLimiter.ongmswitch.inventory.separate.InventorySwitch;
import creativeLimiter.placeprotect.RemoveDropFromPlaced;
import creativeLimiter.playerprotect.VoidDamageListener;
import creativeLimiter.restrict.DropRestrict;
import creativeLimiter.restrict.EntityDamageRestrict;
import creativeLimiter.restrict.InteractRestrict;
import creativeLimiter.restrict.InvOpenRestrict;

public class CreativeLimiter extends JavaPlugin {

	private Config config;

	@Override
	public void onEnable() {
		//config
		config = new Config(this);
		config.loadConfig();
		//inv
		getServer().getPluginManager().registerEvents(new InventorySwitch(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClose(), this);
		//restrict
		getServer().getPluginManager().registerEvents(new EntityDamageRestrict(), this);
		getServer().getPluginManager().registerEvents(new InvOpenRestrict(), this);
		getServer().getPluginManager().registerEvents(new DropRestrict(), this);
		getServer().getPluginManager().registerEvents(new InteractRestrict(config), this);
		//block nodrop
		getServer().getPluginManager().registerEvents(new RemoveDropFromPlaced(), this);
		//creative items checker
		getServer().getPluginManager().registerEvents(new CreativeItemGetListener(), this);
		//void damage protection
		getServer().getPluginManager().registerEvents(new VoidDamageListener(), this);
		//gamemode changer
		getServer().getPluginManager().registerEvents(new JoinGamemodeChanger(this), this);
	}

	@Override
	public void onDisable() {
		config = null;
	}

}
