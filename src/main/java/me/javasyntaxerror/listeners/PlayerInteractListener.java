package me.javasyntaxerror.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock() != null) {
				if(event.getClickedBlock().getType() == Material.BEACON) {
					event.setCancelled(true);
				}
			}
		} else if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(event.getClickedBlock() != null) {
				if(event.getClickedBlock().getType() == Material.BEACON) {
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1000, 0));
				} else {
					event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
				}
			} else {
				event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
			}
		}

		if (Cores.getInstance().team.get(event.getPlayer().getName()) != null) {
			String team = Cores.getInstance().team.get(event.getPlayer().getName());
			if (team.equals("Spectator")) {
				if (event.getItem() != null && event.getItem().getItemMeta() != null) {
					if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (event.getPlayer().getItemInHand() != null) {
							if (Cores.getInstance().state == GameState.INGAME) {
								if (event.getPlayer().getItemInHand().getType() == Material.COMPASS) {
									Cores.getInstance().inventoryManager.openSpecInv(player);
								}
							}
						}
					}
				}

				event.setCancelled(true);
				return;
			}
		}
		if (event.getAction() == Action.PHYSICAL) {
			event.setCancelled(true);
		}
		
		if (event.getItem() != null && event.getItem().getItemMeta() != null) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (event.getPlayer().getItemInHand() != null) {
					if (Cores.getInstance().state == GameState.LOBBY) {
						if (event.getPlayer().getItemInHand().getType() == Material.BED) {
							Cores.getInstance().inventoryManager.openTeamChangeInv(player);
						}
					}
				}
			}
		}
	}
	
}
