# PracticeDataExtraction 1

# Brief Description
This project was made for just simple practice in IntelliJ IDEA IDE. Main.java scrapes http://walker.dgf.uchile.cl/Explorador/DAANC/ this site's charts and puts it in the Data.xlsx document (Main.java asks you which region's data you want to scrape and creates Sheet accordingly from scratch).

# Aim
Aim for me was to develop my skills further in Selenium browser automation tool. In this project I have became friends with Maven project management tool which works like a charm with SeleniumHQ. I have once again used Apache POI library to convert the simply scraped data into Excel's format.

# Things done so far
I have created working version of the project on Windows and Ubuntu. In both cases scraper uses ChromeDriver which works equally well in both cases.

# Things to do
I want to keep working on RaspberryPi branch to create the working version for Raspbian OS which works on Arm processor. The problem is following: there is no ChromeDriver available for arm processor. As it seems I'll use FirefoxDriver (geckodriver) for RPi.
