package com.yiran.fastcodecutil.codecs.stream;

import com.mojang.datafixers.util.Pair;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record PairStreamCodec<B extends ByteBuf, K, V>(StreamCodec<? super B, K> keyCodec,
                                                       StreamCodec<? super B, V> valueCodec) implements StreamCodec<B, Pair<K, V>> {

    public static <B extends ByteBuf, K, V> PairStreamCodec<B, K, V> create(StreamCodec<B, K> keyCodec, StreamCodec<B, V> valueCodec) {
        return new PairStreamCodec<>(keyCodec, valueCodec);
    }

    @Override
    public @NotNull Pair<K, V> decode(@NotNull B byteBuf) {
        return Pair.of(keyCodec.decode(byteBuf), valueCodec.decode(byteBuf));
    }

    @Override
    public void encode(@NotNull B byteBuf, Pair<K, V> kvPair) {
        keyCodec.encode(byteBuf, kvPair.getFirst());
        valueCodec.encode(byteBuf, kvPair.getSecond());
    }
}
