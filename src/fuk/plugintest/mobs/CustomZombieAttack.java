package fuk.plugintest.mobs;

import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.PathfinderGoalZombieAttack;

public class CustomZombieAttack extends PathfinderGoalZombieAttack {
	
	protected int cd;
	protected EntityCreature b;
	protected int cooldown = 20;
	
	public CustomZombieAttack(EntityZombie entityzombie, double d0, boolean flag, int cd) {
		super(entityzombie, d0, flag);
		cooldown = cd;
		
	}
	
	@Override
	protected void a(EntityLiving entityliving, double d0) {
		double d1 = this.a(entityliving);

        if (d0 <= d1 && this.cd <= 0) {
            this.cd = cooldown;
            this.a.swingHand(EnumHand.MAIN_HAND);
            this.a.attackEntity(entityliving);
        }
		this.cd = cooldown;
 
    }
	
	@Override
	public void e() {
		super.e();
		this.cd = Math.max(this.cd - 1, 0);
	}
	
}
