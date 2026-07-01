package com.yiran.fastcodecutil.codecs.stream;

import com.google.common.collect.ListMultimap;
import com.mojang.datafixers.util.Pair;
import com.yiran.fastcodecutil.api.FastMultimapUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record ListMultimapStreamCodec<B extends ByteBuf, K, V>(
        ListPairStreamCodec<B, K, V> elementCodec) implements StreamCodec<B, ListMultimap<K, V>> {

    public static <B extends ByteBuf, K, V> ListMultimapStreamCodec<B, K, V> create(ListPairStreamCodec<B, K, V> elementCodec) {
        return new ListMultimapStreamCodec<>(elementCodec);
    }

    public static <B extends ByteBuf, K, V> ListMultimapStreamCodec<B, K, V> create(PairStreamCodec<B, K, V> elementCodec) {
        return create(new ListPairStreamCodec<>(elementCodec));
    }

    public static <B extends ByteBuf, K, V> ListMultimapStreamCodec<B, K, V> create(StreamCodec<B, K> keyCodec, StreamCodec<B, V> valueCodec) {
        return create(new PairStreamCodec<>(keyCodec, valueCodec));
    }

    @Override
    public @NotNull ListMultimap<K, V> decode(@NotNull B byteBuf) {
        var list = elementCodec.decode(byteBuf);
        ListMultimap<K, V> result = FastMultimapUtil.createListMultiArrayMap(list.size());
        for (Pair<K, V> pair : list) {
            result.put(pair.getFirst(), pair.getSecond());
        }
        return result;
    }

    @Override
    public void encode(@NotNull B byteBuf, @NotNull ListMultimap<K, V> listMultimap) {
        var list = listMultimap.entries()
                .stream()
                .map(kvEntry -> Pair.of(kvEntry.getKey(), kvEntry.getValue()))
                .collect(ObjectArrayList.toList());
        elementCodec.encode(byteBuf, list);
    }
}
