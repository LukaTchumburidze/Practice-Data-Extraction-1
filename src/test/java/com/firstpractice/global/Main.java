package com.firstpractice.global;

import com.thoughtworks.selenium.SeleniumException;
import javafx.scene.chart.PieChart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.Region;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Luka Tchumburidze on 23.03.2017.
 */

public class Main {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static WebDriverWait wait;
    private static WebDriver driver;

    /** Chart head */
    private static ArrayList <String> Head;
    /** Chart Data */
    private static String[][] Database;

    /**
     * Main method, which is called when class is run
     */

    public static void main(String[] args) throws Exception{
        //Sets variable to local chromedriver's path
        System.setProperty("webdriver.chrome.driver", "chromedriver");

        //Initialise new chromedriver and wait
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

        driver.navigate().to(Variables.InitLink);

        NavigatetoTheMainChart ();

        SelectRegion ();
        WebElement Region = WaitUntilAppears(Variables.RegionClass, "Class");
        Region.click();

        InitialiseDatabase ();
        for (int i = 0; GotoNextPage(); ExtractCurrentPage (i * Variables.EntriesinTable), i ++) {
            System.out.println (i);
            //System.out.println ("Waiting started!");
            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
            MakeBrowserWait ();
            //System.out.println ("Waiting completed!");
        }
        DBSaver output = new DBSaver(Head, Database, Variables.RegionName, in);

        in.read();
        //Close browser
        driver.close();


        //Close input buffer
        in.close();

        System.exit(0);
    }

    /**
     * Navigate to the main chart and wait until it appears
     */

    private static void NavigatetoTheMainChart () {
        WebElement AcceptButton = driver.findElement(By.xpath(Variables.AcceptXpath));
        AcceptButton.click();

        WebElement DAANC = driver.findElement(By.xpath(Variables.DAANCXpath));
        DAANC.click();

        WaitUntilAppears(Variables.TableContXpath, "Xpath");
    }

    /**
     * Shows region list and lets to select one you want to scrape
     */

    private static void SelectRegion () {
        WebElement RegionMap = driver.findElement(By.xpath(Variables.MapXpath));
        List <WebElement> Regions = RegionMap.findElements(By.xpath(".//*"));

        //Get regions
        for (int i = 0; i < Regions.size(); i ++) {
            System.out.println ("" + (i+1) + ") " + Regions.get(i).getAttribute("class"));
        }

        //Print regions
        int ind = 1;
        try {
            System.out.println ("Write index of a region");
            ind = Integer.parseInt(in.readLine());
        } catch (Exception e) {
            System.out.println ("Couldn't choose region");
        }
        //Get selected region's class and add it to the Varibales.java's constants
        ind --;
        Variables.RegionClass = Regions.get(ind).getAttribute("class");
        String temp[] = Variables.RegionClass.split(" ");
        Variables.RegionClass = temp[1];
        System.out.println (Variables.RegionClass);
    }

    /**
     * As I have observed, after calling driver's implicit wait,
     *you need to force the driver to throw some exception to make wait
     *so, This function makes sure to throw exception
     */

    private static void MakeBrowserWait () {
        try {
            driver.findElement(By.xpath("randomwordramdomword123"));
        } catch (Exception e) {

        }
    }

    /**
     *  This function clicks appropriate button to go to next page
     *  if click is possible it returns true, in other hand it returns false
     *
     * @return
     */

    private static boolean GotoNextPage () {
        WebElement NextButton = driver.findElement(By.xpath(Variables.NextPageXpath));
        String Attr = driver.findElement(By.xpath(Variables.NextPageWrapperXpath)).getAttribute("aria-disabled");
        if (Attr != null) {
            return false;
        }
        System.out.println ("Next page is available");
        NextButton.click();
        return true;
    }

    /**
     *  Fills up header array with values of header row
     *  and initialises database matrix
     */

    private static void InitialiseDatabase () {

        //System.out.println ("Waiting started!");
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        MakeBrowserWait ();
        //System.out.println ("Waiting completed!");
        WebElement HeadEl = WaitUntilAppears(Variables.HeaderXpath, "Xpath");
        Variables.RegionName = driver.findElement(By.xpath(Variables.RegionNameElXpath)).getText().substring(Variables.EndofPattern);
        System.out.println(Variables.RegionName);

        Head = new ArrayList<String>();
        Database = new String [Variables.TableWidth][Variables.MaxNofEntries];

        int ind;
        while (true) {
            try {
                ind = 1;
                while (true) {
                    WebElement HeaderChild = driver.findElement(By.xpath(Variables.HeaderXpath + "/th[" + ind + "]"));
                    System.out.println (HeaderChild.getText());
                    Head.add(HeaderChild.getText());
                    ind ++;
                }
            } catch (Exception e) {
                System.out.print("Completed scraping of header row");
                break;
            }
        }

        System.out.println("Competed Initialisation");
    }

    /**
     *  Extracts the current page of the table from the site and
     *  stores it in the Database array
     * @param FirstInd Appropriate index for the first entry of current page in the Database array
     */

    private static void ExtractCurrentPage (int FirstInd) {
        for (int i = 0; i < Variables.EntriesinTable; i ++) {
            for (int j = 0; j < Head.size(); j ++) {
                try {
                    WebElement CurCell = driver.findElement(By.xpath(Variables.TableContXpath + "/tr[" + (i+1) + "]/td[" + (j+1) + "]"));
                    Database[j][FirstInd + i] = CurCell.getText();
                    System.out.print (String.format("%30s", Database[j][FirstInd + i]));
                } catch (Exception e) {
                    return;
                }
            }
            System.out.println ();
        }
    }

    /**
     *  By simple try/catch block it waits until
     *  desired element is available
     *
     * @param Loc Either xpath or the class of the desired element
     * @param type Gives information about Loc. Can be equal to Xpath or Class.
     * @return
     */

    private static WebElement WaitUntilAppears (String Loc, String type) {
        WebElement El = null;
        //System.out.println (Loc);

        while (true) {
            try {
                if (type == "Xpath") {
                    El = driver.findElement(By.xpath(Loc));
                }
                if (type == "Class") {
                    El = driver.findElement(By.className(Loc));
                }

                break;
            } catch (Exception e) {
                //System.out.println (Loc);
            }
        }
        return El;
    }
}