package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Object2DoubleMapCodec<K>(Codec<K> keyCodec) implements IMapCodec<K, Double, Object2DoubleMap<K>> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Double> elementCodec() {
        return Codec.DOUBLE;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public StreamCodec<ByteBuf, Double> getElementStreamCodec() {
        return ByteBufCodecs.DOUBLE;
    }

    @Override
    public Object2DoubleMap<K> getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Object2DoubleArrayMap<>() : new Object2DoubleOpenHashMap<>();
    }
}
