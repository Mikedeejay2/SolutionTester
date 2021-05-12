package com.mikedeejay2.codingbatsolutions.internal.util;

import java.lang.reflect.Array;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class SolveUtils
{
    public static boolean isArray(Object obj) {
        return obj instanceof Object[] ||
            obj instanceof byte[] ||
            obj instanceof short[] ||
            obj instanceof int[] ||
            obj instanceof long[] ||
            obj instanceof char[] ||
            obj instanceof float[] ||
            obj instanceof double[] ||
            obj instanceof boolean[];
    }

    public static boolean isInstance(Class<?> clazz, Object obj) {
        return (obj.getClass() == clazz) ||
            (obj.getClass() == Byte.class && clazz == byte.class) ||
            (obj.getClass() == Short.class && clazz == short.class) ||
            (obj.getClass() == Integer.class && clazz == int.class) ||
            (obj.getClass() == Long.class && clazz == long.class) ||
            (obj.getClass() == Character.class && clazz == char.class) ||
            (obj.getClass() == Float.class && clazz == float.class) ||
            (obj.getClass() == Double.class && clazz == double.class) ||
            (obj.getClass() == Boolean.class && clazz == boolean.class);
    }

    public static Class<?> toClassArray(Class<?> clazz) {
        return Array.newInstance(clazz, 0).getClass();
    }

    public static Class<?> toPrimitiveClass(Class<?> clazz) {
        try
        {
            return (Class<?>) clazz.getField("TYPE").get(null);
        }
        catch(NoSuchFieldException | IllegalAccessException ignored) {}
        return clazz;
    };

    public static Object[] fixArr(Object[] obj) {
        if(obj.length == 0) return obj;
        Class<?>[] classes = new Class[obj.length];
        for(int i = 0; i < obj.length; ++i) {
            Object cur = obj[i];
            if(cur == null) {
                classes[i] = null;
                continue;
            }
            classes[i] = cur.getClass();
        }
        Class<?> type = null;
        for(int i = 0; i < classes.length; ++i) {
            Class<?> cur = classes[i];
            if(cur == null) continue;
            if(type == null) {
                type = cur;
            }
            if(type != cur) {
                throw new IllegalArgumentException(
                    String.format("%s is not of type %s",
                                  cur.getSimpleName(), type.getSimpleName()));
            }
        }
        Object[] newArr = (Object[]) Array.newInstance(type, obj.length);
        System.arraycopy(obj, 0, newArr, 0, obj.length);
        return newArr;
    };

    public static Object[] classArrCast(Class<?> clazz, Object[] obj) {
        Object newArr = Array.newInstance(clazz, obj.length);
        System.arraycopy(obj, 0, newArr, 0, obj.length);
        return (Object[]) newArr;
    };
}
