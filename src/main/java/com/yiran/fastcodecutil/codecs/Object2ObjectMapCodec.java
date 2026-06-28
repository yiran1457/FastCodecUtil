package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.slf4j.Logger;

public record Object2ObjectMapCodec<K, V>(Codec<K> keyCodec, Codec<V> elementCodec)
        implements IMapCodec<K, V, Object2ObjectMap<K, V>> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public Object2ObjectMap<K, V> getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Object2ObjectArrayMap<>() : new Object2ObjectOpenHashMap<>();
    }
}
