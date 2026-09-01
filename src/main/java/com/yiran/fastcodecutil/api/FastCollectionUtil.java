package com.yiran.fastcodecutil.api;

import com.mojang.serialization.Codec;
import com.yiran.fastcodecutil.codecs.CollectionCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class FastCollectionUtil {
    public static <T> Codec<List<T>> listOf(Codec<T> codec) {
        return new CollectionCodec<>(ObjectArrayList::new, codec);
    }

    public static <T> Codec<Set<T>> setOf(Codec<T> codec) {
        return new CollectionCodec<>(ObjectOpenHashSet::new, codec);
    }

    public static <T, L extends Collection<T>> Codec<L> collectionOf(Codec<T> codec, Supplier<L> collectionProvider) {
        return new CollectionCodec<>(collectionProvider, codec);
    }
}
