package team.cqr.cqrepoured.item.armor;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockRenderType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.input.Keyboard;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import team.cqr.cqrepoured.client.init.CQRArmorModels;
import team.cqr.cqrepoured.util.ItemUtil;

public class ItemArmorSpider extends ArmorItem {

	private AttributeModifier movementSpeed;

	public ItemArmorSpider(ArmorMaterial materialIn, int renderIndexIn, EquipmentSlotType equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);

		this.movementSpeed = new AttributeModifier("SpiderArmorModifier", 0.05D, 2);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

		if (slot == MobEntity.getSlotForItemStack(stack)) {
			multimap.put(Attributes.MOVEMENT_SPEED.getName(), this.movementSpeed);
		}

		return multimap;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			tooltip.add(TextFormatting.BLUE + I18n.format("description.spider_armor.name"));
		} else {
			tooltip.add(TextFormatting.BLUE + I18n.format("description.click_shift.name"));
		}
	}

	@Override
	public void onArmorTick(World world, PlayerEntity player, ItemStack itemStack) {
		if (ItemUtil.hasFullSet(player, ItemArmorSpider.class)) {
			if (player.isSpectator()) {
				return;
			}
			if (player.collidedHorizontally) {
				if (world.isRemote) {
					if (player.moveForward > 0) {
						player.motionY = 0.2D;
						this.createClimbingParticles(player, world);
					} else if (player.isSneaking()) {
						player.motionY = 0.0D;
					} else {
						player.motionY = -0.2D;
					}
				}

				player.onGround = true;
			}
			player.fallDistance = 0F;
			player.jumpMovementFactor += 0.005;
			player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 0, 1, false, false));
		}
	}

	private void createClimbingParticles(PlayerEntity player, World world) {
		int i = (int) player.posX;
		int j = MathHelper.floor(player.getPosition().getY());
		int k = (int) player.posZ;

		int direction = MathHelper.floor((player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (direction == 0) // south
		{
			if (k > 0) {
				k += 1;
			}

			if (i < 0) {
				i -= 1;
			}

			BlockPos blockpos = new BlockPos(i, j, k);
			BlockState iblockstate = world.getBlockState(blockpos);

			if (!iblockstate.getBlock().addRunningEffects(iblockstate, world, blockpos, player)) {
				if (iblockstate.getRenderType() != BlockRenderType.INVISIBLE) {
					world.spawnParticle(ParticleTypes.BLOCK_CRACK, player.posX + (itemRand.nextFloat() - 0.5D) * player.width, player.getEntityBoundingBox().minY + 0.1D, (player.posZ + 0.3) + (itemRand.nextFloat() - 0.5D) * player.width, -player.motionX * 4.0D, 1.5D, -player.motionZ * 4.0D,
							Block.getStateId(iblockstate));
				}
			}
		}

		if (direction == 1) // west
		{
			if (i > 0) {
				i -= 1;
			}

			if (k < 0) {
				k -= 1;
			}

			if (i < 0) {
				i -= 2;
			}

			BlockPos blockpos = new BlockPos(i, j, k);
			BlockState iblockstate = world.getBlockState(blockpos);

			if (!iblockstate.getBlock().addRunningEffects(iblockstate, world, blockpos, player)) {
				if (iblockstate.getRenderType() != BlockRenderType.INVISIBLE) {
					world.spawnParticle(ParticleTypes.BLOCK_CRACK, (player.posX - 0.3) + (itemRand.nextFloat() - 0.5D) * player.width, player.getEntityBoundingBox().minY + 0.1D, player.posZ + (itemRand.nextFloat() - 0.5D) * player.width, -player.motionX * 4.0D, 1.5D, -player.motionZ * 4.0D,
							Block.getStateId(iblockstate));
				}
			}
		}

		if (direction == 2) // north
		{
			if (i < 0) {
				i -= 1;
			}

			if (k > 0) {
				k -= 1;
			}

			if ((i > 0 && k < 0) || (i < 0 && k < 0)) {
				k -= 2;
			}

			BlockPos blockpos = new BlockPos(i, j, k);
			BlockState iblockstate = world.getBlockState(blockpos);

			if (!iblockstate.getBlock().addRunningEffects(iblockstate, world, blockpos, player)) {
				if (iblockstate.getRenderType() != BlockRenderType.INVISIBLE) {
					world.spawnParticle(ParticleTypes.BLOCK_CRACK, player.posX + (itemRand.nextFloat() - 0.5D) * player.width, player.getEntityBoundingBox().minY + 0.1D, (player.posZ - 0.3) + (itemRand.nextFloat() - 0.5D) * player.width, -player.motionX * 4.0D, 1.5D, -player.motionZ * 4.0D,
							Block.getStateId(iblockstate));
				}
			}
		}

		if (direction == 3) // east
		{
			if (i > 0) {
				i += 1;
			}

			if (k < 0) {
				k -= 1;
			}

			BlockPos blockpos = new BlockPos(i, j, k);
			BlockState iblockstate = world.getBlockState(blockpos);

			if (!iblockstate.getBlock().addRunningEffects(iblockstate, world, blockpos, player)) {
				if (iblockstate.getRenderType() != BlockRenderType.INVISIBLE) {
					world.spawnParticle(ParticleTypes.BLOCK_CRACK, (player.posX + 0.3) + (itemRand.nextFloat() - 0.5D) * player.width, player.getEntityBoundingBox().minY + 0.1D, player.posZ + (itemRand.nextFloat() - 0.5D) * player.width, -player.motionX * 4.0D, 1.5D, -player.motionZ * 4.0D,
							Block.getStateId(iblockstate));
				}
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	@Nullable
	public ModelBiped getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, ModelBiped _default) {
		return armorSlot == EquipmentSlotType.LEGS ? CQRArmorModels.spiderArmorLegs : CQRArmorModels.spiderArmor;
	}

}
