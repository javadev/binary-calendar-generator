/*
 * $Id$
 *
 * Copyright 2012 Valentyn Kolesnikov
 */
package com.github.binarycalendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * CalendarDataGenerator.
 *
 * @author javadev
 * @version $Revision$ $Date$
 */
public class CalendarDataGenerator {
    private List<List<String>> result = new ArrayList<List<String>>();
    private int year;
    private int dayOfWeek;

    public List<List<String>> generate(String yearStr) {
        year = Integer.valueOf(yearStr);
        result.add(Arrays.asList(makeBinary(year)));
        dayOfWeek = getDayOfWeek(0);
        generateMonths(0, 3);
        generateMonths(4, 7);
        generateMonths(8, 11);
        return result;
    }

    private String makeBinary(int number) {
        return Integer.toBinaryString(number);
    }

    private void generateMonths(int monthStart, int monthFinish) {
        List<String> monthsOfYear = new ArrayList<String>();
        List<String> daysOfWeeks = new ArrayList<String>();
        List<List<String>> daysInMonth = new ArrayList<List<String>>();
        daysInMonth.add(new ArrayList<String>());
        daysInMonth.add(new ArrayList<String>());
        daysInMonth.add(new ArrayList<String>());
        daysInMonth.add(new ArrayList<String>());
        daysInMonth.add(new ArrayList<String>());
        daysInMonth.add(new ArrayList<String>());
        for (int monthIndex = monthStart; monthIndex <= monthFinish; monthIndex += 1) {
            monthsOfYear.add(makeBinary(monthIndex + 1));
            for (int dayOfWeekIndex = 0; dayOfWeekIndex < 7; dayOfWeekIndex += 1) {
                daysOfWeeks.add(makeBinary(dayOfWeekIndex + 1));
            }
            for (int dayIndex = 0; dayIndex < getDayOfWeek(monthIndex); dayIndex += 1) {
                daysInMonth.get(0).add("-");
            }
            for (int dayIndex = 0; dayIndex < 7 - getDayOfWeek(monthIndex); dayIndex += 1) {
                daysInMonth.get(0).add(makeBinary(dayIndex + 1));
            }
            int daysInMonthIndex = 0;
            int dayRowIndex = 0;
            for (int dayIndex = 7 - getDayOfWeek(monthIndex); dayIndex < getDaysInMonth(monthIndex);
                dayIndex += 1, dayRowIndex += 1) {
                if (dayRowIndex % 7 == 0) {
                    daysInMonthIndex += 1;
                }
                daysInMonth.get(daysInMonthIndex).add(makeBinary(dayIndex + 1));
            }
            if (dayRowIndex % 7 != 0) {
                for (int dayIndex = 0; dayIndex < 7 - (dayRowIndex % 7); dayIndex += 1) {
                    daysInMonth.get(daysInMonthIndex).add("-");
                }
            }

            if (daysInMonthIndex == 4) {
                for (int dayIndex = 0; dayIndex < 7; dayIndex += 1) {
                    daysInMonth.get(daysInMonthIndex + 1).add("-");
                }
            }
        }
        result.add(monthsOfYear);
        result.add(daysOfWeeks);
        result.addAll(daysInMonth);
    }

    private int getDayOfWeek(int month) {
        Calendar xmas = new GregorianCalendar(year, month, 1);
        return (7 + xmas.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) % 7;
    }

    private int getDaysInMonth(int month) {
        Calendar xmas = new GregorianCalendar(year, month, 1);
        return xmas.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
