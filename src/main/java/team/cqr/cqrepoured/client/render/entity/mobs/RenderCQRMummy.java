package team.cqr.cqrepoured.client.render.entity.mobs;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import team.cqr.cqrepoured.client.render.entity.RenderCQREntity;
import team.cqr.cqrepoured.entity.mobs.EntityCQRMummy;

public class RenderCQRMummy extends RenderCQREntity<EntityCQRMummy> {

	public RenderCQRMummy(EntityRendererManager rendermanagerIn) {
		super(rendermanagerIn, "mob/mummy", true);
	}

}
