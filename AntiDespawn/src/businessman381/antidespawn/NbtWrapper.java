package businessman381.antidespawn;

import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R2.NBTTagCompound;

public class NbtWrapper {
	
	public ItemStack setNbtTag(Integer key, String value, ItemStack item) {
		net.minecraft.server.v1_16_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tagCompound = nmsStack.getOrCreateTag();
		tagCompound.setString(key.toString(), value);
		item = CraftItemStack.asBukkitCopy(nmsStack);
		return item;
	}
	
	public String getNbtTag(Integer key, ItemStack item) {
		net.minecraft.server.v1_16_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tagCompound = nmsStack.getTag();
		if (tagCompound == null)
			return null;
		
		return tagCompound.getString(key.toString());
	}
	
	public ItemStack removeNbtTag(Integer key, ItemStack item) {
		net.minecraft.server.v1_16_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tagCompound = nmsStack.getTag();
		tagCompound.remove(key.toString());
		item = CraftItemStack.asBukkitCopy(nmsStack);
		return item;
	}
	
}
