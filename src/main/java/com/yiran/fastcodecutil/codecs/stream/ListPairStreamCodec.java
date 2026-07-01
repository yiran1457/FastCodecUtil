package com.yiran.fastcodecutil.codecs.stream;

import com.mojang.datafixers.util.Pair;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record ListPairStreamCodec<B extends ByteBuf, K, V>(
        PairStreamCodec<B, K, V> elementCodec) implements StreamCodec<B, ObjectArrayList<Pair<K, V>>> {

    public static <B extends ByteBuf, K, V> ListPairStreamCodec<B, K, V> create(PairStreamCodec<B, K, V> elementCodec) {
        return new ListPairStreamCodec<>(elementCodec);
    }

    public static <B extends ByteBuf, K, V> ListPairStreamCodec<B, K, V> create(StreamCodec<B, K> keyCodec, StreamCodec<B, V> valueCodec) {
        return create(new PairStreamCodec<>(keyCodec, valueCodec));
    }

    @Override
    public @NotNull ObjectArrayList<Pair<K, V>> decode(@NotNull B byteBuf) {
        int size = byteBuf.readInt();
        ObjectArrayList<Pair<K, V>> pairs = new ObjectArrayList<>(size);
        for (int i = 0; i < size; i++) {
            pairs.add(elementCodec.decode(byteBuf));
        }
        return pairs;
    }

    @Override
    public void encode(@NotNull B byteBuf, ObjectArrayList<Pair<K, V>> pairs) {
        byteBuf.writeInt(pairs.size());
        for (Pair<K, V> pair : pairs) {
            elementCodec.encode(byteBuf, pair);
        }
    }
}
