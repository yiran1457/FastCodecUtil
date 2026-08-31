package com.yiran.fastcodecutil;

import com.yiran.fastcodecutil.api.FastMultimapUtil;
import com.yiran.fastcodecutil.codecs.stream.ListMultimapStreamCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(FastCodecUtil.MODID)
public class FastCodecUtil {
    public static final String MODID = "fast_codec_util";
    public static long CapChoiceFactor = 8;

    public FastCodecUtil(IEventBus modEventBus, ModContainer modContainer) {
    }

}
