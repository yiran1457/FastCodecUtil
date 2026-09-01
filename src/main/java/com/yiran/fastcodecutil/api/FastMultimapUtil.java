package com.yiran.fastcodecutil.api;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.yiran.fastcodecutil.FastCodecUtil;
import com.yiran.fastcodecutil.codecs.Object2ObjectMapCodec;
import com.yiran.fastcodecutil.codecs.stream.ListMultimapStreamCodec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;
import java.util.Map;

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
        return expectedSize <= FastCodecUtil.CapChoiceFactor
                ? createListMultiArrayMap(expectedSize)
                : createListMultiHashMap(expectedSize);
    }

    public static <K, V> Codec<ListMultimap<K, V>> multimapCodec(MapCodec<K> keyCodec, MapCodec<V> elementCodec) {
        return Codec.pair(keyCodec.codec(), elementCodec.codec()).listOf()
                .xmap(FastMultimapUtil::list2MultiMap, FastMultimapUtil::multiMap2List);
    }

    public static <K, V, B extends ByteBuf> StreamCodec<B, ListMultimap<K, V>> createStreamMap(StreamCodec<? super B, K> keyCodec, StreamCodec<? super B, V> elementCodec) {
        return ListMultimapStreamCodec.create(keyCodec, elementCodec);
    }

    public static <K, V> Codec<ListMultimap<K, V>> multimapCodecWithList(Codec<K> keyCodec, Codec<V> elementCodec) {
        return FastCollectionUtil.listOf(Codec.pair(keyCodec, elementCodec))
                .xmap(FastMultimapUtil::list2MultiMap, FastMultimapUtil::multiMap2List);
    }

    public static <K, V> Codec<ListMultimap<K, V>> multimapCodecWithMap(Codec<K> keyCodec, Codec<V> elementCodec) {
        return new Object2ObjectMapCodec<>(keyCodec, FastCollectionUtil.listOf(elementCodec))
                .xmap(FastMultimapUtil::map2MultiMap, FastMultimapUtil::multiMap2Map);
    }

    private static <K, V> ListMultimap<K, V> list2MultiMap(List<Pair<K, V>> list) {
        ListMultimap<K, V> result = list.size() < FastCodecUtil.CapChoiceFactor ? createListMultiArrayMap() : createListMultiHashMap();
        for (Pair<K, V> pair : list) {
            result.put(pair.getFirst(), pair.getSecond());
        }
        return result;
    }

    private static <K, V> ObjectArrayList<Pair<K, V>> multiMap2List(ListMultimap<K, V> map) {
        ObjectArrayList<Pair<K, V>> result = new ObjectArrayList<>(map.size());
        for (Map.Entry<K, V> entry : map.entries()) {
            result.add(Pair.of(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    private static <K, V> ListMultimap<K, V> map2MultiMap(Object2ObjectMap<K, List<V>> map) {
        ListMultimap<K, V> result = createListMultiMap(map.size());
        map.forEach(result::putAll);
        return result;
    }

    private static <K, V> Object2ObjectMap<K, List<V>> multiMap2Map(ListMultimap<K, V> map) {
        Object2ObjectMap<K, List<V>> result = new Object2ObjectOpenHashMap<>(map.size());
        for (K k : map.keySet()) {
            result.put(k, map.get(k));
        }
        return result;
    }


}
