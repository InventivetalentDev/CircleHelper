package org.inventivetalent.circlehelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class RenderListener {

	public static final double CUBE_RADIUS = 0.4;
	public static final double TWO_PI      = Math.PI * 2;

	private CircleHelperMod mod;

	int particleTick = 0;

	public RenderListener(CircleHelperMod mod) {
		this.mod = mod;
	}

	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if (!ShapeManager.visible) {
			return;
		}

		Minecraft mc = Minecraft.getMinecraft();

		particleTick++;

		if (particleTick == 1) {
			mc.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, true, ShapeManager.center.x + .5, ShapeManager.center.y + .5, ShapeManager.center.z + .5, 0, 0, 0);
		}

		if (particleTick >= 20) {
			//						for (int i = 0; i < ShapeManager.currentPositions.size(); i++) {
			//							Pos pos = ShapeManager.currentPositions.get(i);
			//							mc.world.spawnParticle(EnumParticleTypes.FLAME, false, pos.x + .5, pos.y + .5, pos.z + .5, 0, 0, 0);
			//						}

			particleTick = 0;
		}

		//		int size = ShapeManager.currentPositions.size();
		//		if ((size == 0 && particleTick > 20) || particleTick >= size) {
		//			particleTick = 0;
		//		} else {
		//			Pos pos = ShapeManager.currentPositions.get(particleTick);
		//			mc.world.spawnParticle(EnumParticleTypes.FLAME, true, pos.x, pos.y, pos.z, 0, 0, 0);
		//		}

		GL11.glPushMatrix();

		//		GL11.glClearColor(0.0f,0.0f,0.0f,0.0f);

		double partialTicks = event.getPartialTicks();
		double d0 = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double) partialTicks;
		double d1 = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double) partialTicks;
		double d2 = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double) partialTicks;

		GlStateManager.translate(-d0, -d1, -d2);

		//		RayTraceResult res = new RayTraceResult(new Vec3d(x, y, z), EnumFacing.UP, new BlockPos(x, y, z));
		//event.getContext().drawSelectionBox(mc.player,res,1,event.getPartialTicks());

		int x = 20;
		int y = 50;
		int z = 20;

		GL11.glColor4f(1f, 1f, 0f, .2f);

		Tessellator tessy = Tessellator.getInstance();
		BufferBuilder buf = tessy.getBuffer();

		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.disableCull();
		GlStateManager.disableLighting();

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		//		GlStateManager.translate(x, y, z);

		GlStateManager.depthMask(false);
		GlStateManager.alphaFunc(GL11.GL_LESS, 1.0F);

		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

		for (int i = 0; i < ShapeManager.currentPositions.size(); i++) {
			Pos pos = ShapeManager.currentPositions.get(i);

			drawCube(buf, pos.x + 0.5f, pos.y + 0.5f, pos.z + 0.5f);
		}

		tessy.draw();

		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();

		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.disableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
		GL11.glPopMatrix();

	}

	// https://stackoverflow.com/questions/34388662/how-do-i-draw-a-cube-in-lwjgl
	void drawCube(BufferBuilder buf, float x, float y, float z) {

		//south side [pos z] [parent x]
		buf.pos(x - CUBE_RADIUS, y + CUBE_RADIUS, z + CUBE_RADIUS).endVertex();
		buf.pos(x - CUBE_RADIUS, y - CUBE_RADIUS, z + CUBE_RADIUS).endVertex();
		buf.pos(x + CUBE_RADIUS, y - CUBE_RADIUS, z + CUBE_RADIUS).endVertex();
		buf.pos(x + CUBE_RADIUS, y + CUBE_RADIUS, z + CUBE_RADIUS).endVertex();

		//north side [neg z] [parent x]
		buf.pos(x - CUBE_RADIUS, y + CUBE_RADIUS, z - CUBE_RADIUS).endVertex();
		buf.pos(x - CUBE_RADIUS, y - CUBE_RADIUS, z - CUBE_RADIUS).endVertex();
		buf.pos(x + CUBE_RADIUS, y - CUBE_RADIUS, z - CUBE_RADIUS).endVertex();
		buf.pos(x + CUBE_RADIUS, y + CUBE_RADIUS, z - CUBE_RADIUS).endVertex();

		//east side [pos x] [parent z]
		buf.pos(x + CUBE_RADIUS, y + CUBE_RADIUS, z - CUBE_RADIUS).endVertex();
		buf.pos(x + CUBE_RADIUS, y - CUBE_RADIUS, z - CUBE_RADIUS).endVertex();
		buf.pos(x + CUBE_RADIUS, y - CUBE_RADIUS, z + CUBE_RADIUS).endVertex();
		buf.pos(x + CUBE_RADIUS, y + CUBE_RADIUS, z + CUBE_RADIUS).endVertex();

		//west side [neg x] [parent z]
		buf.pos(x - CUBE_RADIUS, y - CUBE_RADIUS, z + CUBE_RADIUS).endVertex();
		buf.pos(x - CUBE_RADIUS, y - CUBE_RADIUS, z - CUBE_RADIUS).endVertex();
		buf.pos(x - CUBE_RADIUS, y + CUBE_RADIUS, z - CUBE_RADIUS).endVertex();
		buf.pos(x - CUBE_RADIUS, y + CUBE_RADIUS, z + CUBE_RADIUS).endVertex();

		//top [pos y] [parent x & y]
		buf.pos(x + CUBE_RADIUS, y + CUBE_RADIUS, z - CUBE_RADIUS).endVertex();
		buf.pos(x + CUBE_RADIUS, y + CUBE_RADIUS, z + CUBE_RADIUS).endVertex();
		buf.pos(x - CUBE_RADIUS, y + CUBE_RADIUS, z + CUBE_RADIUS).endVertex();
		buf.pos(x - CUBE_RADIUS, y + CUBE_RADIUS, z - CUBE_RADIUS).endVertex();

		//bottom [neg y] [parent x & y]
		buf.pos(x + CUBE_RADIUS, y - CUBE_RADIUS, z - CUBE_RADIUS).endVertex();
		buf.pos(x + CUBE_RADIUS, y - CUBE_RADIUS, z + CUBE_RADIUS).endVertex();
		buf.pos(x - CUBE_RADIUS, y - CUBE_RADIUS, z + CUBE_RADIUS).endVertex();
		buf.pos(x - CUBE_RADIUS, y - CUBE_RADIUS, z - CUBE_RADIUS).endVertex();

	}

}
