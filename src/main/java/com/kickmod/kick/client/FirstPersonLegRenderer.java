package com.kickmod.kick.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
//? if >=1.21.9 {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?} else {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?}
//? if >=1.21.11 {
import net.minecraft.client.renderer.rendertype.RenderTypes;
//?} else {
/*import net.minecraft.client.renderer.RenderType;
*///?}
//? if >=26.3 {
import net.minecraft.client.renderer.texture.UvMapping;
//?} else if >=1.21.9 {
/*import net.minecraft.client.renderer.texture.TextureAtlasSprite;
*///?}

/**
 * Draws the local player's right leg rising from the bottom of the screen during a kick (first person).
 * The leg is a private copy baked from the vanilla player model layer, drawn with the player's skin.
 */
public final class FirstPersonLegRenderer {

	/** Hip position in camera space (blocks): right of centre, below and slightly ahead of the eyes. */
	private static final float HIP_X = 0.15f;
	private static final float HIP_Y = -0.55f;
	private static final float HIP_Z = -0.55f;
	/** In first person the swing goes a bit higher than in third person, so the foot comes into view. */
	private static final float FIRST_PERSON_SWING = 1.3f;

	private static ModelPart leg;
	//? if <1.21.2 {
	/*private static ModelPart pants;
	*///?}

	private FirstPersonLegRenderer() {}

	//? if >=1.21.9 {
	public static void render(PoseStack pose, SubmitNodeCollector out, int light, float partialTick) {
	//?} else {
	/*public static void render(PoseStack pose, MultiBufferSource out, int light, float partialTick) {
	*///?}
		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;
		if (player == null) return;
		float progress = KickAnimations.progress(player.getId(), partialTick);
		if (progress <= 0f) return;
		if (leg == null) bake(mc);

		pose.pushPose();
		pose.translate(HIP_X, HIP_Y, HIP_Z);
		pose.scale(-1f, -1f, 1f); // model space is upside down: flip it like entity rendering does
		leg.x = 0f;
		leg.y = 0f;
		leg.z = 0f;
		leg.xRot = KickCurve.legPitch(progress) * FIRST_PERSON_SWING;
		leg.yRot = 0f;
		leg.zRot = 0f;
		Identifier skin = skin(player);
		//? if >=26.3 {
		out.submitModelPart(leg, pose, RenderTypes.entityTranslucent(skin), light, OverlayTexture.NO_OVERLAY, (UvMapping) null);
		//?} else if >=1.21.11 {
		/*out.submitModelPart(leg, pose, RenderTypes.entityTranslucent(skin), light, OverlayTexture.NO_OVERLAY, (TextureAtlasSprite) null);
		*///?} else if >=1.21.9 {
		/*out.submitModelPart(leg, pose, RenderType.entityTranslucent(skin), light, OverlayTexture.NO_OVERLAY, (TextureAtlasSprite) null);
		*///?} else {
		/*var buffer = out.getBuffer(RenderType.entityTranslucent(skin));
		leg.render(pose, buffer, light, OverlayTexture.NO_OVERLAY);
		*///?}
		//? if <1.21.2 {
		/*pants.copyFrom(leg);
		pants.render(pose, buffer, light, OverlayTexture.NO_OVERLAY);
		*///?}
		pose.popPose();
		if (KickSelfTest.ENABLED) KickSelfTest.firstPersonLegFrames++;
	}

	private static void bake(Minecraft mc) {
		ModelPart root = mc.getEntityModels().bakeLayer(ModelLayers.PLAYER);
		leg = root.getChild("right_leg"); // 1.21.2+: the pants layer is a child of the leg
		//? if <1.21.2 {
		/*pants = root.getChild("right_pants");
		*///?}
	}

	private static Identifier skin(LocalPlayer player) {
		//? if >=1.21.9 {
		return player.getSkin().body().texturePath();
		//?} else if >=1.20.2 {
		/*return player.getSkin().texture();
		*///?} else {
		/*return player.getSkinTextureLocation();
		*///?}
	}
}
