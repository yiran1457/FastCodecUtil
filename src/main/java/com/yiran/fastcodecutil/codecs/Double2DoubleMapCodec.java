package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.doubles.*;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Double2DoubleMapCodec() implements IMapCodec<Double, Double, Double2DoubleMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Double> keyCodec() {
        return Codec.DOUBLE;
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
    public StreamCodec<ByteBuf, Double> getKeyStreamCodec() {
        return ByteBufCodecs.DOUBLE;
    }

    @Override
    public StreamCodec<ByteBuf, Double> getElementStreamCodec() {
        return ByteBufCodecs.DOUBLE;
    }

    @Override
    public Double2DoubleMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Double2DoubleArrayMap() : new Double2DoubleOpenHashMap();
    }

}
