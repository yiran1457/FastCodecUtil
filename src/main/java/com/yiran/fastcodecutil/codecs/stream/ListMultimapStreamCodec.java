package com.yiran.fastcodecutil.codecs.stream;

import com.google.common.collect.ListMultimap;
import com.yiran.fastcodecutil.api.FastMultimapUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public record ListMultimapStreamCodec<B extends ByteBuf, K, V>(
        StreamCodec<? super B, K> keyCodec,
        StreamCodec<? super B, Collection<V>> valueCodec) implements StreamCodec<B, ListMultimap<K, V>> {

    public static <B extends ByteBuf, K, V> ListMultimapStreamCodec<B, K, V> create(StreamCodec<? super B, K> keyCodec, StreamCodec<? super B, V> elementCodec) {
        return new ListMultimapStreamCodec<>(keyCodec, ByteBufCodecs.collection(ObjectArrayList::new, elementCodec));
    }

    @Override
    public @NotNull ListMultimap<K, V> decode(@NotNull B byteBuf) {
        int size = VarInt.read(byteBuf);
        ListMultimap<K, V> result = FastMultimapUtil.createListMultiMap(size);
        for (int i = 0; i < size; i++) {
            result.putAll(keyCodec.decode(byteBuf), valueCodec.decode(byteBuf));
        }
        return result;
    }

    @Override
    public void encode(@NotNull B byteBuf, @NotNull ListMultimap<K, V> listMultimap) {
        Map<K, Collection<V>> asMap = listMultimap.asMap();
        VarInt.write(byteBuf, asMap.size());
        for (Map.Entry<K, Collection<V>> entry : asMap.entrySet()) {
            keyCodec.encode(byteBuf, entry.getKey());
            valueCodec.encode(byteBuf, entry.getValue());
        }
    }
}
