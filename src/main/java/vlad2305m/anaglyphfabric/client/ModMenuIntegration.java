package vlad2305m.anaglyphfabric.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@SuppressWarnings({"unused", "overrides", "deprecated"})
@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi
{

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> AutoConfig.getConfigScreen(AnaglyphConfig.class, screen).get();
    }

}