package vlad2305m.anaglyphfabric.client;

import it.unimi.dsi.fastutil.floats.FloatConsumer;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public class Util {
    private static final Vector3f pY = new Vector3f(0,1,0);
    private static final float dtr = (float) (Math.PI/180);
    public static void renderTwice(MatrixStack matrices, Runnable render, FloatConsumer translate, Quaternionf camRotation) {
        AnaglyphConfig config = AutoConfig.getConfigHolder(AnaglyphConfig.class).getConfig();
        boolean anaglyphMode = config.anaglyphMode;
        if (anaglyphMode) {
            float eyeDistance = config.eyeDistance;
            float halfAngle = config.angle*dtr/2;
            boolean doAngle = config.enableAngle && halfAngle != 0d;
            FloatConsumer rotateAndRender = doAngle ? (angle)->{
                Vector3f rotation = new Vector3f(pY).rotate(camRotation);
                matrices.multiply(new Quaternionf(new AxisAngle4f(angle, rotation)));
                render.run();
                matrices.multiply(new Quaternionf(new AxisAngle4f(-angle, rotation)));
            } : (f)->render.run();

            // Left eye
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glColorMask(!config.rightR, !config.rightG, !config.rightB, true);
            translate.accept(eyeDistance/2);
            rotateAndRender.accept(-halfAngle);

            // Right eye
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glColorMask(config.rightR, config.rightG, config.rightB, true);
            translate.accept(-eyeDistance);
            rotateAndRender.accept(halfAngle);

            //Reset
            GL11.glColorMask(true,true,true, true);
            translate.accept(eyeDistance/2);
        } else {
            render.run();
        }
    }
}
