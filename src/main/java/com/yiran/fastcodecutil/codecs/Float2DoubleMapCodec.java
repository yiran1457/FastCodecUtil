package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.floats.Float2DoubleArrayMap;
import it.unimi.dsi.fastutil.floats.Float2DoubleMap;
import it.unimi.dsi.fastutil.floats.Float2DoubleOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Float2DoubleMapCodec() implements IMapCodec<Float, Double, Float2DoubleMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Float> keyCodec() {
        return Codec.FLOAT;
    }

    @Override
    public Codec<Double> elementCodec() {
        return Codec.DOUBLE;
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
    public StreamCodec<ByteBuf, Double> getElementStreamCodec() {
        return ByteBufCodecs.DOUBLE;
    }

    @Override
    public Float2DoubleMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Float2DoubleArrayMap() : new Float2DoubleOpenHashMap();
    }
}
