package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.floats.Float2BooleanArrayMap;
import it.unimi.dsi.fastutil.floats.Float2BooleanMap;
import it.unimi.dsi.fastutil.floats.Float2BooleanOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Float2BooleanMapCodec() implements IMapCodec<Float, Boolean, Float2BooleanMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Float> keyCodec() {
        return Codec.FLOAT;
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
    public StreamCodec<ByteBuf, Float> getKeyStreamCodec() {
        return ByteBufCodecs.FLOAT;
    }

    @Override
    public StreamCodec<ByteBuf, Boolean> getElementStreamCodec() {
        return ByteBufCodecs.BOOL;
    }

    @Override
    public Float2BooleanMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Float2BooleanArrayMap() : new Float2BooleanOpenHashMap();
    }
}
