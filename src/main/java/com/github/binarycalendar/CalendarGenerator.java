/*
 * $Id$
 *
 * Copyright 2012 Valentyn Kolesnikov
 */
package com.github.binarycalendar;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CalendarGenerator.
 *
 * @author javadev
 * @version $Revision$ $Date$
 */
public class CalendarGenerator {
    public static void main(String[] args) {
        if (args.length < 1 || !args[0].matches("\\d+")) {
            Logger.getLogger(CalendarGenerator.class.getName()).log(Level.INFO,
                    "Binary calendar generator. Copyright (c) 2012 (javadev75@gmail.com)\n"
                    + "Usage: CalendarGenerator [year]");
            return;
        }
        List<List<String>> calendarData = new CalendarDataGenerator().generate(args[0]);
        new XlsGenerator("result.xls").generate(calendarData);
    }
}
