package team.cqr.cqrepoured.entity.misc;

import java.util.UUID;

import com.google.common.base.Predicates;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import team.cqr.cqrepoured.entity.Capes;
import team.cqr.cqrepoured.entity.EntityEquipmentExtraSlot;
import team.cqr.cqrepoured.entity.bases.AbstractEntityCQR;
import team.cqr.cqrepoured.entity.boss.EntityCQRWalkerKing;
import team.cqr.cqrepoured.entity.mobs.EntityCQRWalker;
import team.cqr.cqrepoured.init.CQRCreatureAttributes;

public class EntityWalkerKingIllusion extends EntityCQRWalker {

	private EntityCQRWalkerKing parent;
	private int ttl = 1200;
	private int searchTicksForParent = 20;
	private int damageCounter = 0;
	private UUID parentUUID = null;

	public EntityWalkerKingIllusion(World worldIn) {
		super(worldIn);
	}

	public EntityWalkerKingIllusion(int ttl, EntityCQRWalkerKing parent, World world) {
		this(world);
		this.parent = parent;
		this.ttl = ttl;
		this.parentUUID = parent.getPersistentID();

		this.cloneParentEquipment(parent);
	}

	private void cloneParentEquipment(AbstractEntityCQR parent) {
		this.setItemStackToExtraSlot(EntityEquipmentExtraSlot.POTION, parent.getItemStackFromExtraSlot(EntityEquipmentExtraSlot.POTION));
		this.setItemStackToSlot(EquipmentSlotType.CHEST, parent.getItemStackFromSlot(EquipmentSlotType.CHEST));
		this.setItemStackToSlot(EquipmentSlotType.HEAD, parent.getItemStackFromSlot(EquipmentSlotType.HEAD));
		this.setItemStackToSlot(EquipmentSlotType.LEGS, parent.getItemStackFromSlot(EquipmentSlotType.LEGS));
		this.setItemStackToSlot(EquipmentSlotType.FEET, parent.getItemStackFromSlot(EquipmentSlotType.FEET));
		this.setItemStackToSlot(EquipmentSlotType.OFFHAND, parent.getItemStackFromSlot(EquipmentSlotType.OFFHAND));
		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(parent.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem(), 1));
	}

	@Override
	protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
	}

	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return 0;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return new ResourceLocation("enpty");
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		if (this.parent != null) {
			this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(this.parent.getMaxHealth());
			this.setHealth(this.parent.getHealth());
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return this.attackEntityFrom(source, amount, false);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount, boolean sentFromPart) {
		// return super.attackEntityFrom(source, amount, sentFromPart);
		if (!this.world.isRemote && this.damageCounter >= 2 * (this.world.getDifficulty().ordinal() <= 0 ? 1 : this.world.getDifficulty().ordinal())) {
			this.setDead();
		}
		this.damageCounter++;
		return true;
	}

	@Override
	public void setDead() {
		if (this.world.isRemote) {
			this.playDeathEffect();
		}
		super.setDead();
	}

	private void playDeathEffect() {
		if (this.world.isRemote) {
			for (int i = 0; i < 15; i++) {
				this.world.spawnParticle(ParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ, 0.0, 0.025, 0.0);
				this.world.spawnParticle(ParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ, 0.025, 0.01, 0.025);
				this.world.spawnParticle(ParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ, 0.025, 0.01, -0.025);
				this.world.spawnParticle(ParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ, -0.025, 0.01, 0.025);
				this.world.spawnParticle(ParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ, -0.025, 0.01, -0.025);
			}
			this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT, 2, 0.75F, true);
		}
	}

	@Override
	public void onEntityUpdate() {
		if (!this.world.isRemote) {
			if (this.ttl < 0) {
				this.setDead();
				return;
			}
			// Search parent
			if (this.parent == null && this.parentUUID != null) {
				if (this.searchTicksForParent > 0) {
					if (!this.world.isRemote) {
						this.world.getEntitiesInAABBexcluding(this, new AxisAlignedBB(this.getPosition().add(-10, -10, -10), this.getPosition().add(10, 10, 10)), Predicates.instanceOf(EntityCQRWalkerKing.class)).forEach(t -> {
							if (t.getPersistentID().equals(EntityWalkerKingIllusion.this.parentUUID)) {
								EntityWalkerKingIllusion.this.parent = (EntityCQRWalkerKing) t;
							}
						});

						this.searchTicksForParent--;
					}
				} else {
					this.setDead();
					return;
				}
			}
			if (this.parent == null || this.parent.isDead) {
				this.setDead();
				return;
			}
			super.onEntityUpdate();
			this.setHealth(this.parent.getHealth());

			if (this.parent.getAttackTarget() != null || this.getAttackTarget() != null) {
				this.ttl--;
			} else {
				this.ttl -= 10;
			}
		} else {
			super.onEntityUpdate();
		}
	}

	@Override
	public ResourceLocation getResourceLocationOfCape() {
		return Capes.CAPE_WALKER;
	}

	@Override
	public boolean hasCape() {
		return true;
	}

	@Override
	public void save(CompoundNBT compound) {
		super.save(compound);
		compound.setInteger("ttl", this.ttl);
		compound.setTag("illusionParent", NBTUtil.createUUIDTag(this.parentUUID));
	}

	@Override
	public void readEntityFromNBT(CompoundNBT compound) {
		super.readEntityFromNBT(compound);
		this.ttl = compound.getInteger("ttl");
		this.parentUUID = NBTUtil.getUUIDFromTag(compound.getCompoundTag("illusionParent"));
	}

	@Override
	public CreatureAttribute getCreatureAttribute() {
		return CQRCreatureAttributes.VOID;
	}

}
