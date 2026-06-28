package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.floats.Float2IntArrayMap;
import it.unimi.dsi.fastutil.floats.Float2IntMap;
import it.unimi.dsi.fastutil.floats.Float2IntOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Float2IntMapCodec() implements IMapCodec<Float, Integer, Float2IntMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Float> keyCodec() {
        return Codec.FLOAT;
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
    public StreamCodec<ByteBuf, Float> getKeyStreamCodec() {
        return ByteBufCodecs.FLOAT;
    }

    @Override
    public StreamCodec<ByteBuf, Integer> getElementStreamCodec() {
        return ByteBufCodecs.INT;
    }

    @Override
    public Float2IntMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Float2IntArrayMap() : new Float2IntOpenHashMap();
    }
}
