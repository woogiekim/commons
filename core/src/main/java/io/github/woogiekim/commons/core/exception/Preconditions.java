package io.github.woogiekim.commons.core.exception;

import static io.github.woogiekim.commons.core.support.StringHelper.lenientFormat;

public final class Preconditions {

    private Preconditions() {
    }

    public static <T> T requireNotNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }

        return obj;
    }

    public static <T> T requireNotNull(T obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }

        return obj;
    }

    public static <T> T requireNotNull(T obj, String message, Object... args) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(message, args));
        }

        return obj;
    }

    public static void require(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    public static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void require(boolean condition, String message, Object... args) {
        if (!condition) {
            throw new IllegalArgumentException(lenientFormat(message, args));
        }
    }

    public static <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }

        return obj;
    }

    public static <T> T checkNotNull(T obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }

        return obj;
    }

    public static <T> T checkNotNull(T obj, String message, Object... args) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(message, args));
        }

        return obj;
    }

    public static void check(boolean condition) {
        if (!condition) {
            throw new IllegalStateException();
        }
    }

    public static void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    public static void check(boolean condition, String message, Object... args) {
        if (!condition) {
            throw new IllegalStateException(lenientFormat(message, args));
        }
    }
}
