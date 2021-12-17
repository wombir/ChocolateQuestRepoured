package team.cqr.cqrepoured.entity.mobs;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import team.cqr.cqrepoured.config.CQRConfig;
import team.cqr.cqrepoured.entity.bases.AbstractEntityCQR;
import team.cqr.cqrepoured.faction.EDefaultFaction;

public class EntityCQRDummy extends AbstractEntityCQR {

	public EntityCQRDummy(World worldIn) {
		super(worldIn);
	}

	@Override
	public float getBaseHealth() {
		return CQRConfig.baseHealths.Dummy;
	}

	@Override
	public EDefaultFaction getDefaultFaction() {
		return EDefaultFaction.ALL_ALLY;
	}

	@Override
	protected void initEntityAI() {

	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {

	}

	@Override
	protected ResourceLocation getLootTable() {
		return LootTableList.EMPTY;
	}

}