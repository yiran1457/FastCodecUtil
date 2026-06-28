package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.floats.Float2FloatArrayMap;
import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Float2FloatMapCodec() implements IMapCodec<Float, Float, Float2FloatMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Float> keyCodec() {
        return Codec.FLOAT;
    }

    @Override
    public Codec<Float> elementCodec() {
        return Codec.FLOAT;
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
    public StreamCodec<ByteBuf, Float> getElementStreamCodec() {
        return ByteBufCodecs.FLOAT;
    }

    @Override
    public Float2FloatMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Float2FloatArrayMap() : new Float2FloatOpenHashMap();
    }
}
