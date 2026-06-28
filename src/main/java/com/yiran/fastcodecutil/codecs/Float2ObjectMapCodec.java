package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.floats.Float2ObjectArrayMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Float2ObjectMapCodec<V>(Codec<V> elementCodec) implements IMapCodec<Float, V, Float2ObjectMap<V>> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Float> keyCodec() {
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
    public Float2ObjectMap<V> getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Float2ObjectArrayMap<>() : new Float2ObjectOpenHashMap<>();
    }
}
