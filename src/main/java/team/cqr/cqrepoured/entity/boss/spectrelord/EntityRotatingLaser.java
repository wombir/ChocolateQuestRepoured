package team.cqr.cqrepoured.entity.boss.spectrelord;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import team.cqr.cqrepoured.entity.boss.AbstractEntityLaser;

public class EntityRotatingLaser extends AbstractEntityLaser {

	private float deltaRotationYawPerTick;
	private float deltaRotationPitchPerTick;

	public EntityRotatingLaser(World worldIn) {
		this(worldIn, null, 4.0F, 1.0F, 0.0F);
	}

	public EntityRotatingLaser(World worldIn, LivingEntity caster, float length, float deltaRotationYawPerTick, float deltaRotationPitchPerTick) {
		super(worldIn, caster, length);
		this.deltaRotationYawPerTick = deltaRotationYawPerTick;
		this.deltaRotationPitchPerTick = deltaRotationPitchPerTick;
	}

	@Override
	public void updatePositionAndRotation() {
		this.rotationYawCQR = MathHelper.wrapDegrees(this.rotationYawCQR + this.deltaRotationYawPerTick);
		this.rotationPitchCQR = MathHelper.wrapDegrees(this.rotationPitchCQR + this.deltaRotationPitchPerTick);
		// TODO reduce unnecessary vec3d creation
		Vector3d vec1 = new Vector3d(this.caster.posX, this.caster.posY + this.caster.height * 0.6D, this.caster.posZ);
		vec1 = vec1.add(this.getOffsetVector());
		Vector3d vec4 = Vector3d.fromPitchYaw(this.rotationPitchCQR, this.rotationYawCQR);
		this.setPosition(vec1.x + vec4.x * 0.25D, vec1.y + vec4.y * 0.25D, vec1.z + vec4.z * 0.25D);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		super.writeSpawnData(buffer);
		buffer.writeFloat(this.deltaRotationYawPerTick);
		buffer.writeFloat(this.deltaRotationPitchPerTick);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		super.readSpawnData(additionalData);
		this.deltaRotationYawPerTick = additionalData.readFloat();
		this.deltaRotationPitchPerTick = additionalData.readFloat();
	}

}
