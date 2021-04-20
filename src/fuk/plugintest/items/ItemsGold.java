package fuk.plugintest.items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import fuk.plugintest.Main;

public class ItemsGold {
	
	private static Main plugin;
	
	public static UUID goldUUID;
	
	public static ArrayList<String> hasAbilities = new ArrayList<String>();
	
	public ItemsGold(Main plugin){
		this.plugin = plugin;
	}
	
	private static void createStackGold() {
		ItemStack item = new ItemStack(Material.GOLD_INGOT, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Stacked gold");
		List<String> lore = new ArrayList<>();
		lore.add("");
		for (String s: itemManager.setDescription("Gold is a chemical element with the symbol Au (from Latin: aurum) "
				+ "and atomic number 79, making it one of the higher atomic number elements that occur naturally. In a pure form, it is a bright, "
				+ "slightly reddish yellow, dense, soft, malleable, and ductile metal.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.stackGold = item;
	}
	
	private static void createExStackGold() {
		ItemStack item = new ItemStack(Material.GOLD_INGOT, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_RED + "Extremely stacked gold");
		List<String> lore = new ArrayList<>();
		lore.add("");
		for (String s: itemManager.setDescription("Gold has a density of 19.3 g/cm3, which means that this gold ingot weighs a lot."
				+ " Studies show that, if this gold were to be taken to Mount Everest and thrown down the gold would accelarate so much "
				+ "that the gold would be as fast as if a person were to fall off Mount Everest.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.exStackGold = item;
	}
	
	private static void createStackGoldBlock() {
		ItemStack item = new ItemStack(Material.GOLD_BLOCK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Stacked gold block");
		List<String> lore = new ArrayList<>();
		lore.add("");
		for (String s: itemManager.setDescription("Traditionally, gold in the universe is thought to have formed by the r-process (rapid neutron capture) in "
				+ "supernova nucleosynthesis, but more recently it has been suggested that gold and other elements heavier than iron may also be produced in "
				+ "quantity by the r-process in the collision of neutron stars. In both cases, satellite spectrometers at first only indirectly detected the "
				+ "resulting gold. However, in August 2017, the spectroscopic signatures of heavy elements, including gold, were observed by electromagnetic "
				+ "observatories in the GW170817 neutron star merger event, after gravitational wave detectors confirmed the event as a neutron star merger.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.stackGoldBlock = item;
		itemManager.noRightClick.add(itemManager.stackGoldBlock);
	}
	
	private static void createExStackGoldBlock() {
		ItemStack item = new ItemStack(Material.GOLD_BLOCK, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Extremely stacked gold block");
		List<String> lore = new ArrayList<>();
		lore.add("");
		for (String s: itemManager.setDescription("Visit wikipedia for more information on gold. Also this gold block is so dense that it has a possibility"
				+ " to become a black hole. Hopefully not though.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		itemManager.exStackGoldBlock = item;
		itemManager.noRightClick.add(itemManager.exStackGoldBlock);
	}
	
	private static void createGoldHelmet(){
		ItemStack item = new ItemStack(Material.GOLDEN_HELMET, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Golden helmet");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+1500");
		lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+250");
		lore.add(ChatColor.GRAY + "Element defense:" + ChatColor.RED + " 20♦" + ChatColor.YELLOW + " -30♦" + ChatColor.DARK_PURPLE + " 100♦");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Malleable");
		lore.add(ChatColor.GRAY + "Reduces damage from projectiles by " + ChatColor.RED + "10%" + ChatColor.GRAY + ".");
		lore.add("");
		for (String s: itemManager.setDescription("Gold is the most malleable and ductile of all known metals. "
				+ "A single ounce of gold can be beaten into a sheet measuring roughly 5 meters on a side. Thin sheets of gold, known as gold leaf, are "
				+ "primarily used in arts and crafts for gilding. One sheet of gold leaf can be as thin as 0.000127 millimeters, or about 400 times "
				+ "thinner than a human hair.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(goldUUID, "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "health"), PersistentDataType.INTEGER, 1500);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defense"), PersistentDataType.INTEGER, 250);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDefense"), PersistentDataType.INTEGER_ARRAY, new int[] {20, 0, 0, 0, -30, 100});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "goldhelmet");
		item.setItemMeta(meta);
		itemManager.goldHelmet = item;
	}
	
	private static void createGoldChestplate(){
		ItemStack item = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Golden chestplate");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+1800");
		lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+310");
		lore.add(ChatColor.GRAY + "Element defense:" + ChatColor.RED + " 25♦" + ChatColor.YELLOW + " -35♦" + ChatColor.DARK_PURPLE + " 115♦");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Shiny");
		lore.add(ChatColor.GRAY + "Increases damage by " + ChatColor.RED + "5%" + ChatColor.GRAY + " during day.");
		lore.add("");
		for (String s: itemManager.setDescription("Looking at the light reflecting off the chestplate, you feel a little stronger, but only during"
				+ "the day. The moon simply isn't light enough for you to feel stronger. Probably it's because you're not strong enough, or probably the gold"
				+ "isn't good enough. Better get some upgrades so you can feel stronger.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(goldUUID, "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "health"), PersistentDataType.INTEGER, 1800);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defense"), PersistentDataType.INTEGER, 310);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDefense"), PersistentDataType.INTEGER_ARRAY, new int[] {25, 0, 0, 0, -35, 115});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "goldchest");
		item.setItemMeta(meta);
		itemManager.goldChestplate = item;
	}
	
	private static void createGoldLeggings(){
		ItemStack item = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Golden leggings");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+1700");
		lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+280");
		lore.add(ChatColor.GRAY + "Element defense:" + ChatColor.RED + " 20♦" + ChatColor.YELLOW + " -30♦" + ChatColor.DARK_PURPLE + " 110♦");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Heat reflector");
		lore.add(ChatColor.GRAY + "Gives fire resistance while wearing.");
		lore.add("");
		for (String s: itemManager.setDescription("While wearing the leggings you feel no heat from the sun. It seems as if the gold completly "
				+ "insulates the heat from outside. It's strange though that it wouldn't melt in lava.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(goldUUID, "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "health"), PersistentDataType.INTEGER, 1700);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defense"), PersistentDataType.INTEGER, 280);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDefense"), PersistentDataType.INTEGER_ARRAY, new int[] {20, 0, 0, 0, -30, 110});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "goldleggings");
		item.setItemMeta(meta);
		itemManager.goldLeggings = item;
	}
	
