package me.javasyntaxerror.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.javasyntaxerror.Cores;
import me.javasyntaxerror.methods.others.GameState;

public class PlayerDamageListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (Cores.getInstance().state == GameState.INGAME) {
				if (Cores.getInstance().team.get(event.getEntity().getName()).equalsIgnoreCase("Spectator")) {
					event.setCancelled(true);
					return;
				}
				return;
			}
			event.setCancelled(true);
			return;
		}
		event.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			if (Cores.getInstance().state == GameState.INGAME) {
				String damagerteam = Cores.getInstance().team.get(event.getDamager().getName());
				String entityteam = Cores.getInstance().team.get(event.getEntity().getName());

				if (entityteam.equalsIgnoreCase(damagerteam)) {
					event.setCancelled(true);
					return;
				}
				if (entityteam.equalsIgnoreCase("spectator")) {
					event.setCancelled(true);
					return;
				}
				if (damagerteam.equalsIgnoreCase("spectator")) {
					event.setCancelled(true);
					return;
				}
				return;
			}
			event.setCancelled(true);
			return;
		}
		if(event.getCause().equals(DamageCause.PROJECTILE)){
			Projectile projectile = (Projectile) event.getDamager();
			Player shooter = (Player) projectile.getShooter();
			String damagerteam = Cores.getInstance().team.get(shooter.getName());
			
			String entityteam = Cores.getInstance().team.get(event.getEntity().getName());

			if (entityteam.equalsIgnoreCase(damagerteam)) {
				event.setCancelled(true);
				return;
			}
			return;
		}
		event.setCancelled(true);
	}

}
