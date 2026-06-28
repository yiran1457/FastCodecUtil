package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.doubles.Double2ObjectArrayMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Double2ObjectMapCodec<V>(Codec<V> elementCodec) implements IMapCodec<Double, V, Double2ObjectMap<V>> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Double> keyCodec() {
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
    public Double2ObjectMap<V> getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Double2ObjectArrayMap<>() : new Double2ObjectOpenHashMap<>();
    }

}
