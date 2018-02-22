package com.firstpractice.global;

import java.awt.*;
import java.util.*;
import java.io.*;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import java.util.ArrayList;

/**
 * Created by Luka Tchumburidze on 24.03.2017.
 */
public class DBSaver {

    /**
     * Stores indicators of the types of columns,
     * 1 means number 2 means string
     */
    private static int[] Formattings;
    private static XSSFWorkbook Output;
    private static File OutputFile;

    /**
     *  Constructor of this Class, which takes
     *  care of all the necessary actions to store
     *  the extracted data in appropriate sheet of the Data.xlsx file
     *
     * @param Head Usually same as Main.java's Head Array
     * @param Database Usually same as Main.java's Database Array
     * @param Region Name of the region, which is needed for naming the Sheet itself
     * @param in BufferedReader for console reading
     * @throws Exception
     */

    public DBSaver (ArrayList <String> Head, String[][] Database, String Region, BufferedReader in) throws Exception {

        ReadTheOutputFile ();
        FillFormattings ();


        XSSFSheet CurSheet = Output.createSheet(Region);
        CreateHeadRow (Head, CurSheet);
        for (int i = 0; Database[0][i] != null; i ++) {
            XSSFRow Row = CurSheet.createRow(i + 1);
            for (int j = 0; j < Database.length; j ++) {
                XSSFCell Cell = Row.createCell(j);
                switch (Formattings[j]) {
                    case 1:
                        try {
                            Cell.setCellValue(Double.parseDouble(Database[j][i]));
                        } catch (Exception e) {
                            System.out.println("There was improper number: " + Database[j][i]);
                            String[] Temp = Database[j][i].split(",");
                            double numb = Double.parseDouble(Temp[0]) * 1000 + Double.parseDouble(Temp[1]);
                            Cell.setCellValue(numb);
                        }
                    break;

                    case 2:
                        Cell.setCellValue(Database[j][i]);
                        break;
                }
            }
        }
        AutoSizeColumns (Head.size(), CurSheet);
        try {
            FileOutputStream OutStream = new FileOutputStream(OutputFile);
            Output.write(OutStream);
            OutStream.close();
        } catch (Exception e) {

        }
    }

    /**
     * Reads the Excel file if there is any and
     * prints names of the sheets which are
     * already in the excel file.
     * If Excel file is not available then it will be
     * created from scratch
     *
     */

    private void ReadTheOutputFile () {
        try {
            OutputFile = new File (Variables.FileName);
            FileInputStream InStream = new FileInputStream (OutputFile);
            Output = new XSSFWorkbook(InStream);

            InStream.close();
        } catch (IOException e) {
            /*
                if there isn't any file IOException
                will be thrown and we will make workbook,
                therefore excel file, from scratch
             */
            Output = new XSSFWorkbook();
        }
        Iterator <XSSFSheet> iterator = Output.iterator();
        while (iterator.hasNext()) {
            XSSFSheet Sheet = iterator.next();
            System.out.println (Sheet.getSheetName());
        }
    }

    /**
     * Simply fills Formattings array
     */

    private void FillFormattings () {
        Formattings = new int [Variables.TableWidth];
        Formattings[0] = 1;
        Formattings[1] = 1;
        Formattings[2] = 2;
        Formattings[3] = 2;
        Formattings[4] = 2;
        Formattings[5] = 1;
        Formattings[6] = 2;
        Formattings[7] = 1;
        Formattings[8] = 1;
        Formattings[9] = 1;
    }

    /**
     *
     * @param Head Header row of the sheet
     * @param Sheet Sheet in which we add head row
     */

    private void CreateHeadRow (ArrayList <String> Head, XSSFSheet Sheet) {
        XSSFCellStyle Style = Output.createCellStyle();
        Style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        Style.setFillForegroundColor (new XSSFColor(Variables.HeaderColor));
        Style.setLeftBorderColor(new XSSFColor(new Color (255, 255, 255)));
        Style.setBorderLeft(CellStyle.BORDER_THIN);
        XSSFRow HeadRow = Sheet.createRow(0);
        for (int i = 0; i < Head.size(); i ++) {
            XSSFCell Cell = HeadRow.createCell(i);
            Cell.setCellValue(Head.get(i));
            Cell.setCellStyle(Style);
        }
    }

    /**
     *  Autosizes all the columns in the desired sheet
     *
     * @param NofColumns Number of columns
     * @param Sheet Sheet in which we autosize all columns
     */

    private void AutoSizeColumns (int NofColumns, XSSFSheet Sheet) {
        for (int i = 0; i < NofColumns; i ++) {
            Sheet.autoSizeColumn(i);
        }
    }
}

