package com.yiran.fastcodecutil.codecs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;

public interface IMapCodec<K, V, M extends Map<K, V>> extends Codec<M> {
    Codec<K> keyCodec();

    Codec<V> elementCodec();

    Logger getLogger();

    M getMap(long count);

    default StreamCodec<ByteBuf, K> getKeyStreamCodec() {
        return ByteBufCodecs.fromCodec(keyCodec());
    }

    default StreamCodec<ByteBuf, V> getElementStreamCodec() {
        return ByteBufCodecs.fromCodec(elementCodec());
    }

    default <B extends ByteBuf> StreamCodec<B, M> toStreamCodec(){
        return toStreamCodec(this);
    }

    static <B extends ByteBuf, K, V, M extends Map<K, V>> StreamCodec<B, M> toStreamCodec(
            IMapCodec<K, V, M> iMapCodec,
            @Nullable StreamCodec<? super B, K> keyStreamCodec,
            @Nullable StreamCodec<? super B, V> elementStreamCodec
    ) {
        return ByteBufCodecs.map(
                iMapCodec::getMap,
                keyStreamCodec == null ? iMapCodec.getKeyStreamCodec() : keyStreamCodec,
                elementStreamCodec == null ? iMapCodec.getElementStreamCodec() : elementStreamCodec
        );
    }

    static <B extends ByteBuf, K, V, M extends Map<K, V>> StreamCodec<B, M> toStreamCodec(
            IMapCodec<K, V, M> iMapCodec
    ) {
        return ByteBufCodecs.map(
                iMapCodec::getMap,
                iMapCodec.getKeyStreamCodec(),
                iMapCodec.getElementStreamCodec()
        );
    }

    @Override
    default <T> DataResult<T> encode(M input, DynamicOps<T> ops, T prefix) {
        RecordBuilder<T> builder = ops.mapBuilder();
        input.forEach((key, value) -> {
            builder.add(keyCodec().encodeStart(ops, key), elementCodec().encodeStart(ops, value));
        });
        return builder.build(prefix);
    }

    @Override
    default <T> DataResult<Pair<M, T>> decode(DynamicOps<T> ops, T input) {
        return ops.getMap(input).setLifecycle(Lifecycle.stable())
                .flatMap(mapLike -> {
                    M map = getMap(mapLike.entries().count());
                    mapLike.entries().forEach(pair -> handleDecode(pair, ops, map));
                    return DataResult.success(map);
                })
                .map(decodedMap -> Pair.of(decodedMap, input));
    }

    default <T> void handleDecode(Pair<T, T> pair, DynamicOps<T> ops, M map) {
        DataResult<K> keyResult = keyCodec().parse(ops, pair.getFirst());
        DataResult<V> valueResult = elementCodec().parse(ops, pair.getSecond());
        DataResult<Pair<K, V>> entryResult = keyResult.apply2stable(Pair::of, valueResult);
        entryResult.error().
                ifPresent(error ->
                        getLogger().error("Failed to decode entry (Key: {}, Value: {}): {}",
                                keyResult, valueResult, error)
                );
        entryResult.result().ifPresent(p -> map.put(p.getFirst(), p.getSecond()));
    }

}
