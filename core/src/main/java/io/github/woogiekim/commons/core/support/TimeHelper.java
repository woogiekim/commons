package io.github.woogiekim.commons.core.support;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class TimeHelper {

    public static final LocalDateTime MINIMUM_LOCAL_DATE_TIME = LocalDateTime.of(1000, 1, 1, 0, 0, 0);
    public static final LocalDateTime MAXIMUM_LOCAL_DATE_TIME = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    public static boolean isHoliday(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SUNDAY) || date.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    public static boolean isBeforeOrEqual(LocalDate source, LocalDate target) {
        return source.isBefore(target) || source.isEqual(target);
    }

    public static boolean isAfterOrEqual(LocalDate source, LocalDate target) {
        return source.isAfter(target) || source.isEqual(target);
    }

    public static YearMonth toYearMonth(LocalDate date) {
        return YearMonth.of(date.getYear(), date.getMonth());
    }
}
