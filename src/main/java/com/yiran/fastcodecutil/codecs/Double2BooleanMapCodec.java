package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.doubles.Double2BooleanArrayMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Double2BooleanMapCodec() implements IMapCodec<Double, Boolean, Double2BooleanMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Double> keyCodec() {
        return Codec.DOUBLE;
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
    public StreamCodec<ByteBuf, Double> getKeyStreamCodec() {
        return ByteBufCodecs.DOUBLE;
    }

    @Override
    public StreamCodec<ByteBuf, Boolean> getElementStreamCodec() {
        return ByteBufCodecs.BOOL;
    }

    @Override
    public Double2BooleanMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Double2BooleanArrayMap() : new Double2BooleanOpenHashMap();
    }

}
