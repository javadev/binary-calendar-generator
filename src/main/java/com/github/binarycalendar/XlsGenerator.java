/*
 * $Id$
 *
 * Copyright 2012 Valentyn Kolesnikov
 */
package com.github.binarycalendar;

import java.io.File;
import java.io.IOException;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WritableFont;
import jxl.write.WritableCellFormat;
import jxl.write.WriteException;

/**
 * XlsGenerator.
 *
 * @author javadev
 * @version $Revision$ $Date$
 */
public class XlsGenerator {
    private String fileName;

    public XlsGenerator(String fileName) {
        this.fileName = fileName;
    }

    public void generate(List<List<String>> calendarData) {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
            WritableSheet sheet = workbook.createSheet("Calendar " + calendarData.get(0).get(0), 0);

            int lineIndex = 0;
            for (List<String> line : calendarData) {
                int cellIndex = 0;
                for (String cell : line) {
                    WritableFont wf = new WritableFont(WritableFont.ARIAL, getFontSize(line.size()),
                        line.size() == 1 ? WritableFont.BOLD : WritableFont.NO_BOLD);
                    WritableCellFormat cf = new WritableCellFormat(wf);
                    if (line.size() != 1) {
                        cf.setWrap(true);
                    }
                    cf.setAlignment(jxl.format.Alignment.CENTRE);
                    Label label = new Label(getColumnNumber(cellIndex, line.size()), lineIndex, cell, cf);
                    sheet.addCell(label);
                    cellIndex += 1;
                }
                lineIndex += 1;
            }

            workbook.write();
            workbook.close();
        } catch (IOException e) {
            Logger.getLogger(XlsGenerator.class.getName()).log(Level.SEVERE, e.getMessage());
        } catch (WriteException e) {
            Logger.getLogger(XlsGenerator.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    private int getFontSize(int lineSize) {
        int result = 10;
        switch (lineSize) {
            case 1:
                result = 20;
                break;
            case 4:
                result = 15;
                break;
            default:
                result = 10;
                break;
        }
        return result;
    }

    private int getColumnNumber(int index, int lineSize) {
        int result = index;
        switch (lineSize) {
            case 1:
                result = 13;
                break;
            case 4:
                result = (index + 1) * 7 - 4;
                break;
            default:
                result = index;
                break;
        }
        return result;
    }
}
