package team.cqr.cqrepoured.entity.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.entity.PartEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import team.cqr.cqrepoured.entity.misc.EntityBubble;

public class ProjectileBubble extends ProjectileBase {

	private Entity shooter;
	protected float damage;

	public ProjectileBubble(World worldIn) {
		super(worldIn);
	}

	public ProjectileBubble(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public ProjectileBubble(World worldIn, LivingEntity shooter) {
		super(worldIn, shooter);
		this.shooter = shooter;
		this.damage = 1F;
		this.isImmuneToFire = true;
	}

	@Override
	protected void onHit(RayTraceResult result) {
		if (!this.world.isRemote && result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit != null && result.entityHit != this.shooter && !(result.entityHit instanceof PartEntity)) {
			this.applyEntityCollision(result.entityHit);
		}

		super.onHit(result);
	}

	@Override
	public void applyEntityCollision(Entity entityHit) {
		if (entityHit == this.shooter) {
			return;
		}

		if (entityHit instanceof EntityBubble || entityHit instanceof ProjectileBubble) {
			return;
		}

		if (entityHit.isRiding() && entityHit.getRidingEntity() instanceof EntityBubble) {
			return;
		}

		if (entityHit instanceof MobEntity && ((MobEntity) entityHit).getActiveItemStack().getItem() instanceof ShieldItem) {
			return;
		}

		entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.shooter, this), this.damage);
		float pitch = (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F;
		this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_SWIM, SoundCategory.PLAYERS, 4, pitch, true);

		EntityBubble bubbles = new EntityBubble(this.world);
		bubbles.moveToBlockPosAndAngles(entityHit.getPosition().add(0, 0.25, 0), entityHit.rotationYaw, entityHit.rotationPitch);
		this.world.spawnEntity(bubbles);

		entityHit.startRiding(bubbles, true);

		this.setDead();
	}

	@Override
	protected void onUpdateInAir() {
		super.onUpdateInAir();
		if (this.world.isRemote) {
			if (this.ticksExisted % 5 == 0) {
				this.world.spawnParticle(ParticleTypes.WATER_BUBBLE, this.posX, this.posY + 0.1D, this.posZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}

}
