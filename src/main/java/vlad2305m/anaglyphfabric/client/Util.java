package vlad2305m.anaglyphfabric.client;

import it.unimi.dsi.fastutil.floats.FloatConsumer;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import org.lwjgl.opengl.GL11;

public class Util {
    public static void renderTwice(MatrixStack matrices, Runnable render, FloatConsumer translate) {
        AnaglyphConfig config = AutoConfig.getConfigHolder(AnaglyphConfig.class).getConfig();
        boolean anaglyphMode = config.anaglyphMode;
        if (anaglyphMode) {
            float eyeDistance = config.eyeDistance;
            float angle = config.angle;
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glColorMask(!config.rightR, !config.rightG, !config.rightB, true);
            translate.accept(eyeDistance/2);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-angle/2));
            render.run();
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(angle/2));

            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

            GL11.glColorMask(config.rightR, config.rightG, config.rightB, true);
            translate.accept(-eyeDistance);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(angle/2));
            render.run();
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-angle/2));

            GL11.glColorMask(true,true,true, true);
            translate.accept(eyeDistance/2);
        } else {
            render.run();
        }
    }
}
