package team.cqr.cqrepoured.entity.projectiles;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import team.cqr.cqrepoured.util.EntityUtil;

public class ProjectileBullet extends ProjectileBase implements IEntityAdditionalSpawnData {

	private int type;

	public ProjectileBullet(World worldIn) {
		super(worldIn);
	}

	public ProjectileBullet(World worldIn, double x, double y, double z, int type) {
		super(worldIn, x, y, z);
		this.type = type;
	}

	public ProjectileBullet(World worldIn, LivingEntity shooter, int type) {
		super(worldIn, shooter);
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	@Override
	protected void onHit(RayTraceResult result) {
		if (!this.world.isRemote) {
			if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
				if (result.entityHit == this.thrower) {
					return;
				}

				if (result.entityHit instanceof LivingEntity) {
					LivingEntity entity = (LivingEntity) result.entityHit;

					float damage = 5.0F;
					if (this.type == 1) {
						damage += 2.5F;
					} else if (this.type == 2) {
						damage += 3.75F;
					} else if (this.type == 3) {
						damage += 5.0F;
					} else if (this.type == 4) {
						damage += 5.0F;

						if (entity.attackEntityFrom(new IndirectEntityDamageSource("onFire", this, this.thrower).setFireDamage(), damage / 2)) {
							entity.setFire(3);
						}
					}
					if (EntityUtil.isEntityFlying(entity)) {
						damage *= 2;
					}

					entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.thrower), damage);
					this.setDead();
				}
			}

			super.onHit(result);
		}
	}

	@Override
	protected void onUpdateInAir() {
		if (this.world.isRemote) {
			if (this.ticksExisted < 10) {
				this.world.spawnParticle(ParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(this.type);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.type = additionalData.readInt();
	}

}
