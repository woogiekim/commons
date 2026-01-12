package io.github.woogiekim.commons.core.exception;

import static io.github.woogiekim.commons.core.support.StringHelper.lenientFormat;

import jakarta.validation.ValidationException;
import java.util.function.Supplier;

public final class Validations {

    private Validations() {
    }

    public static <T> T validateNotNull(T obj, Supplier<String> messageSupplier) {
        if (obj == null) {
            throw new ValidationException(messageSupplier.get());
        }

        return obj;
    }

    public static <T> T validateNotNull(T obj, String message) {
        if (obj == null) {
            throw new ValidationException(message);
        }

        return obj;
    }

    public static <T> T validateNotNull(T obj, String message, Object... args) {
        if (obj == null) {
            throw new ValidationException(lenientFormat(message, args));
        }

        return obj;
    }

    public static void validate(boolean condition, Supplier<String> messageSupplier) {
        if (!condition) {
            throw new ValidationException(messageSupplier.get());
        }
    }

    public static void validate(boolean condition, String message) {
        if (!condition) {
            throw new ValidationException(message);
        }
    }

    public static void validate(boolean condition, String message, Object... args) {
        if (!condition) {
            throw new ValidationException(lenientFormat(message, args));
        }
    }
}
