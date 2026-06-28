package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Object2BooleanMapCodec<K>(Codec<K> keyCodec) implements IMapCodec<K, Boolean, Object2BooleanMap<K>> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Boolean> elementCodec() {
        return Codec.BOOL;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public StreamCodec<ByteBuf, Boolean> getElementStreamCodec() {
        return ByteBufCodecs.BOOL;
    }

    @Override
    public Object2BooleanMap<K> getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Object2BooleanArrayMap<>() : new Object2BooleanOpenHashMap<>();
    }
}
