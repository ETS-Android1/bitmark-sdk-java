package utils;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author Hieu Pham
 * @since 8/27/18
 * Email: hieupham@bitmark.com
 * Copyright © 2018 Bitmark. All rights reserved.
 */

public class ArrayUtil {

    private ArrayUtil() {
    }

    public static int toPrimitiveInteger(byte[] value) {
        try {
            return ByteBuffer.wrap(value).getInt();
        } catch (BufferUnderflowException exception) {
            return -1;
        }
    }

    public static byte[] toByteArray(int value) throws BufferUnderflowException {
        return minimize(ByteBuffer.allocate(Integer.SIZE / 8).putInt(value).array());
    }

    public static byte[] concat(byte[] a, byte[] b) {
        if (a != null && b != null) {
            byte[] rv = new byte[a.length + b.length];

            System.arraycopy(a, 0, rv, 0, a.length);
            System.arraycopy(b, 0, rv, a.length, b.length);

            return rv;
        } else if (b != null) {
            return clone(b);
        } else {
            return clone(a);
        }
    }

    public static byte[] concat(byte[] a, byte[] b, byte[] c) {
        if (a != null && b != null && c != null) {
            byte[] rv = new byte[a.length + b.length + c.length];

            System.arraycopy(a, 0, rv, 0, a.length);
            System.arraycopy(b, 0, rv, a.length, b.length);
            System.arraycopy(c, 0, rv, a.length + b.length, c.length);

            return rv;
        } else if (a == null) {
            return concat(b, c);
        } else if (b == null) {
            return concat(a, c);
        } else {
            return concat(a, b);
        }
    }

    public static byte[] concat(byte[] a, byte[] b, byte[] c, byte[] d) {
        if (a != null && b != null && c != null && d != null) {
            byte[] rv = new byte[a.length + b.length + c.length + d.length];

            System.arraycopy(a, 0, rv, 0, a.length);
            System.arraycopy(b, 0, rv, a.length, b.length);
            System.arraycopy(c, 0, rv, a.length + b.length, c.length);
            System.arraycopy(d, 0, rv, a.length + b.length + c.length, d.length);

            return rv;
        } else if (d == null) {
            return concat(a, b, c);
        } else if (c == null) {
            return concat(a, b, d);
        } else if (b == null) {
            return concat(a, c, d);
        } else {
            return concat(b, c, d);
        }
    }

    public static boolean equals(byte[] a, byte[] b) {
        return Arrays.equals(a, b);
    }

    public static byte[] clone(byte[] data) {
        if (data == null) {
            return null;
        }
        byte[] copy = new byte[data.length];

        System.arraycopy(data, 0, copy, 0, data.length);

        return copy;
    }

    public static byte[] slice(byte[] data, int from, int to) {
        return Arrays.copyOfRange(data, from, to);
    }

    public static boolean[] slice(boolean[] data, int from, int to) {
        return Arrays.copyOfRange(data, from, to);
    }

    public static byte[] minimize(byte[] data) {
        int i = 0;
        for (byte b : data) {
            if (b == 0) i++;
            else break;
        }
        return slice(data, i, data.length);
    }
}
