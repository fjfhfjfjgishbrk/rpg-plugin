package fuk.plugintest;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.EntityType;

public class EntityElementDefense {
	
	private Main plugin;
	
	public static HashMap<EntityType, ArrayList<Integer>> elementDefense = new HashMap<EntityType, ArrayList<Integer>>();
	public static HashMap<EntityType, ArrayList<Integer>> elementAttack = new HashMap<EntityType, ArrayList<Integer>>();
	
	public EntityElementDefense(Main plugin){
		this.plugin = plugin;
		init();
	}
	
	public static void init(){
		elementDefense.put(EntityType.COW, setElementStats(-150, 0, 0, 0, -50, 400));
		elementDefense.put(EntityType.ZOMBIE, setElementStats(-250, 50, -100, 100, 0, 200));
		
		elementAttack.put(EntityType.ZOMBIE, setElementStats(0, 0, 0, 50, 0, 100));
		
	}
	
	
	private static ArrayList<Integer> setElementStats(Integer fire, Integer water, Integer ice, Integer earth, Integer thunder, Integer magic){
		ArrayList<Integer> elementList = new ArrayList<Integer>();
		elementList.add(fire);
		elementList.add(water);
		elementList.add(ice);
		elementList.add(earth);
		elementList.add(thunder);
		elementList.add(magic);
		return elementList;
	}
	
}
