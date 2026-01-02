package io.github.woogiekim.commons.core.data;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CursorImpl<T> implements Cursor<T> {

    private final List<T> content;
    private final Function<? super T, Object> nextCursorFinder;
    private final Cursorable cursorable;
    private final Cursorable nextCursorable;
    private final Long total;

    public CursorImpl(List<T> content, Cursorable cursorable, Long total, Function<? super T, Object> nextCursorFinder) {
        this.content = content.stream().limit(cursorable.getCursorSize()).toList();
        this.nextCursorFinder = nextCursorFinder;
        this.cursorable = cursorable;
        this.nextCursorable = findNextCursor(content, cursorable, nextCursorFinder);
        this.total = total;
    }

    private static <T> Cursorable findNextCursor(List<? extends T> content, Cursorable cursorable, Function<? super T, Object> nextCursorFinder) {
        if (content.size() == cursorable.getCursorSize()) {
            return null;
        }

        final var countWithoutNext = content.size() - 1;

        if (countWithoutNext < cursorable.getCursorSize()) {
            return null;
        }

        final var next = content.get(countWithoutNext);

        return new CursorRequest(nextCursorFinder.apply(next), cursorable.getCursorSize());
    }

    @Override
    public Object getCursor() {
        return this.cursorable.getCursor();
    }

    @Override
    public Object getNextCursor() {
        return Optional.ofNullable(this.nextCursorable).map(Cursorable::getCursor).orElse(null);
    }

    @Override
    public int getSize() {
        return this.cursorable.getCursorSize();
    }

    @Override
    public int getNumberOfElements() {
        return this.content.size();
    }

    @Override
    public @NotNull List<T> getContent() {
        return this.content;
    }

    @Override
    public boolean hasContent() {
        return !this.content.isEmpty();
    }

    @Override
    public Long getTotalElements() {
        return this.total;
    }

    @Override
    public boolean hasNext() {
        return this.getNextCursor() != null;
    }

    @Override
    public boolean isFirst() {
        return this.getCursor() == null;
    }

    @Override
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return this.getCursor() != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull <U> Cursor<U> map(@NotNull Function<? super T, ? extends U> converter) {
        return new CursorImpl<>(this.content.stream().map(converter).collect(Collectors.toList()), this.cursorable, this.getTotalElements(), (Function<? super U, Object>) this.nextCursorFinder);
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return this.content.iterator();
    }

    @Override
    public @NotNull Sort getSort() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getNumber() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Pageable nextPageable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Pageable previousPageable() {
        throw new UnsupportedOperationException();
    }
}
