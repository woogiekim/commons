package io.github.woogiekim.commons.core.data;

import java.util.Objects;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;

public class CursorRequest implements Cursorable {

    private final Object cursor;
    private final int size;

    public CursorRequest(Object cursor, int size) {
        this.cursor = cursor;
        this.size = size;
    }

    @Override
    public Object getCursor() {
        return this.cursor;
    }

    @Override
    public int getCursorSize() {
        return this.size;
    }

    @Nullable
    @Override
    public <T> T convert(Function<String, T> converter) {
        if (this.getCursor() == null) {
            return null;
        }

        final var cursorValue = String.valueOf(this.getCursor());

        if (cursorValue.isBlank()) {
            return null;
        }

        return converter.apply(cursorValue);
    }

    @Override
    public Cursorable getFirst() {
        if (this.cursor == null) {
            return this;
        }

        return new CursorRequest(null, this.size);
    }

    @Override
    public boolean hasPrevious() {
        return this.cursor != null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final var that = (CursorRequest) o;

        return this.size == that.size && Objects.equals(this.cursor, that.cursor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cursor, this.size);
    }
}
