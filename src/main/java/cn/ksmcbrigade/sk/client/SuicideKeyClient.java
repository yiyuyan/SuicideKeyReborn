package cn.ksmcbrigade.sk.client;

import cn.ksmcbrigade.sk.SuicideKey;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SuicideKey.MODID,value = Dist.CLIENT)
public class SuicideKeyClient {
    public static final KeyMapping key = new KeyMapping("key.sk.suicide", KeyConflictContext.GUI, InputConstants.Type.KEYSYM,InputConstants.KEY_BACKSPACE,KeyMapping.CATEGORY_MISC);

    @SubscribeEvent
    public static void registerKey(final RegisterKeyMappingsEvent event){
        event.register(key);
    }

    @SubscribeEvent
    public static void keyInput(InputEvent.Key event){
        if(event.getKey()==key.getKey().getValue()){
            SuicideKey.CHANNEL.sendToServer(new SuicideKey.netMessage("suicide"));
        }
    }
}
