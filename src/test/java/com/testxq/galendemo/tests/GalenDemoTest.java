package com.testxq.galendemo.tests;


import com.testxq.galendemo.galentests.UITest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class GalenDemoTest {
    WebDriver webDriver;
    UITest uiTest;

    @BeforeSuite
    public void setUp() throws IOException {
        FileUtils.cleanDirectory(new File("build/galen-html-reports"));
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        uiTest = new UITest();
    }

    @BeforeTest
    public void openUrl() {
        webDriver.get("https://www.testxq.com");
    }

    @Test
    public void testOnHomePage() throws IOException {
        waitForPageLoad();
        System.out.println(webDriver.getTitle());
        Assert.assertTrue(webDriver.getTitle().contains("Test XQ"));
        uiTest.checkPage(webDriver, "homePage", "desktop");
        uiTest.checkPage(webDriver, "homePage", "mobile");
    }

    @Test
    public void testOnAcademy() throws IOException {
        webDriver.findElement(By.xpath("//span[contains(text(),'IT Solutions') and contains(@class,'menu')]")).click();
        waitForPageLoad();
        uiTest.checkPage(webDriver, "solutionsPage", "desktop");
        uiTest.checkPage(webDriver, "solutionsPage", "mobile");
    }


    @AfterTest
    public void tearDown() throws IOException {
        if (webDriver != null) {
            webDriver.quit();
        }
        uiTest.generateReport();
    }

    public void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").toString().equals("complete"));
    }

}
