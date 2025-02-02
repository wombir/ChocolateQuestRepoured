package team.cqr.cqrepoured.client.render.projectile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import team.cqr.cqrepoured.client.render.RenderSpriteBase;
import team.cqr.cqrepoured.entity.projectiles.ProjectileHomingEnderEye;

public class RenderProjectileHomingEnderEye extends RenderSpriteBase<ProjectileHomingEnderEye> {

	public RenderProjectileHomingEnderEye(EntityRendererManager renderManager) {
		super(renderManager, new ResourceLocation("textures/items/ender_eye.png"));
	}

	@Override
	public void doRender(ProjectileHomingEnderEye entity, double x, double y, double z, float entityYaw, float partialTicks) {

		// if(entity.ticksExisted % 4 == 0) {
		ClientWorld world = Minecraft.getMinecraft().world;
		double dx = entity.posX + (-0.25 + (0.5 * world.rand.nextDouble()));
		double dy = 0.125 + entity.posY + (-0.25 + (0.5 * world.rand.nextDouble()));
		double dz = entity.posZ + (-0.25 + (0.5 * world.rand.nextDouble()));
		world.spawnParticle(ParticleTypes.DRAGON_BREATH, dx, dy, dz, 0, 0, 0);
		// }

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

}
