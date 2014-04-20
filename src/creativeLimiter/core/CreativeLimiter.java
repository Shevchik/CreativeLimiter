package creativeLimiter.core;

import org.bukkit.plugin.java.JavaPlugin;

import creativeLimiter.core.misc.JoinGamemodeChanger;
import creativeLimiter.inventory.creativeitem.CreativeItemGetListener;
import creativeLimiter.ongmswitch.inventory.close.InventoryClose;
import creativeLimiter.ongmswitch.inventory.separate.InventorySwitch;
import creativeLimiter.placeprotect.RemoveDropFromPlaced;
import creativeLimiter.playerprotect.VoidDamageListener;
import creativeLimiter.restrict.invopen.InvOpenListener;

public class CreativeLimiter extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new InventorySwitch(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClose(), this);
		getServer().getPluginManager().registerEvents(new InvOpenListener(), this);
		getServer().getPluginManager().registerEvents(new RemoveDropFromPlaced(), this);
		getServer().getPluginManager().registerEvents(new CreativeItemGetListener(), this);
		getServer().getPluginManager().registerEvents(new VoidDamageListener(), this);
		getServer().getPluginManager().registerEvents(new JoinGamemodeChanger(this), this);
	}

}
