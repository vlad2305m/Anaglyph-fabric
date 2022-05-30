package vlad2305m.anaglyphfabric.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vlad2305m.anaglyphfabric.client.AnaglyphConfig;

import static vlad2305m.anaglyphfabric.client.Util.renderTwice;

@Mixin(GameRenderer.class)
public abstract class Anaglyph {

    @Redirect(method = "Lnet/minecraft/client/render/GameRenderer;renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V"))
    public void renderWorldTwice(WorldRenderer instance, MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix) {
        double d = getFov(camera, tickDelta, true);
        renderTwice(matrices, ()->{
            instance.render(matrices, tickDelta, limitTime, renderBlockOutline, camera, gameRenderer, lightmapTextureManager, positionMatrix);
        }, (f)->camera.moveBy(0,0,f));

        instance.setupFrustum(matrices, camera.getPos(), getBasicProjectionMatrix(Math.max(d, client.options.fov)));

    }

    /*@Redirect(method = "Lnet/minecraft/client/render/GameRenderer;renderHand(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/Camera;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"))
    public void renderHandTwice(HeldItemRenderer instance, float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light){
        renderTwice(matrices, ()->{
            instance.renderItem(tickDelta, matrices, buffers.getEntityVertexConsumers(), client.player, client.getEntityRenderDispatcher().getLight(client.player, tickDelta));
            //reset transform
            float h = MathHelper.lerp(tickDelta, player.lastRenderPitch, player.renderPitch);
            float i = MathHelper.lerp(tickDelta, player.lastRenderYaw, player.renderYaw);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-(player.getYaw(tickDelta) - i) * 0.1F));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-(player.getPitch(tickDelta) - h) * 0.1F));


        }, (f)->matrices.translate(0,0,-f));
    }*/

    @Mixin(InGameHud.class)
    public static class CrosshairAnaglyph {
        @Redirect(method = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderCrosshair(Lnet/minecraft/client/util/math/MatrixStack;)V"))
        public void renderCrosshairTwice(InGameHud instance, MatrixStack matrices){
            if (AutoConfig.getConfigHolder(AnaglyphConfig.class).getConfig().renderCrosshair) renderCrosshair(matrices);
            //renderTwice(matrices, ()->{
            //    renderCrosshair(matrices);
            //}, (f)->matrices.translate(0,0,-f));
        }
        @Shadow private void renderCrosshair(MatrixStack matrices) {}
    }

    @Shadow public Matrix4f getBasicProjectionMatrix(double max) {return null;}
    @Shadow private double getFov(Camera camera, float tickDelta, boolean b) {return 0;}
    @Shadow @Final private MinecraftClient client;
}
