package com.sebasth.alifood.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    // Method to calculate the difference in days between two dates
    public static long getDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    // Method to check if one date has passed another date by a certain number of days
    public static boolean isToday(LocalDate date, int daysOffset) {
        return getDaysBetween(date, LocalDate.now()) > daysOffset;
    }
}
