package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.doubles.Double2FloatArrayMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatMap;
import it.unimi.dsi.fastutil.doubles.Double2FloatOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Double2FloatMapCodec() implements IMapCodec<Double, Float, Double2FloatMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Double> keyCodec() {
        return Codec.DOUBLE;
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
    public StreamCodec<ByteBuf, Double> getKeyStreamCodec() {
        return ByteBufCodecs.DOUBLE;
    }

    @Override
    public StreamCodec<ByteBuf, Float> getElementStreamCodec() {
        return ByteBufCodecs.FLOAT;
    }

    @Override
    public Double2FloatMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Double2FloatArrayMap() : new Double2FloatOpenHashMap();
    }

}
