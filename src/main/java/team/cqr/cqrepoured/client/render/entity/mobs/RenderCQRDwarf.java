package team.cqr.cqrepoured.client.render.entity.mobs;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import team.cqr.cqrepoured.client.render.entity.RenderCQREntity;
import team.cqr.cqrepoured.entity.mobs.EntityCQRDwarf;

public class RenderCQRDwarf extends RenderCQREntity<EntityCQRDwarf> {

	public RenderCQRDwarf(EntityRendererManager rendermanagerIn) {
		super(rendermanagerIn, "mob/dwarf", 0.9D, 0.65D, true);
	}

}
