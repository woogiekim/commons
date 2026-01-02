package io.github.woogiekim.commons.core.support;

import io.github.woogiekim.commons.core.data.Cursor;
import io.github.woogiekim.commons.core.data.CursorImpl;
import io.github.woogiekim.commons.core.data.Cursorable;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongSupplier;

public final class CursorHelper {

    private CursorHelper() {
    }

    public static <T> Cursor<T> getCursor(List<T> content, Cursorable cursorable, LongSupplier totalSupplier, Function<? super T, Object> nextCursorFinder) {
        if (cursorable.getCursor() == null && content.size() < cursorable.getCursorSize()) {
            return new CursorImpl<>(content, cursorable, (long) content.size(), nextCursorFinder);
        }

        return new CursorImpl<>(content, cursorable, totalSupplier.getAsLong(), nextCursorFinder);
    }

    public static <T> Cursor<T> getCursor(List<T> content, Cursorable cursorable, Function<? super T, Object> nextCursorFinder) {
        return new CursorImpl<>(content, cursorable, null, nextCursorFinder);
    }
}
