package com.testxq.galendemo.galentests;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class UITest {
    LayoutReport layoutReport;
    GalenTestInfo galenTestInfo;
    List<GalenTestInfo> tests = new LinkedList<>();

    /**
     * Perform Visual Tests based on PageName
     *
     * @param webDriver
     * @param pageName
     * @param layoutType
     * @throws IOException
     */
    public void checkPage(WebDriver webDriver, String pageName, String layoutType) throws IOException {
        resizeWindow(layoutType, webDriver);
        layoutReport = Galen.checkLayout(webDriver, String.format("galen-specs/%s.gspec", pageName), asList(layoutType));
        resizeWindow("desktop", webDriver);
        galenTestInfo = GalenTestInfo.fromString(String.format("%s on %s Test", pageName, layoutType));
        galenTestInfo.getReport().layout(layoutReport, String.format("Check %s on %s layout", pageName, layoutType));
        tests.add(galenTestInfo);
    }

    /**
     * Resize Window after switch to different layout
     *
     * @param layoutType
     * @param webDriver
     */
    public void resizeWindow(String layoutType, WebDriver webDriver) {
        if (layoutType.equalsIgnoreCase("mobile")) {
            webDriver.manage().window().setSize(new Dimension(400, 800));
            webDriver.navigate().refresh();
        } else {
            webDriver.manage().window().maximize();
        }
    }

    /**
     * Generates Galen Report for all GSpec
     *
     * @throws IOException
     */
    public void generateReport() throws IOException {
        new HtmlReportBuilder().build(tests, "./build/galen-html-reports");
    }

}
