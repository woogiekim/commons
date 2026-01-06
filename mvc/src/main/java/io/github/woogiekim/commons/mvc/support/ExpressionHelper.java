package io.github.woogiekim.commons.mvc.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

@UtilityClass
public final class ExpressionHelper {

    public static BooleanBuilder like(
        StringPath path,
        String value,
        MatchMode mode
    ) {
        if (StringUtils.isBlank(value)) {
            return new BooleanBuilder();
        }

        return new BooleanBuilder(path.like(mode.toMatchString(value)));
    }

    public static BooleanExpression notLike(
        StringPath path,
        String value,
        MatchMode mode
    ) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return path.notLike(mode.toMatchString(value));
    }

    public static <T> BooleanExpression eq(
        SimpleExpression<T> path,
        T value
    ) {
        if (value == null) {
            return null;
        }

        return path.eq(value);
    }

    public static BooleanExpression eq(
        SimpleExpression<String> path,
        String value
    ) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return path.eq(value);
    }

    public static <T> BooleanExpression neq(
        SimpleExpression<T> path,
        T value
    ) {
        if (value == null) {
            return null;
        }

        return path.ne(value);
    }

    public static BooleanExpression eq(
        StringPath path,
        String value
    ) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return path.eq(value);
    }

    public static <T extends Enum<T>> BooleanExpression eq(
        EnumPath<T> path,
        String value,
        Class<T> cl
    ) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return eq(path, Enum.valueOf(cl, value));
    }

    public static <T> BooleanExpression isNull(SimpleExpression<T> path) {
        return path.isNull();
    }

    public static <T> BooleanExpression isNotNull(SimpleExpression<T> path) {
        return path.isNotNull();
    }

    public static BooleanExpression before(
        DateTimePath<LocalDateTime> path,
        LocalDateTime before
    ) {
        if (before == null) {
            return null;
        }

        return path.before(before);
    }

    public static BooleanExpression beforeTomorrow(
        DateTimePath<LocalDateTime> path,
        LocalDate before
    ) {
        if (before == null) {
            return null;
        }

        return path.before(before.plusDays(1).atStartOfDay());
    }

    public static BooleanExpression before(
        DatePath<LocalDate> path,
        LocalDate before
    ) {
        if (before == null) {
            return null;
        }

        return path.before(before);
    }

    public static BooleanExpression after(
        DateTimePath<LocalDateTime> path,
        LocalDateTime after
    ) {
        if (after == null) {
            return null;
        }

        return path.after(after);
    }

    public static BooleanExpression afterYesterday(
        DateTimePath<LocalDateTime> path,
        LocalDate after
    ) {
        if (after == null) {
            return null;
        }

        return path.goe(after.atStartOfDay());
    }

    public static BooleanExpression after(
        DatePath<LocalDate> path,
        LocalDate after
    ) {
        if (after == null) {
            return null;
        }

        return path.after(after);
    }

    public static BooleanExpression between(
        DateTimePath<LocalDate> path,
        LocalDate from,
        LocalDate to
    ) {
        if (from == null || to == null) {
            return null;
        }

        return path.between(from, to);
    }

    public static BooleanExpression between(
        DateTimePath<LocalDateTime> path,
        LocalDateTime from,
        LocalDateTime to
    ) {
        if (from == null || to == null) {
            return null;
        }

        return path.between(from, to);
    }

    public static BooleanExpression between(
        DateTimePath<OffsetDateTime> path,
        OffsetDateTime from,
        OffsetDateTime to
    ) {
        if (from == null || to == null) {
            return null;
        }

        return path.between(from, to);
    }

    public static BooleanExpression between(
        DateTimePath<ZonedDateTime> path,
        ZonedDateTime from,
        ZonedDateTime to
    ) {
        if (from == null || to == null) {
            return null;
        }

        return path.between(from, to);
    }

    public static <A extends Number & Comparable<?>> BooleanExpression gt(
        NumberPath<?> path,
        A value
    ) {
        if (value == null) {
            return null;
        }
        return path.gt(value);
    }

    public static <A extends Number & Comparable<?>> BooleanExpression lt(
        NumberPath<?> path,
        A value
    ) {
        if (value == null) {
            return null;
        }

        return path.lt(value);
    }

    public static <A extends Number & Comparable<?>> BooleanExpression goe(
        NumberPath<?> path,
        A value
    ) {
        if (value == null) {
            return null;
        }

        return path.goe(value);
    }

    public static <A extends Number & Comparable<?>> BooleanExpression loe(
        NumberPath<?> path,
        A value
    ) {
        if (value == null) {
            return null;
        }

        return path.loe(value);
    }

    public static BooleanBuilder between(
        NumberPath<?> path,
        BigInteger left,
        BigInteger right
    ) {
        var booleanBuilder = new BooleanBuilder();

        if (left == null && right == null) {
            return booleanBuilder;
        }

        if (left == null) {
            return booleanBuilder.and(loe(path, right));
        }

        if (right == null) {
            return booleanBuilder.and(goe(path, left));
        }

        return booleanBuilder.and(path.between(left, right));
    }

    public static <T> BooleanExpression in(
        SimpleExpression<T> path,
        Collection<T> value
    ) {
        if (CollectionUtils.isEmpty(value)) {
            return null;
        }

        return path.in(value);
    }

    public static <T> BooleanExpression notIn(
        SimpleExpression<T> path,
        Collection<T> value
    ) {
        if (CollectionUtils.isEmpty(value)) {
            return null;
        }

        return path.notIn(value);
    }

    public static <T extends Enum<T>> BooleanExpression equalsIgnoreCase(
        EnumPath<T> path,
        String value
    ) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return path.stringValue().equalsIgnoreCase(value);
    }

    public static <T> BooleanBuilder orAll(
        SimpleExpression<T> path,
        Collection<T> conditions
    ) {
        if (conditions == null || conditions.isEmpty()) {
            return null;
        }

        var booleanBuilder = new BooleanBuilder();

        for (var condition : conditions) {
            booleanBuilder.or(path.eq(condition));
        }

        return booleanBuilder;
    }

    public enum MatchMode {
        EXACT {
            @Override
            public String toMatchString(String pattern) {
                return pattern;
            }
        },
        START {
            @Override
            public String toMatchString(String pattern) {
                return pattern + '%';
            }
        },
        END {
            @Override
            public String toMatchString(String pattern) {
                return '%' + pattern;
            }
        },
        ANYWHERE {
            @Override
            public String toMatchString(String pattern) {
                return '%' + pattern + '%';
            }
        };

        public abstract String toMatchString(String var1);
    }
}
