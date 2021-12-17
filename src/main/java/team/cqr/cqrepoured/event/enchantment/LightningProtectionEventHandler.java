package team.cqr.cqrepoured.event.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.cqr.cqrepoured.CQRMain;
import team.cqr.cqrepoured.init.CQREnchantments;

@EventBusSubscriber(modid = CQRMain.MODID)
public class LightningProtectionEventHandler {

	@SubscribeEvent
	public static void onStruckByLightning(EntityStruckByLightningEvent event) {
		if (event.getEntity() instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) event.getEntity();
			ItemStack helmet = living.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

			int lvl = EnchantmentHelper.getEnchantmentLevel(CQREnchantments.LIGHTNING_PROTECTION, helmet);
			if (lvl > 0 && lvl > living.getRNG().nextInt(10)) {
				event.setCanceled(true);
			}
		}
	}

}