package fuk.plugintest;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.EntityType;

public class EntityElementDefense {
	
	private Main plugin;
	
	public static HashMap<EntityType, ArrayList<Integer>> elementDefense = new HashMap<EntityType, ArrayList<Integer>>();
	
	public EntityElementDefense(Main plugin){
		this.plugin = plugin;
		init();
	}
	
	public static void init(){
		elementDefense.put(EntityType.COW, setElementDefense(-150, 0, 0, 0, -200, 500));
		
	}
	
	
	private static ArrayList<Integer> setElementDefense(Integer fire, Integer water, Integer ice, Integer earth, Integer thunder, Integer poison){
		ArrayList<Integer> elementList = new ArrayList<Integer>();
		elementList.add(fire);
		elementList.add(water);
		elementList.add(ice);
		elementList.add(earth);
		elementList.add(thunder);
		elementList.add(poison);
		return elementList;
	}
	
}
