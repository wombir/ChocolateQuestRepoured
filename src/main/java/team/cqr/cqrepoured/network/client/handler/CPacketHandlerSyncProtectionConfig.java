package team.cqr.cqrepoured.network.client.handler;

import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import team.cqr.cqrepoured.config.CQRConfig;
import team.cqr.cqrepoured.network.AbstractPacketHandler;
import team.cqr.cqrepoured.network.server.packet.SPacketSyncProtectionConfig;
import team.cqr.cqrepoured.world.structure.protection.ProtectedRegionHelper;

public class CPacketHandlerSyncProtectionConfig extends AbstractPacketHandler<SPacketSyncProtectionConfig> {

	@Override
	protected void execHandlePacket(SPacketSyncProtectionConfig message, Supplier<Context> context, World world, PlayerEntity player) {
		CQRConfig.dungeonProtection = message.getProtectionConfig();
		ProtectedRegionHelper.updateWhitelists();
	}

}
