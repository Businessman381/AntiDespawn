package businessman381.antidespawn;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiDespawn extends JavaPlugin implements Listener {
	
	private NbtWrapper nbtWrapper;
	private int i = 1;
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		if (event.getEntity() instanceof Player) {
			
			nbtWrapper = new NbtWrapper();
			ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
			for (ItemStack drop : event.getDrops())
				drops.add(drop);
			event.getDrops().clear();
			
			for (ItemStack item : drops) {
				Bukkit.broadcastMessage(item.toString());
				ItemStack nbtItem = nbtWrapper.setNbtTag(i, "AntiDespawn", item);
				event.getDrops().add(nbtItem);
			}
			drops.clear();
			
		}
		
	}
	
	@EventHandler
	public void onDespawn(ItemDespawnEvent event) {
		
		ItemStack item = event.getEntity().getItemStack();
		
		try {
			String nbtTag = nbtWrapper.getNbtTag(i, item);
			if (nbtTag.equals("AntiDespawn")) {
				event.setCancelled(true);	
			}
		} catch (NullPointerException ex) {}
		
	}
	
	@EventHandler
	public void onPickUp(EntityPickupItemEvent event) {
		
		if (event.getEntity() instanceof Player) {
			
			Player p = (Player) event.getEntity();
			
			try {
				ItemStack nbtItem = event.getItem().getItemStack();
				nbtWrapper = new NbtWrapper();
				if (nbtWrapper.getNbtTag(i, nbtItem).equals("AntiDespawn")) {
					removeAllNbt(p.getInventory());
				}
			} catch (NullPointerException ex) {}
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void removeAllNbt(Inventory inv) {
		
		Bukkit.getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				int x = 0;
				for (ItemStack invItem : inv.getStorageContents()) {
					try {
						if (nbtWrapper.getNbtTag(i, invItem).equals("AntiDespawn")) {
							ItemStack item = nbtWrapper.removeNbtTag(i, invItem);
							inv.setItem(x, item);
						}
					} catch (NullPointerException ex) {}
					x++;
				}
			}
		}, 1L);
		
	}
	
}
