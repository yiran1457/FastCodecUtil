package com.yiran.fastcodecutil.api;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FastMultimapUtil {
    private FastMultimapUtil() {
    }

    public static <K, V> ListMultimap<K, V> createListMultiArrayMap() {
        return Multimaps.newListMultimap(new Object2ObjectArrayMap<>(), ObjectArrayList::new);
    }

    public static <K, V> ListMultimap<K, V> createListMultiArrayMap(int expectedSize) {
        return Multimaps.newListMultimap(new Object2ObjectArrayMap<>(expectedSize), ObjectArrayList::new);
    }

    public static <K, V> ListMultimap<K, V> createListMultiHashMap() {
        return Multimaps.newListMultimap(new Object2ObjectOpenHashMap<>(), ObjectArrayList::new);
    }

    public static <K, V> ListMultimap<K, V> createListMultiHashMap(int expectedSize) {
        return Multimaps.newListMultimap(new Object2ObjectOpenHashMap<>(expectedSize), ObjectArrayList::new);
    }

    public static <K, V> ListMultimap<K, V> createListMultiMap(int expectedSize) {
        return expectedSize <= 16
                ? createListMultiArrayMap(expectedSize)
                : createListMultiHashMap(expectedSize);
    }

    public static <K, V> Codec<ListMultimap<K, V>> multimapCodec(MapCodec<K> keyCodec, MapCodec<V> elementCodec) {
        return Codec.pair(keyCodec.codec(), elementCodec.codec()).listOf()
                .xmap(FastMultimapUtil::list2MultiMap, FastMultimapUtil::multiMap2List);
    }

    public static <K, V> Codec<ListMultimap<K, V>> multimapCodec(Codec<K> keyCodec, Codec<V> elementCodec) {
        return listOf(Codec.pair(keyCodec, elementCodec))
                .xmap(FastMultimapUtil::list2MultiMap, FastMultimapUtil::multiMap2List);
    }

    public static <T> Codec<ObjectArrayList<T>> listOf(Codec<T> codec) {
        return codec.listOf().xmap(ObjectArrayList::new, Function.identity());
    }

    private static <K, V> ListMultimap<K, V> list2MultiMap(List<Pair<K, V>> list) {
        ListMultimap<K, V> result = list.size() < 50 ? createListMultiArrayMap() : createListMultiHashMap();
        for (Pair<K, V> pair : list) {
            result.put(pair.getFirst(), pair.getSecond());
        }
        return result;
    }

    private static <K, V> ObjectArrayList<Pair<K, V>> multiMap2List(Multimap<K, V> map) {
        ObjectArrayList<Pair<K, V>> result = new ObjectArrayList<>(map.size());
        for (Map.Entry<K, V> entry : map.entries()) {
            result.add(Pair.of(entry.getKey(), entry.getValue()));
        }
        return result;
    }


}
