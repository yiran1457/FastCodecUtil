package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import org.slf4j.Logger;

import java.util.Map;

public record CustomMapCodec<K, V, M extends Map<K, V>>(Codec<K> keyCodec, Codec<V> elementCodec,
                                                        Int2ObjectFunction<M> mapGetter) implements IMapCodec<K, V, M> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public M getMap(long count) {
        return mapGetter.apply((int) count);
    }
}
