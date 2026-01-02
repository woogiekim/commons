package io.github.woogiekim.commons.core.data;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Slice;

public interface Cursor<T> extends Slice<T> {

    Object getCursor();

    Object getNextCursor();

    Long getTotalElements();

    boolean hasNext();

    @Override
    <U> @NotNull Cursor<U> map(@NotNull Function<? super T, ? extends U> converter);
}
