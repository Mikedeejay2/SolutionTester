package com.mikedeejay2.solutiontester.util;

import java.util.Arrays;

public final class SolveUtils {
    private SolveUtils() {
        throw new UnsupportedOperationException("This class should not be initialized.");
    }

    public static boolean isInstance(Class<?> clazz1, Class<?> clazz2) {
        return (clazz1 == clazz2) ||
            ((clazz1 == Byte.class      && clazz2 == byte.class   ) || (clazz2 == Byte.class && clazz1 == byte.class      )) ||
            ((clazz1 == Short.class     && clazz2 == short.class  ) || (clazz2 == Short.class && clazz1 == short.class    )) ||
            ((clazz1 == Integer.class   && clazz2 == int.class    ) || (clazz2 == Integer.class && clazz1 == int.class    )) ||
            ((clazz1 == Long.class      && clazz2 == long.class   ) || (clazz2 == Long.class && clazz1 == long.class      )) ||
            ((clazz1 == Character.class && clazz2 == char.class   ) || (clazz2 == Character.class && clazz1 == char.class )) ||
            ((clazz1 == Float.class     && clazz2 == float.class  ) || (clazz2 == Float.class && clazz1 == float.class    )) ||
            ((clazz1 == Double.class    && clazz2 == double.class ) || (clazz2 == Double.class && clazz1 == double.class  )) ||
            ((clazz1 == Boolean.class   && clazz2 == boolean.class) || (clazz2 == Boolean.class && clazz1 == boolean.class)) ||
            ((clazz1.isArray() || clazz2.isArray()) && isArrayInstance(clazz1, clazz2));
    }

    public static boolean isArrayInstance(Class<?> clazz1, Class<?> clazz2) {
        Class<?> rootComponent1 = getRootComponent(clazz1);
        Class<?> rootComponent2 = getRootComponent(clazz2);
        if(getArrayDepth(clazz1) != getArrayDepth(clazz2)) return false;
        return rootComponent1 == rootComponent2;
    }

    public static Class<?> getRootComponent(Class<?> clazz) {
        if(!clazz.isArray()) return clazz;
        return getRootComponent(clazz.getComponentType());
    }

    public static int getArrayDepth(Class<?> clazz) {
        int count = 0;
        Class<?> curClass = clazz;
        while(curClass.isArray()) {
            ++count;
            curClass = curClass.getComponentType();
        }
        return count;
    }

    public static boolean eEquals(Object obj1, Object obj2) {
        if(obj1 == null && obj2 == null) return true;
        if(obj1 == null ^ obj2 == null) return false;
        final Class<?> obj1Class = obj1.getClass();
        final Class<?> obj2Class = obj2.getClass();
        if(obj1Class.isArray() && obj2Class.isArray()) {
            return Arrays.deepEquals((Object[]) obj1, (Object[]) obj2);
        } else if(obj1Class.isArray() || obj2Class.isArray()) {
            return false;
        }
        return obj1.equals(obj2);
    }

    public static String quotedToString(Object obj) {
        StringBuilder result = new StringBuilder();
        if(obj.getClass().isArray()) {
            return _quotedToString((Object[]) obj);
        }
        if(obj instanceof String) {
            result.append('\"');
            result.append(obj);
            result.append('\"');
        } else if(obj instanceof Character) {
            result.append('\'');
            result.append(obj);
            result.append('\'');
        } else {
            result.append(obj);
        }
        return result.toString();
    }

    private static String _quotedToString(Object[] obj) {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for(int i = 0; i < obj.length; ++i) {
            Object o = obj[i];
            if(o.getClass().isArray()) {
                if(o.getClass().getComponentType().isPrimitive()) {
                    if(o instanceof byte[]) result.append(Arrays.toString((byte[]) o));
                    else if(o instanceof short[]) result.append(Arrays.toString((short[]) o));
                    else if(o instanceof int[]) result.append(Arrays.toString((int[]) o));
                    else if(o instanceof long[]) result.append(Arrays.toString((long[]) o));
                    else if(o instanceof char[]) result.append(Arrays.toString((char[]) o));
                    else if(o instanceof float[]) result.append(Arrays.toString((float[]) o));
                    else if(o instanceof double[]) result.append(Arrays.toString((double[]) o));
                    else if(o instanceof boolean[]) result.append(Arrays.toString((boolean[]) o));
                } else {
                    result.append(_quotedToString((Object[]) o));
                }
            } else result.append(quotedToString(o));
            if(i < obj.length - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
}
