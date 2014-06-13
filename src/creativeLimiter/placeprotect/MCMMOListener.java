package creativeLimiter.placeprotect;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.fake.FakeBlockBreakEvent;

public class MCMMOListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreak(FakeBlockBreakEvent e) {
		Block block = e.getBlock();
		if (Materials.isMaterialProtected(block.getType()) && block.getData() == 15) {
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
		}
	}

}
