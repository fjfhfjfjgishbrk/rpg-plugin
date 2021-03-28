package fuk.plugintest.mobs;

import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack;

public class CustomMeleeAttack extends PathfinderGoalMeleeAttack {
	
	protected int c;
	protected EntityCreature b;
	protected int cooldown = 20;
	
	public CustomMeleeAttack(EntityCreature entitycreature, double d0, boolean flag, int cd) {
		super(entitycreature, d0, flag);
		cooldown = cd;
		
	}
	
	@Override
	protected void a(EntityLiving entityliving, double d0) {
		super.a(entityliving, d0);
		this.c = cooldown;
 
    }

}
