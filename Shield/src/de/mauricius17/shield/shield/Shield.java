package de.mauricius17.shield.shield;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.mauricius17.shield.system.Main;

public class Shield  {
	
	private Player player;
	private int taskID = -1;
	
	public Shield(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void start(IShield shield) {
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if(shield != null) {
					shield.onShieldStart();
				}
			}
		}, 0, 10L);
	}
	
	public void stop() {
		if(taskID != -1) {
			Bukkit.getScheduler().cancelTask(taskID);
		}
	}
	
	public interface IShield {
		void onShieldStart();
	}
}