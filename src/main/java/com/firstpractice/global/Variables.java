package com.firstpractice.global;

import java.awt.*;

/**
 * Simple variables class, which stores some
 * constant and non-constant variables, which are
 * vital for the main program
 *
 * Created by Luka Tchumburidze on 23.03.2017.
 */
public class Variables {
    public static final String InitLink = "http://walker.dgf.uchile.cl/Explorador/DAANC/";

    public static final String AcceptXpath = "//*[@id=\"splash_enter\"]";
    public static final String DAANCXpath = "//*[@id=\"select_report_table\"]";
    public static final String MapXpath = "//*[contains(concat(\" \", normalize-space(@class), \" \"), \"highcharts-series-group\")]";
    public static final String RegionNameElXpath = "html/body/div[2]/div[3]/table/tbody/tr/td[3]/table[2]/tbody/tr/td/table/tbody/tr[2]/td/div/span[1]";
    public static final int EndofPattern = 10;
    public static String RegionClass = "";
    public static String RegionName = "";

    public static final String HeaderXpath = "//*[@id=\"DAA_Table_Container\"]/div/div[1]/table/thead/tr";

    public static final String TableContXpath = "//*[@id=\"DAA_Table_Container\"]/div/div[1]/table/tbody";
    public static final String NextPageWrapperXpath = "//*[@id=\"DAA_Table_Container\"]/div/div[2]/div[2]";
    public static final String NextPageXpath = "//*[@id=\"DAA_Table_Container\"]/div/div[2]/div[2]/div/div/span";
    public static final int EntriesinTable = 10;
    public static final int MaxNofEntries = 5000;
    public static final int TableWidth = 10;

    public static final String FileName = "Data.xlsx";
    public static final Color HeaderColor = new Color(70, 130, 180);
}
