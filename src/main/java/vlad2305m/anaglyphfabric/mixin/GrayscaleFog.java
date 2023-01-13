package vlad2305m.anaglyphfabric.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.autoconfig.AutoConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import vlad2305m.anaglyphfabric.client.AnaglyphConfig;

@Mixin(RenderSystem.class)
public class GrayscaleFog {

    private static float average(float r, float g, float b) {return 0.3f*r + 0.59f*g + 0.11f*b;}
    @ModifyArgs(method = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_clearColor(FFFF)V"))
    private static void clearBW(Args args, float r, float g, float b, float a){
        if(AutoConfig.getConfigHolder(AnaglyphConfig.class).getConfig().colorlessFog) {
            float w = average(r, g, b);
            args.set(0, w);
            args.set(1, w);
            args.set(2, w);
        }
    }

    @ModifyArgs(method = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogColor(FFFF)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;_setShaderFogColor(FFFF)V"))
    private static void setShaderFogBW(Args args, float r, float g, float b, float a){
        if(AutoConfig.getConfigHolder(AnaglyphConfig.class).getConfig().colorlessFog) {
            float w = average(r, g, b);
            args.set(0, w);
            args.set(1, w);
            args.set(2, w);
        }
    }

    @ModifyArgs(method = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;_setShaderColor(FFFF)V"))
    private static void setShaderBW(Args args, float r, float g, float b, float a){
        if(AutoConfig.getConfigHolder(AnaglyphConfig.class).getConfig().colorlessFog) {
            float w = average(r, g, b);
            args.set(0, w);
            args.set(1, w);
            args.set(2, w);
        }
    }

}
