package cn.ksmcbrigade.sk;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(SuicideKey.MODID)
@Mod.EventBusSubscriber(modid = SuicideKey.MODID)
public class SuicideKey {

    public static final String MODID = "sk";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID,"suicide"),()->"345",(a)->true,(b)->true);

    public SuicideKey() {
        MinecraftForge.EVENT_BUS.register(this);

        CHANNEL.registerMessage(0,netMessage.class,netMessage::encode,netMessage::decode,(msg,context)->{
            ServerPlayer player = context.get().getSender();
            if(player!=null && (!player.isCreative() && !player.isSpectator())){
                player.kill();
            }
            context.get().setPacketHandled(true);
        });
    }

    public static class netMessage {

        public String message;

        public netMessage(String message){
            this.message = message;
        }

        public static void encode(netMessage MSG, FriendlyByteBuf code){
            code.writeUtf(MSG.message);
        }

        public static netMessage decode(FriendlyByteBuf code){
            return new netMessage(code.readUtf());
        }
    }
}
