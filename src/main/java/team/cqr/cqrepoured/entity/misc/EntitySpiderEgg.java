package team.cqr.cqrepoured.entity.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import team.cqr.cqrepoured.init.CQREntityTypes;

public class EntitySpiderEgg extends Entity {

	private static final int STAGE_DURATION = 30;
	private static final int STAGE_COUNT = 5;
	private static final ResourceLocation MINION_ID = new ResourceLocation("minecraft", "cave_spider");

	protected static final DataParameter<Integer> STAGE = EntityDataManager.<Integer>defineId(EntitySpiderEgg.class, DataSerializers.INT);

	private int currentStageDuration = 0;

	public EntitySpiderEgg(World worldIn) {
		this(CQREntityTypes.SPIDER_EGG.get(), worldIn);
	}
	
	public EntitySpiderEgg(EntityType<? extends EntitySpiderEgg> type, World worldIn) {
		super(type, worldIn);

	}
	
	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(STAGE, 0);
	}

	@Override
	public void tick() {
		// TODO: Play particles and sound on mob spawning
		if (!this.level.isClientSide) {
			this.currentStageDuration++;
			if (this.currentStageDuration > STAGE_DURATION) {
				this.entityData.set(STAGE, this.getStage() + 1);
				this.currentStageDuration = 0;
			}
			super.tick();
			if (this.getStage() >= STAGE_COUNT) {
				// Destroy yourself and spawn the spider
				Entity spider = EntityList.createEntityByIDFromName(MINION_ID, this.level);
				if (spider != null) {
					spider.setPos(this.position().x, this.position().y + 0.5D, this.position().z);
					this.level.addFreshEntity(spider);
				}
				this.remove();
			}
		} else {
			super.tick();
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT compound) {
		this.entityData.set(STAGE, compound.getInt("stage"));
		this.currentStageDuration = compound.getInt("stage_duration");
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT compound) {
		compound.putInt("stage", this.getStage());
		compound.putInt("stage_duration", this.currentStageDuration);
	}

	public int getStage() {
		return this.entityData.get(STAGE);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
