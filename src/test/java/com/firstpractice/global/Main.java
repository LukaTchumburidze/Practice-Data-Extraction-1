package com.firstpractice.global;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

/**
 * Created by Luka Tchumburidze on 23.03.2017.
 */

public class Main {

    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static String FBLink = "https://www.facebook.com/";
    private static WebDriverWait wait;

    /**
     * main method, which is called when class is run
     */

    public static void main(String[] args) {
        //Sets variable to local chromedriver's path
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        //Initialise new chromedriver and wait
        WebDriver driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.navigate().to(FBLink);


        //Close browser
        driver.close();

        //Close input buffer
        try {
            in.close();
        } catch (IOException e) {

        }

        System.exit(0);
    }
}