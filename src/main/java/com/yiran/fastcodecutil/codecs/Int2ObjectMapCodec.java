package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Int2ObjectMapCodec<V>(Codec<V> elementCodec) implements IMapCodec<Integer, V, Int2ObjectMap<V>> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Integer> keyCodec() {
        return Codec.INT;
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
    public Int2ObjectMap<V> getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Int2ObjectArrayMap<>() : new Int2ObjectOpenHashMap<>();
    }

}
