package com.kickmod.kick.net;

import com.kickmod.kick.KickRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
//? if >=1.20.5 {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
//?}
//? if >=1.20.5 || neoforge {
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//?}

/** Client to server: "I kicked entity {@code targetId}" ({@link #AIR} = kicked the air). */
//? if >=1.20.5 {
public record KickRequest(int targetId) implements CustomPacketPayload {
	public static final Identifier ID = KickRegistry.id("request");
	public static final Type<KickRequest> TYPE = new Type<>(ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, KickRequest> STREAM_CODEC =
		StreamCodec.composite(ByteBufCodecs.VAR_INT, KickRequest::targetId, KickRequest::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
//?} else if neoforge {
/*// NeoForge 1.20.4: the pre-1.20.5 payload interface (write + id)
public record KickRequest(int targetId) implements CustomPacketPayload {
	public static final Identifier ID = KickRegistry.id("request");

	@Override
	public Identifier id() {
		return ID;
	}
*///?} else {
/*public record KickRequest(int targetId) {
	public static final Identifier ID = KickRegistry.id("request");
*///?}

	public static final int AIR = -1;

	public void write(FriendlyByteBuf buf) {
		buf.writeVarInt(targetId);
	}

	public static KickRequest read(FriendlyByteBuf buf) {
		return new KickRequest(buf.readVarInt());
	}
}
