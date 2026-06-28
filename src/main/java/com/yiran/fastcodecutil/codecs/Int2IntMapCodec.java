package com.yiran.fastcodecutil.codecs;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.FastCodecUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

public record Int2IntMapCodec() implements IMapCodec<Integer, Integer, Int2IntMap> {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public Codec<Integer> keyCodec() {
        return Codec.INT;
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
    public StreamCodec<ByteBuf, Integer> getKeyStreamCodec() {
        return ByteBufCodecs.INT;
    }

    @Override
    public StreamCodec<ByteBuf, Integer> getElementStreamCodec() {
        return ByteBufCodecs.INT;
    }

    @Override
    public Int2IntMap getMap(long count) {
        return count < FastCodecUtil.CapChoiceFactor ? new Int2IntArrayMap() : new Int2IntOpenHashMap();
    }

}
