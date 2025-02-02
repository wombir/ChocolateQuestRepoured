package team.cqr.cqrepoured.client.render.entity.mobs;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.inventory.EquipmentSlotType;
import team.cqr.cqrepoured.client.model.entity.mobs.ModelCQRGremlin;
import team.cqr.cqrepoured.client.render.entity.RenderCQREntity;
import team.cqr.cqrepoured.entity.mobs.EntityCQRGremlin;

public class RenderCQRGremlin extends RenderCQREntity<EntityCQRGremlin> {

	public RenderCQRGremlin(EntityRendererManager rendermanagerIn) {
		super(rendermanagerIn, new ModelCQRGremlin(), 0.5F, "mob/gremlin", 1.0D, 1.0D);
	}

	@Override
	public void setupHeadOffsets(ModelRenderer modelRenderer, EquipmentSlotType slot) {
		this.applyRotations(modelRenderer);
		GlStateManager.translate(0.0D, 0.25D, 0.0D);
		this.resetRotations(modelRenderer);
	}

	@Override
	public void setupRightArmOffsets(ModelRenderer modelRenderer, EquipmentSlotType slot) {
		this.applyRotations(modelRenderer);
		GlStateManager.translate(-0.0625D, 0.0D, 0.0D);
		this.resetRotations(modelRenderer);
	}

	@Override
	public void setupLeftArmOffsets(ModelRenderer modelRenderer, EquipmentSlotType slot) {
		this.applyRotations(modelRenderer);
		GlStateManager.translate(0.0625D, 0.0D, 0.0D);
		this.resetRotations(modelRenderer);
	}

	@Override
	public void setupRightLegOffsets(ModelRenderer modelRenderer, EquipmentSlotType slot) {
		if (slot == EquipmentSlotType.LEGS) {
			this.applyTranslations(modelRenderer);
			this.applyRotations(modelRenderer);
			GlStateManager.translate(-0.125D, 0.0D, 0.0D);
			GlStateManager.scale(1.0D, 0.5D, 1.0D);
			this.resetRotations(modelRenderer);
			this.resetTranslations(modelRenderer);
		} else if (slot == EquipmentSlotType.FEET) {
			this.applyTranslations(modelRenderer);
			this.applyRotations(modelRenderer);
			GlStateManager.translate(-0.125D, -0.225D, 0.0D);
			GlStateManager.scale(1.0D, 0.8D, 1.0D);
			this.resetRotations(modelRenderer);
			this.resetTranslations(modelRenderer);
		}
	}

	@Override
	public void setupLeftLegOffsets(ModelRenderer modelRenderer, EquipmentSlotType slot) {
		if (slot == EquipmentSlotType.LEGS) {
			this.applyTranslations(modelRenderer);
			this.applyRotations(modelRenderer);
			GlStateManager.translate(0.125D, 0.0D, 0.0D);
			GlStateManager.scale(1.0D, 0.5D, 1.0D);
			this.resetRotations(modelRenderer);
			this.resetTranslations(modelRenderer);
		} else if (slot == EquipmentSlotType.FEET) {
			this.applyTranslations(modelRenderer);
			this.applyRotations(modelRenderer);
			GlStateManager.translate(0.125D, -0.225D, 0.0D);
			GlStateManager.scale(1.0D, 0.8D, 1.0D);
			this.resetRotations(modelRenderer);
			this.resetTranslations(modelRenderer);
		}
	}

	@Override
	public void setupPotionOffsets(ModelRenderer modelRenderer) {
		GlStateManager.translate(0.0F, -0.225F, 0.0F);
	}

}
