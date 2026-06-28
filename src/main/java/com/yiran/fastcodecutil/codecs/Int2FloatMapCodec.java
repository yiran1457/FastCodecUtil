package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2FloatArrayMap;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Int2FloatMapCodec() implements IMapCodec<Integer, Float, Int2FloatMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Integer> keyCodec() {
        return Codec.INT;
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
    public StreamCodec<ByteBuf, Integer> getKeyStreamCodec() {
        return ByteBufCodecs.INT;
    }

    @Override
    public StreamCodec<ByteBuf, Float> getElementStreamCodec() {
        return ByteBufCodecs.FLOAT;
    }

    @Override
    public Int2FloatMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Int2FloatArrayMap() : new Int2FloatOpenHashMap();
    }

}
