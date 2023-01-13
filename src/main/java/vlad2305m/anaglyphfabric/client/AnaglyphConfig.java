package vlad2305m.anaglyphfabric.client;

import blue.endless.jankson.Comment;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "anaglyph-fabric")
@Config.Gui.Background("minecraft:textures/block/gray_stained_glass.png")
public class AnaglyphConfig implements ConfigData {
    public boolean anaglyphMode = true;
    public boolean colorlessFog = true;
    public float eyeDistance = 1f/16;
    public boolean enableAngle = true;
    @Comment("formula: -2*arctan([distance between your eyes]/[distance to the monitor]/2)                      (yes, it is negative => hard to focus but very cool, near-VR experience)")
    public float angle = -4f;
    public boolean rightR = false;
    public boolean rightG = true;
    public boolean rightB = true;
    @Comment("Crosshair is infinitely away. Distracting.")
    public boolean renderCrosshair = false;
}
