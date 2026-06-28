package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.doubles.Double2IntArrayMap;
import it.unimi.dsi.fastutil.doubles.Double2IntMap;
import it.unimi.dsi.fastutil.doubles.Double2IntOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Double2IntMapCodec() implements IMapCodec<Double, Integer, Double2IntMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Double> keyCodec() {
        return Codec.DOUBLE;
    }

    @Override
    public Codec<Integer> elementCodec() {
        return Codec.INT;
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
    public StreamCodec<ByteBuf, Integer> getElementStreamCodec() {
        return ByteBufCodecs.INT;
    }

    @Override
    public Double2IntMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Double2IntArrayMap() : new Double2IntOpenHashMap();
    }

}
