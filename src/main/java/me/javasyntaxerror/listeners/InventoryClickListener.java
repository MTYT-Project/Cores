package me.javasyntaxerror.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;

public class InventoryClickListener implements Listener {
	
	@EventHandler
	public void onInvClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		
		if(Cores.getInstance().state != GameState.INGAME){
			event.setCancelled(true);
		} else {
			return;
		}
		
		if(event.getCurrentItem() != null && event.getClickedInventory() != null && event.getClickedInventory() != player.getInventory() && event.getCurrentItem().getItemMeta() != null){
			if(event.getClickedInventory().getName().equalsIgnoreCase(Cores.translation("inventory.name", "§eTeamauswahl"))){
				String[] strings = event.getCurrentItem().getItemMeta().getDisplayName().split(" ");
				
				Cores.getInstance().teamVote.joinTeam(player, strings[1]);
				
				player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 100);
			}
		}
	}

}
