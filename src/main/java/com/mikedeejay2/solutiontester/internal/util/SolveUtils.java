package com.mikedeejay2.solutiontester.internal.util;

import com.mikedeejay2.solutiontester.internal.SolutionTester;

import java.util.Arrays;

/**
 * Utility class for {@link SolutionTester} and related classes.
 *
 * @author Mikedeejay2
 * @since 1.0.0
 */
public final class SolveUtils {
    /**
     * This is a utility class, it can't be initialized.
     */
    private SolveUtils() {
        throw new UnsupportedOperationException("This class should not be initialized.");
    }

    /**
     * Get whether two classes are instances of each other. Includes checking primitive vs non-primitive, array deep
     * equals, object equals, etc.
     *
     * @param clazz1 The first class
     * @param clazz2 The second class
     * @return Whether the two classes are instances of each other
     */
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

    /**
     * Get whether two class arrays are an instance of each other. Checks deep, comparing the depth of arrays, data
     * types, etc.
     *
     * @param clazz1 The first array class
     * @param clazz2 The second array class
     * @return Whether the two classes are instances of each other
     */
    public static boolean isArrayInstance(Class<?> clazz1, Class<?> clazz2) {
        Class<?> rootComponent1 = getRootComponent(clazz1);
        Class<?> rootComponent2 = getRootComponent(clazz2);
        if(getArrayDepth(clazz1) != getArrayDepth(clazz2)) return false;
        return rootComponent1 == rootComponent2;
    }

    /**
     * Recursively iterate to get the root component class of an Array Class
     *
     * @param clazz The array Class to get the root component of
     * @return The root component
     */
    public static Class<?> getRootComponent(Class<?> clazz) {
        if(!clazz.isArray()) return clazz;
        return getRootComponent(clazz.getComponentType());
    }

    /**
     * Get the array depth of a class array
     *
     * @param clazz The class to get the array depth of
     * @return The array depth
     */
    public static int getArrayDepth(Class<?> clazz) {
        int count = 0;
        Class<?> curClass = clazz;
        while(curClass.isArray()) {
            ++count;
            curClass = curClass.getComponentType();
        }
        return count;
    }

    /**
     * A deep equivalent equals of two objects. Compares nullability, array depths, arrays, objects equals, etc
     *
     * @param obj1 The first object
     * @param obj2 The second object
     * @return Whether the two objects are equivalently equal to each other
     */
    public static boolean eEquals(Object obj1, Object obj2) {
        if(obj1 == null && obj2 == null) return true;
        if(obj1 == null ^ obj2 == null) return false;
        final Class<?> obj1Class = obj1.getClass();
        final Class<?> obj2Class = obj2.getClass();
        if(obj1Class.isArray() && obj2Class.isArray()) {
            return Arrays.deepEquals(toObjectArray(obj1), toObjectArray(obj2));
        } else if(obj1Class.isArray() || obj2Class.isArray()) {
            return false;
        }
        return obj1.equals(obj2);
    }

    /**
     * Convert a primitive array to an object array
     *
     * @param obj The input primitive array
     * @return The converted object array
     */
    public static Object[] toObjectArray(Object obj) {
        if(obj instanceof Object[]) return (Object[]) obj;
        else if(obj instanceof byte[]) {
            byte[] arr = (byte[]) obj;
            Object[] res = new Object[arr.length];
            for(int i = 0; i < arr.length; ++i) res[i] = arr[i];
            return res;
        } else if(obj instanceof short[]) {
            short[] arr = (short[]) obj;
            Object[] res = new Object[arr.length];
            for(int i = 0; i < arr.length; ++i) res[i] = arr[i];
            return res;
        } else if(obj instanceof int[]) {
            int[] arr = (int[]) obj;
            Object[] res = new Object[arr.length];
            for(int i = 0; i < arr.length; ++i) res[i] = arr[i];
            return res;
        } else if(obj instanceof long[]) {
            long[] arr = (long[]) obj;
            Object[] res = new Object[arr.length];
            for(int i = 0; i < arr.length; ++i) res[i] = arr[i];
            return res;
        } else if(obj instanceof char[]) {
            char[] arr = (char[]) obj;
            Object[] res = new Object[arr.length];
            for(int i = 0; i < arr.length; ++i) res[i] = arr[i];
            return res;
        } else if(obj instanceof float[]) {
            float[] arr = (float[]) obj;
            Object[] res = new Object[arr.length];
            for(int i = 0; i < arr.length; ++i) res[i] = arr[i];
            return res;
        } else if(obj instanceof double[]) {
            double[] arr = (double[]) obj;
            Object[] res = new Object[arr.length];
            for(int i = 0; i < arr.length; ++i) res[i] = arr[i];
            return res;
        } else if(obj instanceof boolean[]) {
            boolean[] arr = (boolean[]) obj;
            Object[] res = new Object[arr.length];
            for(int i = 0; i < arr.length; ++i) res[i] = arr[i];
            return res;
        }
        return null;
    }

    /**
     * If the object is a String or a Character (or char), surround that String in quotes.
     *
     * @param obj The input object
     * @return The new quoted String if applicable
     */
    public static String quotedToString(Object obj) {
        if(obj == null) return "null";
        StringBuilder result = new StringBuilder();
        if(obj.getClass().isArray()) {
            return _quotedToString(toObjectArray(obj));
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

    /**
     * Internal quoted to String for handling arrays deeply.
     *
     * @param obj The input array
     * @return The quoted array String
     */
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
                    result.append(_quotedToString(toObjectArray(o)));
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
