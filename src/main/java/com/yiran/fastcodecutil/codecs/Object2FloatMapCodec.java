package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Object2FloatMapCodec<K>(Codec<K> keyCodec) implements IMapCodec<K, Float, Object2FloatMap<K>> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Float> elementCodec() {
        return Codec.FLOAT;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public StreamCodec<ByteBuf, Float> getElementStreamCodec() {
        return ByteBufCodecs.FLOAT;
    }

    @Override
    public Object2FloatMap<K> getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Object2FloatArrayMap<>() : new Object2FloatOpenHashMap<>();
    }
}
