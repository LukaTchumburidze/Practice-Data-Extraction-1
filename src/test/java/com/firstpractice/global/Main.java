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
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Luka Tchumburidze on 23.03.2017.
 */

public class Main {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static WebDriverWait wait;
    private static WebDriver driver;

    private static ArrayList <String> Head;
    private static String[][] Database;

    /**
     * main method, which is called when class is run
     */

    public static void main(String[] args) throws Exception{
        //Sets variable to local chromedriver's path
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        //Initialise new chromedriver and wait
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

        driver.navigate().to(Variables.InitLink);

        WebElement AcceptButton = driver.findElement(By.xpath(Variables.AcceptXpath));
        AcceptButton.click();

        WebElement DAANC = driver.findElement(By.xpath(Variables.DAANCXpath));
        DAANC.click();

        WebElement Region = MakeWait(Variables.TableContXpath, "Xpath"); //!!!!!
        Region = MakeWait(Variables.RegionClass, "Class");
        Region.click();

        InitialiseDatabase ();
        for (int i = 0; i < Variables.NofPages; i ++) {
            ExtractCurrentTable (i * Variables.EntriesinTable);
            GotoNextPage ();
            System.out.println ("Waiting started!");
            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
            try {
                driver.findElement(By.xpath("randomwordramdomword123"));
            } catch (Exception e) {

            }
            System.out.println ("Waiting completed!");
        }

        in.read();
        //Close browser
        driver.close();

        //Close input buffer
        try {
            in.close();
        } catch (IOException e) {

        }

        System.exit(0);
    }

    private static void GotoNextPage () {
        WebElement NextButton = driver.findElement(By.xpath(Variables.NextPageXpath));
        NextButton.click();
    }

    private static void InitialiseDatabase () {

        System.out.println ("Waiting started!");
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        WebElement HeadEl = MakeWait(Variables.HeaderXpath, "Xpath"); ///!!!!
        System.out.println ("Waiting completed!");

        Head = new ArrayList<String>();

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
                System.out.print("No");
                break;
            }
        }

        System.out.println(Head.size());
        System.out.println("Competed Initialisation");
    }

    private static void ExtractCurrentTable (int FirstInd) {
        Database = new String [Head.size()][10000];

        //MakeWait (Variables.TableContXPath, "Xpath");
        for (int i = 0; i < Variables.EntriesinTable; i ++) {
            for (int j = 0; j < Head.size(); j ++) {
                WebElement CurCell = MakeWait(Variables.TableContXpath + "/tr[" + (i+1) + "]/td[" + (j+1) + "]", "Xpath");
                Database[j][FirstInd + i] = CurCell.getText();
                System.out.print (Database[j][FirstInd + i]);
                System.out.print ("       ");
            }
            System.out.println ();
        }
    }

    private static WebElement MakeWait (String Loc, String type) {
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
                System.out.println (Loc);
            }
        }

        return El;
    }
}