package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Int2BooleanMapCodec() implements IMapCodec<Integer, Boolean, Int2BooleanMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Integer> keyCodec() {
        return Codec.INT;
    }

    @Override
    public Codec<Boolean> elementCodec() {
        return Codec.BOOL;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public StreamCodec<ByteBuf, Integer> getKeyStreamCodec() {
        return ByteBufCodecs.INT;
    }

    @Override
    public StreamCodec<ByteBuf, Boolean> getElementStreamCodec() {
        return ByteBufCodecs.BOOL;
    }

    @Override
    public Int2BooleanMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Int2BooleanArrayMap() : new Int2BooleanOpenHashMap();
    }

}
