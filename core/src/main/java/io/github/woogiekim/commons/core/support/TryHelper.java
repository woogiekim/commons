package io.github.woogiekim.commons.core.support;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class TryHelper<T> {

    private final T value;
    private final Exception exception;

    private TryHelper(T value, Exception exception) {
        this.value = value;
        this.exception = exception;
    }

    public static <T> TryHelper<T> runCatching(Supplier<T> supplier) {
        try {
            T value = supplier.get();

            return new TryHelper<>(value, null);
        } catch (Exception e) {
            return new TryHelper<>(null, e);
        }
    }

    public static TryHelper<Boolean> runCatching(Runnable runnable) {
        try {
            runnable.run();

            return new TryHelper<>(true, null);
        } catch (Exception e) {
            return new TryHelper<>(false, e);
        }
    }

    public boolean isSuccess() {
        return exception == null;
    }

    public boolean isFailure() {
        return exception != null;
    }

    public Optional<T> getOrNull() {
        return Optional.ofNullable(this.value);
    }

    public T getOrElse(T defaultValue) {
        return isSuccess() ? this.value : defaultValue;
    }

    public Optional<Exception> exceptionOrNull() {
        return Optional.ofNullable(exception);
    }

    public <R> R recover(Function<Exception, R> recoverFunction) {
        if (isFailure()) {
            return recoverFunction.apply(exception);
        }

        return null;
    }
}