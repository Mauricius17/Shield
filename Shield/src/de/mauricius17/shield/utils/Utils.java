package de.mauricius17.shield.utils;

import java.util.HashMap;
import java.util.UUID;

import de.mauricius17.shield.shield.Shield;

public class Utils {

	private static HashMap<UUID, Shield> shield = new HashMap<>();
	
	public static HashMap<UUID, Shield> getShield() {
		return shield;
	}
}