package com.teamcqr.chocolatequestrepoured.client.render.entity;

import java.util.ArrayList;
import java.util.List;

import com.teamcqr.chocolatequestrepoured.client.models.entities.ModelCQRGremlin;
import com.teamcqr.chocolatequestrepoured.objects.entity.mobs.EntityCQRGoblin;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class RenderCQRGoblin extends RenderCQREntity<EntityCQRGoblin> {

	public RenderCQRGoblin(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelCQRGremlin(0F), 0.5F, "entity_mob_cqrgoblin", 1.0D, 1.0D);

		List<LayerRenderer<?>> toRemove = new ArrayList<LayerRenderer<?>>();
		for (LayerRenderer<?> layer : this.layerRenderers) {
			if (layer instanceof LayerBipedArmor) {
				toRemove.add(layer);
			}
		}
		for (LayerRenderer<?> layer : toRemove) {
			this.layerRenderers.remove(layer);
		}

	}

	@Override
	protected void renderModel(EntityCQRGoblin entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
	}

}
