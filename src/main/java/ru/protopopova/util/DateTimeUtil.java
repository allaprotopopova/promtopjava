package ru.protopopova.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {

    private DateTimeUtil() {
    }


    public static LocalDate parseLocalDate(String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static LocalTime parseLocalTime(String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }
}