	private static void createGoldBoots(){
		ItemStack item = new ItemStack(Material.GOLDEN_BOOTS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Golden boots");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+1450");
		lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+240");
		lore.add(ChatColor.GRAY + "Element defense:" + ChatColor.RED + " 15♦" + ChatColor.YELLOW + " -30♦" + ChatColor.DARK_PURPLE + " 85♦");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Gold resonance");
		lore.add(ChatColor.GRAY + "Reduces damage taken by " + ChatColor.RED + "2%" + ChatColor.GRAY + " for every golden armor worn.");
		lore.add("");
		for (String s: itemManager.setDescription("The production of gold from a more common element, such as lead, has long been a subject of human inquiry, "
				+ "and the ancient and medieval discipline of alchemy often focused on it; however, the transmutation of the chemical elements did not become "
				+ "possible until the understanding of nuclear physics in the 20th century. The first synthesis of gold was conducted by Japanese physicist "
				+ "Hantaro Nagaoka, who synthesized gold from mercury in 1924 by neutron bombardment. An American team, working without knowledge of Nagaoka's "
				+ "prior study, conducted the same experiment in 1941, achieving the same result and showing that the isotopes of gold produced by it were all "
				+ "radioactive.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(goldUUID, "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "health"), PersistentDataType.INTEGER, 1450);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defense"), PersistentDataType.INTEGER, 240);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDefense"), PersistentDataType.INTEGER_ARRAY, new int[] {15, 0, 0, 0, -30, 85});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "goldboots");
		item.setItemMeta(meta);
		itemManager.goldBoots = item;
	}
	
	private static void createGoldHelmet2(){
		ItemStack item = new ItemStack(Material.GOLDEN_HELMET, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Highy malleable golden helmet");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+2300");
		lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+390");
		lore.add(ChatColor.GRAY + "Element defense:" + ChatColor.RED + " 40♦" + ChatColor.DARK_GREEN + " 30♦" + ChatColor.YELLOW + " -10♦" + ChatColor.DARK_PURPLE + " 140♦");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Extremely malleable");
		lore.add(ChatColor.GRAY + "Reduces damage from projectiles by " + ChatColor.RED + "20%" + ChatColor.GRAY + ".");
		lore.add(ChatColor.GRAY + "Increases healing by " + ChatColor.BLUE + "25%" + ChatColor.GRAY + ".");
		lore.add("");
		for (String s: itemManager.setDescription("Gold is the most malleable of all metals. It can be drawn into a wire of single-atom width, and "
				+ "then stretched considerably before it breaks. Such nanowires distort via formation, reorientation and migration of dislocations "
				+ "and crystal twins without noticeable hardening. A single gram of gold can be beaten into a sheet of 1 square metre (11 sq ft), "
				+ "and an avoirdupois ounce into 300 square feet (28 m2). Gold leaf can be beaten thin enough to become semi-transparent. The transmitted "
				+ "light appears greenish blue, because gold strongly reflects yellow and red. Such semi-transparent sheets also strongly reflect "
				+ "infrared light, making them useful as infrared (radiant heat) shields in visors of heat-resistant suits, and in sun-visors for spacesuits.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(goldUUID, "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "health"), PersistentDataType.INTEGER, 2300);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defense"), PersistentDataType.INTEGER, 390);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "heal"), PersistentDataType.INTEGER, 2500);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDefense"), PersistentDataType.INTEGER_ARRAY, new int[] {40, 0, 0, 30, -10, 140});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "goldhelmet2");
		item.setItemMeta(meta);
		itemManager.goldHelmetT2 = item;
	}
	
	private static void createGoldChestplate2(){
		ItemStack item = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Radiant golden chestplate");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+2500");
		lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+430");
		lore.add(ChatColor.GRAY + "Element defense:" + ChatColor.RED + " 45♦" + ChatColor.DARK_GREEN + " 30♦" + ChatColor.YELLOW + " -5♦" + ChatColor.DARK_PURPLE + " 160♦");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Radiating luminosity");
		lore.add(ChatColor.GRAY + "Increases damage by " + ChatColor.RED + "5%" + ChatColor.GRAY + " overall and an additional " + ChatColor.RED + "10%");
		lore.add(ChatColor.GRAY + "during day.");
		lore.add("");
		for (String s: itemManager.setDescription("AHHHH IT'S SO BRIGHT I CAN'T SEE ANYTHING HELPPPPP!")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(goldUUID, "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "health"), PersistentDataType.INTEGER, 2500);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defense"), PersistentDataType.INTEGER, 430);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDefense"), PersistentDataType.INTEGER_ARRAY, new int[] {45, 0, 0, 30, -5, 160});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "goldchest2");
		item.setItemMeta(meta);
		itemManager.goldChestplateT2 = item;
	}
	
	private static void createGoldLeggings2(){
		ItemStack item = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Insulated golden leggings");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+2400");
		lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+415");
		lore.add(ChatColor.GRAY + "Element defense:" + ChatColor.RED + " 34♦" + ChatColor.DARK_GREEN + " 15♦"  + ChatColor.YELLOW + " -10♦" + ChatColor.DARK_PURPLE + " 140♦");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Heat absorber");
		lore.add(ChatColor.GRAY + "Gives fire resistance while wearing, and heals" + ChatColor.RED + " 1% Health♥ " + ChatColor.GRAY + "per");
		lore.add(ChatColor.GRAY + "second while on fire.");
		lore.add("");
		for (String s: itemManager.setDescription("Patience serves as a protection against wrongs as clothes do against cold. For if you put on more "
				+ "clothes as the cold increases, it will have no power to hurt you. So in like manner you must grow in patience when you meet with "
				+ "great wrongs, and they will then be powerless to vex your mind. And also these leggings are so cold wtf. I don't even feel the lava underneath me.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(goldUUID, "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "health"), PersistentDataType.INTEGER, 2400);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defense"), PersistentDataType.INTEGER, 415);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDefense"), PersistentDataType.INTEGER_ARRAY, new int[] {34, 0, 0, 15, -10, 140});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "goldleggings2");
		item.setItemMeta(meta);
		itemManager.goldLeggingsT2 = item;
	}
	
	private static void createGoldBoots2(){
		ItemStack item = new ItemStack(Material.GOLDEN_BOOTS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Harmonic golden boots");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + "+2000");
		lore.add(ChatColor.GRAY + "Defense: " + ChatColor.RED + "+380");
		lore.add(ChatColor.GRAY + "Element defense:" + ChatColor.RED + " 20♦" + ChatColor.DARK_GREEN + " 10♦" + ChatColor.YELLOW + " -15♦" + ChatColor.DARK_PURPLE + " 110♦");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Golden harmony");
		lore.add(ChatColor.GRAY + "Reduces damage taken by " + ChatColor.RED + "4%" + ChatColor.GRAY + ", and increases damage ");
		lore.add(ChatColor.GRAY + "by " + ChatColor.RED + "2%" + ChatColor.GRAY + " for every gold armor you are wearing.");
		lore.add("");
		for (String s: itemManager.setDescription("Harmony is a perceptual property of music, and along with melody, one of the building blocks of Western music. Its perception is based "
				+ "on consonance, a concept whose definition has changed various times throughout Western music. In a physiological approach, consonance is a continuous "
				+ "variable. Consonant pitch relationships are described as sounding more pleasant, euphonious, and beautiful than dissonant relationships which sound "
				+ "unpleasant, discordant, or rough. The study of harmony involves chords and their construction and chord progressions and the principles of connection that govern them. "
				+ "Harmony is often said to refer to the 'vertical' aspect of music, as distinguished from melodic line, or the 'horizontal' aspect. Different kinds of gold have different "
				+ "resonance frequency. Gold can be split into serveral types and can be easily distinguished by their colors. They can be alloyed with silver, copper, zinc, palladium, and "
				+ "nickel to create different gold colors. The most common gold colors are: yellow, white, rose, and green. Green gold (or Electrum) is mixed with gold, silver, and "
				+ "sometimes copper. Silver is what gives the gold alloy the green nuance. Rose gold (or pink gold) is alloyed with gold, copper, and silver. Rose gold is more affordable "
				+ "than the other gold colors because it uses the inexpensive copper for its rose color. Due to its copper content, rose gold is more durable than yellow or white gold. "
				+ "White gold is made of gold and platinum (or palladium). White gold can also be made of gold, palladium, nickel and zinc. White gold is more durable and scratch-resistant "
				+ "than yellow gold. It is also more affordable than both yellow gold and platinum. Yellow gold is made by mixing pure gold with silver, copper, and zinc. It is the purest color, "
				+ "the most hypo-allergenic, and requires the least maintenance of all the gold colors. To know the purity of the gold, see the karat mark. It will let you know how much gold "
				+ "content the gold alloy has. 100% pure gold is marked as “24k”. However, it is unusual to find a “24k” mark on a jewelry because pure gold is a soft metal and is more likely "
				+ "to scratch and bend. Gold in jewelry is therefore often mixed with other metals to make it more durable. However, since this is minecraft 24k is totally possible. In fact, "
				+ "these armors are made with a lot of pure gold which make them resonance at a set frequency which enhances the power of the wearer. In some religions, gold also associates with"
				+ "different kinds of power. In some forms of Christianity and Judaism, gold has been associated both with holiness and evil. In the Book of Exodus, the Golden Calf is a symbol "
				+ "of idolatry, while in the Book of Genesis, Abraham was said to be rich in gold and silver, and Moses was instructed to cover the Mercy Seat of the Ark of the Covenant with pure "
				+ "gold. In Byzantine iconography the halos of Christ, Mary and the Christian saints are often golden."
				+ "In Islam, gold (along with silk) is often cited as being forbidden for men to wear. Abu Bakr al-Jazaeri, quoting a hadith, said that 'the wearing of silk and gold are forbidden "
				+ "on the males of my nation, and they are lawful to their women'. This, however, has not been enforced consistently throughout history, e.g. in the Ottoman Empire. Further, small "
				+ "gold accents on clothing, such as in embroidery, may be permitted."
				+ "According to Christopher Columbus, those who had something of gold were in possession of something of great value on Earth and a substance to even help souls to paradise. "
				+ "*Disclaimer: The information contained on "
				+ "the item is for general information purposes only. This plugin assumes no responsibility for errors or omissions in the contents of the item's lore. In no event shall the plugin "
				+ "be liable for any special, direct, indirect, consequential, or incidental damages or any damages whatsoever, whether in an action of contract, negligence or other tort, arising "
				+ "out of or in connection with the use of the item or the contents of the item's lore. The plugin reserves the right to make additions, deletions, or modifications to the contents "
				+ "on the item at any time without prior notice.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(goldUUID, "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "health"), PersistentDataType.INTEGER, 2000);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "defense"), PersistentDataType.INTEGER, 380);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDefense"), PersistentDataType.INTEGER_ARRAY, new int[] {20, 0, 0, 10, -15, 110});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "goldboots2");
		item.setItemMeta(meta);
		itemManager.goldBootsT2 = item;
	}
	
	private static void createGoldSword(){
		ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Golden sword");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "80 " + ChatColor.DARK_GREEN + "40♦");
		lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+70");
		lore.add(ChatColor.GRAY + "Critical damage: " + ChatColor.RED + "+20%");
		lore.add(ChatColor.GRAY + "Attack speed: " + ChatColor.RED + "+150");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Lightweight" + ChatColor.BOLD + " RIGHT CLICK");
		lore.add(ChatColor.GRAY + "Increases attack speed by " + ChatColor.RED + "50" + ChatColor.GRAY + " for 10 seconds.");
		lore.add(ChatColor.DARK_GRAY + "Cooldown: 45 seconds");
		lore.add(ChatColor.DARK_GRAY + "Cannot stack with other gold swords.");
		lore.add("");
		for (String s: itemManager.setDescription("This sword despite made out of gold seems to be able to lighten itself to make "
				+ "the weilder able to perform attacks faster. It's still quute heavy though, and would need significant power to "
				+ "be able to use it. The downside is that the ability seems to resonance with other gold sword's ability which "
				+ "make them only able to activate one ability at a time.")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(goldUUID, "generic.attackSpeed", 1000, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "damage"), PersistentDataType.INTEGER, 80);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDamage"), PersistentDataType.INTEGER_ARRAY, new int[]{0, 0, 0, 40, 0, 0});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "strength"), PersistentDataType.INTEGER, 70);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "critDamage"), PersistentDataType.INTEGER, 2000);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "attackSpeed"), PersistentDataType.INTEGER, 150);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "goldsword");
		item.setItemMeta(meta);
		itemManager.goldSword = item;
		itemManager.canUpgrade.add("goldsword");
		itemManager.sword.add("goldsword");
	}
	
	private static void createGoldSword2(){
		ItemStack item = new ItemStack(Material.GOLDEN_SWORD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Delicate golden sword");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "280 " + ChatColor.RED + "100♦ " + ChatColor.DARK_GREEN + "190♦ " + ChatColor.DARK_PURPLE + "50♦");
		lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+200");
		lore.add(ChatColor.GRAY + "Critical damage: " + ChatColor.RED + "+65%");
		lore.add(ChatColor.GRAY + "Attack speed: " + ChatColor.RED + "+640");
		lore.add("");
		lore.add(ChatColor.YELLOW + "Ability: Weightless" + ChatColor.BOLD + " RIGHT CLICK");
		lore.add(ChatColor.GRAY + "Increases attack speed by " + ChatColor.RED + "350 + 15%" + ChatColor.GRAY + " for 20 seconds.");
		lore.add(ChatColor.DARK_GRAY + "Cooldown: 40 seconds");
		lore.add(ChatColor.DARK_GRAY + "Cannot stack with other gold swords.");
		lore.add("");
		for (String s: itemManager.setDescription("")) {
			lore.add(s);
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LUCK, 1, false);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(goldUUID, "generic.attackSpeed", 1000, AttributeModifier.Operation.ADD_NUMBER));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "damage"), PersistentDataType.INTEGER, 280);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "elementDamage"), PersistentDataType.INTEGER_ARRAY, new int[]{100, 0, 0, 190, 0, 50});
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "strength"), PersistentDataType.INTEGER, 200);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "critDamage"), PersistentDataType.INTEGER, 6500);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "attackSpeed"), PersistentDataType.INTEGER, 640);
		meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "name"), PersistentDataType.STRING, "goldsword2");
		item.setItemMeta(meta);
		itemManager.goldSwordT2 = item;
		itemManager.canUpgrade.add("goldsword2");
		itemManager.sword.add("goldsword2");
	}
	
	public static void init() {
		createStackGold();
		createExStackGold();
		createStackGoldBlock();
		createExStackGoldBlock();
		createGoldHelmet();
		createGoldChestplate();
		createGoldLeggings();
		createGoldBoots();
		createGoldHelmet2();
		createGoldChestplate2();
		createGoldLeggings2();
		createGoldBoots2();
		createGoldSword();
		createGoldSword2();
	}
}
