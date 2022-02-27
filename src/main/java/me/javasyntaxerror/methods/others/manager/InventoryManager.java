package me.javasyntaxerror.methods.others.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.javasyntaxerror.Cores;

public class InventoryManager {
	
	public void setJoinInventory(Player player){
		player.getInventory().clear();
		player.getInventory().setItem(0, Cores.getInstance().itemManager.get(Material.BED, 1, 0, "§eTeamauswahl"));
	}
	
	public void setRespawnInventory(Player player){
		player.getInventory().clear();
		player.getInventory().setItem(0, Cores.getInstance().itemManager.get(Material.STONE_SWORD, 1, 0, "§bStein Schwert"));
		player.getInventory().setItem(1, Cores.getInstance().itemManager.get(Material.BOW, 1, 0, "§bBow"));
		player.getInventory().setItem(2, Cores.getInstance().itemManager.get(Material.LOG, 64, 1, "§bHolz"));
		player.getInventory().setItem(3, Cores.getInstance().itemManager.get(Material.ARROW, 16, 0, "§bPfeil"));
		player.getInventory().setItem(5, Cores.getInstance().itemManager.get(Material.GOLDEN_APPLE, 16, 0, "§bGoldener Apfel"));
		player.getInventory().setItem(7, Cores.getInstance().itemManager.get(Material.IRON_AXE, 1, 0, "§bEisen Axt"));
		player.getInventory().setItem(8, Cores.getInstance().itemManager.get(Material.IRON_PICKAXE, 1, 0, "§bEisen Spitzhacke"));
		
		player.getInventory().setHelmet(Cores.getInstance().itemManager.get(Material.CHAINMAIL_HELMET, 1, 0, "§bHelm"));
		player.getInventory().setChestplate(Cores.getInstance().itemManager.get(Material.CHAINMAIL_CHESTPLATE, 1, 0, "§bBrustpanzer"));
		player.getInventory().setLeggings(Cores.getInstance().itemManager.get(Material.CHAINMAIL_LEGGINGS, 1, 0, "§bHose"));
		player.getInventory().setBoots(Cores.getInstance().itemManager.get(Material.CHAINMAIL_BOOTS, 1, 0, "§bSchuhe"));
	}
	
	public void openSpecInv(Player player){
		Inventory inv = Bukkit.createInventory(null, 3*9, "§bTeleporter");
		
		for(String strings : Cores.getInstance().teams){
			for(String string : Cores.getInstance().teamManager.get(strings).getPlayers()){
				inv.addItem(Cores.getInstance().itemManager.getHead(Material.SKULL_ITEM, 1, 3, Cores.getInstance().teamManager.get(strings).getPrefix() + string, string.toLowerCase()));
			}
		}
		
		player.openInventory(inv);
	}
	
	public void openTeamChangeInv(Player player){
		Inventory inv = Bukkit.createInventory(null, 1*9, "§eTeamauswahl");
		
		for(String strings : Cores.getInstance().teams){
			inv.setItem(Cores.getInstance().teamManager.get(strings).getSlot(), Cores.getInstance().teamManager.get(strings).getItem());
		}
		
		player.openInventory(inv);
	}

}
