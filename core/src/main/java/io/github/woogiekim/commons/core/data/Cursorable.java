package io.github.woogiekim.commons.core.data;

import java.util.function.Function;

public interface Cursorable {

    Object getCursor();

    int getCursorSize();

    <T> T convert(Function<String, T> converter);

    Cursorable getFirst();

    boolean hasPrevious();

    default int getAdjustedCursorSize() {
        return this.getCursorSize() + 1;
    }
}
