package team.cqr.cqrepoured.client.render.entity.boss;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import team.cqr.cqrepoured.client.render.entity.RenderCQREntity;
import team.cqr.cqrepoured.entity.boss.EntityCQRPirateCaptain;

public class RenderCQRPirateCaptain extends RenderCQREntity<EntityCQRPirateCaptain> {

	public RenderCQRPirateCaptain(EntityRendererManager rendermanagerIn) {
		super(rendermanagerIn, "boss/pirate_captain", true);
	}

}
