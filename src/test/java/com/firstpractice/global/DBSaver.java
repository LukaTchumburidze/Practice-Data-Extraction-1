package com.firstpractice.global;

import java.util.*;
import java.io.*;

import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sun.misc.FormattedFloatingDecimal;

import java.util.ArrayList;

/**
 * Created by Luka Tchumburidze on 24.03.2017.
 */
public class DBSaver {

    private static int[] Formattings;
    private static Random Rand = new Random();
    private static XSSFWorkbook Output = new XSSFWorkbook();

    public DBSaver (ArrayList <String> Head, String[][] Database, String Region, BufferedReader in) throws Exception {
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
                            System.out.println("Please input the proper version of this string: " + Database[j][i]);
                            Database[j][i] = in.readLine(); //!!!!
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
            File OutFile = new File ("output.xlsx");
            FileOutputStream OutStream = new FileOutputStream(OutFile);
            Output.write(OutStream);
            OutStream.close();
        } catch (Exception e) {

        }
    }

    private void FillFormattings () {
        Formattings = new int [Variables.TableWidth];
        //1 number
        //2 string
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

    private void CreateHeadRow (ArrayList <String> Head, XSSFSheet Sheet) {
        XSSFRow HeadRow = Sheet.createRow(0);
        for (int i = 0; i < Head.size(); i ++) {
            XSSFCell Cell = HeadRow.createCell(i);
            Cell.setCellValue(Head.get(i));
            Sheet.autoSizeColumn(i);
        }
    }

    private void AutoSizeColumns (int NofColumns, XSSFSheet Sheet) {
        for (int i = 0; i < NofColumns; i ++) {
            Sheet.autoSizeColumn(i);
        }
    }
}

