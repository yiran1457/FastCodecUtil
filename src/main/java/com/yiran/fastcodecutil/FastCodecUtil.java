package com.yiran.fastcodecutil;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(FastCodecUtil.MODID)
public class FastCodecUtil {
    public static final String MODID = "fast_codec_util";
    public static long CapChoiceFactor = 32;

    public FastCodecUtil(IEventBus modEventBus, ModContainer modContainer) {
    }

}
