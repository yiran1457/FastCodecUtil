package com.yiran.fastcodecutil.api;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.function.Function;

public class FastCollectionUtil {
    private FastCollectionUtil() {
    }

    public static <T> Codec<ObjectArrayList<T>> listOf(Codec<T> codec) {
        return codec.listOf().xmap(ObjectArrayList::new, Function.identity());
    }

    public static <T> Codec<ObjectArraySet<T>> setArrayOf(Codec<T> codec) {
        return codec.listOf().xmap(ObjectArraySet::new, ObjectArrayList::new);
    }

    public static <T> Codec<ObjectOpenHashSet<T>> setHashOf(Codec<T> codec) {
        return codec.listOf().xmap(ObjectOpenHashSet::new, ObjectArrayList::new);
    }
}
