package de.mauricius17.shield.system;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;

import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;

public class Main extends JavaPlugin {

	private static Main instance;
	private EffectManager em;

	@Override
	public void onEnable() {
		instance = this;
		em = new EffectManager(EffectLib.instance());
		registerEvents();
	}
	
	public EffectManager getEm() {
		return em;
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	private void registerEvents() {
		PluginManager pluginManager = Bukkit.getPluginManager();
		
		try {
			for(ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader()).getTopLevelClasses("de.mauricius17.shield.shield")) {
				Class<?> clazz = Class.forName(classInfo.getName());
				
				if(Listener.class.isAssignableFrom(clazz)) {
					pluginManager.registerEvents((Listener) clazz.newInstance(), this);
				}
			}
		} catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
	}
}