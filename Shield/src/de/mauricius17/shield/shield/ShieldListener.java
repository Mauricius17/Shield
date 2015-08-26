package de.mauricius17.shield.shield;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import de.mauricius17.shield.shield.Shield.IShield;
import de.mauricius17.shield.system.Main;
import de.mauricius17.shield.utils.Utils;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.WarpEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class ShieldListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		for(UUID uuids : Utils.getShield().keySet()) {
			Player players = Bukkit.getPlayer(uuids);
			
			if(p != players && players != null) {
				if(p.getLocation().distance(players.getLocation()) < 3.5) {
					if((!(p.hasPermission("administrator")))) {
						double ax = p.getLocation().getX(), ay = p.getLocation().getY(), az = p.getLocation().getZ();
						double bx = players.getLocation().getX(), by = players.getLocation().getY(), bz = players.getLocation().getZ();
						
						double x = ax - bx;
						double y = ay - by;
						double z = az - bz;
						
						Vector vec = new Vector(x, y, z).normalize().multiply(1D).setY(0.5D);
						p.setVelocity(vec);	
					}
				}
			}
		}
		
		for(Entity entity : p.getNearbyEntities(3.5, 3.5, 3.5)) {
			if(entity instanceof Player) {
				Player players = (Player) entity;
				
				if(Utils.getShield().containsKey(p.getUniqueId())) {
					if((!(players.hasPermission("administrator")))) {
						double ax = p.getLocation().getX(), ay = p.getLocation().getY(), az = p.getLocation().getZ();
						double bx = players.getLocation().getX(), by = players.getLocation().getY(), bz = players.getLocation().getZ();
						
						double x = bx - ax;
						double y = by - ay;
						double z = bz - az;
						
						Vector vec = new Vector(x, y, z).normalize().multiply(1D).setY(0.5D);
						players.setVelocity(vec);	
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getItem() != null) {
				if(e.getItem().getType().equals(Material.DIAMOND)) {
					
					Player p = e.getPlayer();
					
					if(Utils.getShield().containsKey(p.getUniqueId())) {
						Utils.getShield().get(p.getUniqueId()).stop();
						Utils.getShield().remove(p.getUniqueId());
						p.sendMessage("§cDeaktiviert!");
					} else {
						Shield shield = new Shield(p);
						shield.start(new IShield() {
							
							@Override
							public void onShieldStart() {
								WarpEffect effect = new WarpEffect(Main.getInstance().getEm());									
								effect.particle = ParticleEffect.REDSTONE;
								effect.setEntity(p);
								effect.rings = 5;
								effect.speed = 10;
								effect.iterations = 20;
								effect.type = EffectType.REPEATING;
								effect.radius = 2;
								effect.start();
							}
						});
						
						Utils.getShield().put(p.getUniqueId(), shield);	
						p.sendMessage("§aAktiviert!");
					}					
				}
			}
		}
	}
}
