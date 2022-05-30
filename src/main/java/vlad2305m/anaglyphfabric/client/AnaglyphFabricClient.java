package vlad2305m.anaglyphfabric.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class AnaglyphFabricClient implements ClientModInitializer {
    public void onInitializeClient(){
        AutoConfig.register(AnaglyphConfig.class, JanksonConfigSerializer::new);
    }
}
