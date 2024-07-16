package com.example.base.utils;

import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GenericCompact {

    public interface Functor<I, R> {
        public R apply(I input);
    }

    static public <S, T> List<T> map(List<S> s_list, Functor<S, T> converter) {
        List<T> target_list = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            target_list = s_list.stream().map((e) -> converter.apply(e)).collect(Collectors.toList());
        } else {
            for (S s: s_list) {
                T t = converter.apply(s);
                if (t != null) {
                    target_list.add(t);
                }
            }
        }
        return target_list;
    };

    static public <S, T> List<T> map(Collection<S> s_list, Functor<S, T> converter) {
        List<T> target_list = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            target_list = s_list.stream().map((e) -> converter.apply(e)).collect(Collectors.toList());
        } else {
            for (S s: s_list) {
                T t = converter.apply(s);
                if (t != null) {
                    target_list.add(t);
                }
            }
        }
        return target_list;
    };

    static public <T> List<T> filter(List<T> list, Functor<T, Boolean> filterFunction) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return list.stream().filter(e -> filterFunction.apply(e)).collect(Collectors.toList());
        } else {
            List<T> data = new ArrayList<>();
            for (T item: list) {
                if (filterFunction.apply(item)) {
                    data.add(item);
                }
            }
            return data;
        }
    }

    static public <T> void  sort(List<T> list, Comparator<? super T> comparable) {
        if  (list == null || comparable == null)  {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort(comparable);
        } else {
            T[] array = (T[])list.toArray();
            if (array != null) {
                Arrays.sort(array, comparable);
                list.clear();
                list.addAll(Arrays.asList(array));
            }
        }
    }
}
