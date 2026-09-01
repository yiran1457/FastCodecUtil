package com.yiran.fastcodecutil.codecs;

import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public record CollectionCodec<V, L extends Collection<V>>(Supplier<L> collectionProvider,
                                                          Codec<V> elementCodec) implements Codec<L> {

    @Override
    public <T> DataResult<Pair<L, T>> decode(DynamicOps<T> ops, T input) {
        return ops.getList(input).setLifecycle(Lifecycle.stable()).flatMap(stream -> {
            L elements = collectionProvider.get();
            List<T> failed = new ArrayList<>();
            final DataResult<Unit>[] result = new DataResult[]{DataResult.success(Unit.INSTANCE, Lifecycle.stable())};
            stream.accept(value -> {
                final DataResult<Pair<V, T>> elementResult = elementCodec.decode(ops, value);
                elementResult.error().ifPresent(error -> failed.add(value));
                elementResult.resultOrPartial().ifPresent(pair -> elements.add(pair.getFirst()));
                result[0] = result[0].apply2stable((r, elem) -> r, elementResult);
            });
            T errors = ops.createList(failed.stream());
            Pair<L, T> pair = Pair.of(elements, errors);
            return result[0].map(ignored -> pair).setPartial(pair);
        });
    }


    @Override
    public <T> DataResult<T> encode(L input, DynamicOps<T> ops, T prefix) {
        ListBuilder<T> builder = ops.listBuilder();
        for (V v : input) {
            builder.add(elementCodec.encodeStart(ops, v));
        }
        return builder.build(prefix);
    }
}
