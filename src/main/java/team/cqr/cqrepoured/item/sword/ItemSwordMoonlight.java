package team.cqr.cqrepoured.item.sword;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import team.cqr.cqrepoured.item.IEquipListener;

public class ItemSwordMoonlight extends ItemSword implements IEquipListener {

	protected static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("89ADDE87-B021-417C-9112-2E2C94CBB0D1");
	private static final double DAMAGE_BONUS = 3.0D;

	public ItemSwordMoonlight(ToolMaterial material) {
		super(material);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		if (!(entityIn instanceof EntityLivingBase)) {
			return;
		}
		if (!isSelected) {
			return;
		}
		IAttributeInstance attribute = ((EntityLivingBase) entityIn).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		if (!worldIn.isDaytime()) {
			if (attribute.getModifier(ATTACK_DAMAGE_MODIFIER) != null) {
				return;
			}
			attribute.applyModifier(new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "moonlight_damage_bonus", DAMAGE_BONUS, 0).setSaved(false));
		} else {
			attribute.removeModifier(ATTACK_DAMAGE_MODIFIER);
		}
	}

	@Override
	public void onEquip(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot) {

	}

	@Override
	public void onUnequip(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot) {
		IAttributeInstance attribute = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		attribute.removeModifier(ATTACK_DAMAGE_MODIFIER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			tooltip.add(TextFormatting.BLUE + I18n.format("description.moonlight.name"));
		} else {
			tooltip.add(TextFormatting.BLUE + I18n.format("description.click_shift.name"));
		}

		tooltip.add(TextFormatting.DARK_AQUA + "+3 " + I18n.format("description.attack_damage_at_night.name"));
	}

}
