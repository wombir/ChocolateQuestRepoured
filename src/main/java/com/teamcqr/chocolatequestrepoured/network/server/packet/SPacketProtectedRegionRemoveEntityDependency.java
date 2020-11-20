package com.teamcqr.chocolatequestrepoured.network.server.packet;

import java.util.UUID;

import com.teamcqr.chocolatequestrepoured.util.ByteBufUtil;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketProtectedRegionRemoveEntityDependency implements IMessage {

	private UUID uuid;
	private UUID entityUuid;

	public SPacketProtectedRegionRemoveEntityDependency() {

	}

	public SPacketProtectedRegionRemoveEntityDependency(UUID uuid, UUID entityUuid) {
		this.uuid = uuid;
		this.entityUuid = entityUuid;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.uuid = ByteBufUtil.readUuid(buf);
		this.entityUuid = ByteBufUtil.readUuid(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtil.writeUuid(buf, this.uuid);
		ByteBufUtil.writeUuid(buf, this.entityUuid);
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public UUID getEntityUuid() {
		return this.entityUuid;
	}

}
