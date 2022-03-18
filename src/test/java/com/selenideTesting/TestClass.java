package com.selenideTesting;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class TestClass {

    public long startTime;
    public long endTime;

    @BeforeClass(alwaysRun = true)
    public void start() {
        startTime = System.currentTimeMillis();
    }

    @Test
    public void openBingAndTypeInput() {
        Configuration.headless = true;
        open("https://www.bing.com");
        SelenideElement queryBlock = $(By.name("q"));
        queryBlock.should(Condition.appear);
        queryBlock.sendKeys("Notepad");
        System.out.println(title());

    }

    @Test
    public void forwardAndBack() throws InterruptedException {
        Configuration.headless = true;
        open("https://www.bing.com");
        $(By.name("q")).val("Notepad").pressEnter();
        Thread.sleep(2000);
        System.out.println(title());
        back();
        Thread.sleep(2000);
        System.out.println(title());
        forward();
        Thread.sleep(2000);
        System.out.println(title());
        refresh();


    }

    @Test
    public void bingAndClick() throws InterruptedException {
        open("https://www.bing.com");
        $(By.name("q")).val("Notepad").pressEnter();
        Thread.sleep(2000);
        $(By.xpath("//*[@id=\"b-scopeListItem-images\"]/a")).click();
        System.out.println(title());
        Thread.sleep(5000);
    }

    @Test
    public void switchingToAnotherWindowTest() throws InterruptedException {
        Configuration.headless = true;
        open("https://www.griddynamics.com/");
        SelenideElement linkedIn = $(By.xpath("/html/body/gd-root/gd-app-shell/div/div[2]/gd-footer/footer/div/div[1]/div[3]/gd-social-networks/div/a[3]"));
        linkedIn.scrollTo();
        linkedIn.hover();
        Thread.sleep(4000);
        linkedIn.click();
        switchTo().window(1);
        Thread.sleep(5000);
        closeWindow();
        closeWebDriver();
    }

    @Test
    public void webDriverRunnerTest() throws InterruptedException {
        open("https://facebook.com");
        WebDriverRunner.getAndCheckWebDriver().manage().window().maximize();
        Thread.sleep(3000);
        System.out.println(WebDriverRunner.source());
        if (WebDriverRunner.supportsJavascript()) {
            System.out.println("This browser uses js!");
        }
        List<File> files = WebDriverRunner.getBrowserDownloadsFolder().files();
        Assert.assertEquals(files.size(), 10, "Files should not be empty but they are");
        closeWindow();
    }

    @Test
    public void authPromptTesting() throws InterruptedException {
        open("https://the-internet.herokuapp.com/basic_auth",
                "", "admin", "admin");
        Thread.sleep(2000);
        String header = $("h3").getText();
        String paragraph = $("p").getText();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(header, "Basic Auth", "The header should be Basic auth but it is not");
        softAssert.assertEquals(paragraph, "Congratulations! You must have the proper credentials.", "The paragraph should consist of 7 words but it does not");
        softAssert.assertAll();
        closeWindow();
    }

    @Test
    public void alertsTest() {
        open("https://the-internet.herokuapp.com/");
        $(By.xpath("//a[text()=\"JavaScript Alerts\"]")).click();
        $(By.xpath("//button[text()=\"Click for JS Alert\"]")).click();
        Alert alert = switchTo().alert();

        SoftAssert softAssert = new SoftAssert();
        String textOfAlert = alert.getText();
        softAssert.assertEquals(textOfAlert, "I am a JS Alert", "The text is different from the one that was expected");
        alert.accept();
        String acceptanceConfirmation = $(By.cssSelector("p")).getText();
        softAssert.assertEquals(acceptanceConfirmation, "You successfully clicked an alert", "The paragraph should contain message of success but it doesn't");
    }

    @Test
    public void confirmationAlertAcceptanceTest() {
        open("https://the-internet.herokuapp.com/");
        $(By.xpath("//a[text()=\"JavaScript Alerts\"]")).click();
        $(By.xpath("//button[text()=\"Click for JS Confirm\"]")).click();
        Alert alert = switchTo().alert();

        SoftAssert softAssert = new SoftAssert();
        String textOfAlert = alert.getText();
        System.out.println(textOfAlert);
        softAssert.assertEquals(textOfAlert, "I am a JS Confirm", "The text is different from the one that was expected");
        alert.accept();
        String acceptanceConfirmation = $(By.cssSelector("p")).getText();
        System.out.println(acceptanceConfirmation);
        softAssert.assertEquals(acceptanceConfirmation, "You clicked: Ok", "The paragraph should contain message of success but it doesn't");
    }

    @Test
    public void confirmationAlertDeclineTest() {
        open("https://the-internet.herokuapp.com/");
        $(By.xpath("//a[text()=\"JavaScript Alerts\"]")).click();
        $(By.xpath("//button[text()=\"Click for JS Confirm\"]")).click();
        Alert alert = switchTo().alert();

        SoftAssert softAssert = new SoftAssert();
        String textOfAlert = alert.getText();
        System.out.println(textOfAlert);
        softAssert.assertEquals(textOfAlert, "I am a JS Confirm", "The text is different from the one that was expected");
        alert.dismiss();
        String acceptanceConfirmation = $(By.cssSelector("p")).getText();
        System.out.println(acceptanceConfirmation);
        softAssert.assertEquals(acceptanceConfirmation, "You clicked: Cancel", "The paragraph should contain message of having clicked cancellation but it doesn't");
    }

    @Test
    public void promptAcceptanceWithEmptyKeysTest() {
        open("https://the-internet.herokuapp.com/");
        $(By.xpath("//a[text()=\"JavaScript Alerts\"]")).click();
        $(By.xpath("//button[text()=\"Click for JS Prompt\"]")).click();
        Alert alert = switchTo().alert();

        SoftAssert softAssert = new SoftAssert();
        String textOfAlert = alert.getText();
        System.out.println(textOfAlert);
        softAssert.assertEquals(textOfAlert, "I am a JS Prompt", "The text is different from the one that was expected");

        alert.accept();
        String acceptanceConfirmation = $(By.cssSelector("p")).getText();
        System.out.println(acceptanceConfirmation);
        softAssert.assertEquals(acceptanceConfirmation, "You entered:", "The paragraph should contain message of success but it doesn't");

    }

    @Test
    public void promptAcceptanceWithKeySentTest() throws InterruptedException {
        open("https://the-internet.herokuapp.com/");
        $(By.xpath("//a[text()=\"JavaScript Alerts\"]")).click();
        $(By.xpath("//button[text()=\"Click for JS Prompt\"]")).click();
        Alert alert = switchTo().alert();
        Thread.sleep(2000);
        SoftAssert softAssert = new SoftAssert();
        String textOfAlert = alert.getText();
        System.out.println(textOfAlert);
        softAssert.assertEquals(textOfAlert, "I am a JS prompt", "The text is different from the one that was expected");


        alert.sendKeys("something");
        alert.accept();
        String acceptanceConfirmation = $(By.cssSelector("#result")).getText();
        System.out.println(acceptanceConfirmation);
        softAssert.assertEquals(acceptanceConfirmation, "You entered: something", "The paragraph should contain message of success but it doesn't");

    }

    @Test
    public void promptDeclineTest() {
        open("https://the-internet.herokuapp.com/");
        $(By.xpath("//a[text()=\"JavaScript Alerts\"]")).click();
        $(By.xpath("//button[text()=\"Click for JS Prompt\"]")).click();
        Alert alert = switchTo().alert();

        SoftAssert softAssert = new SoftAssert();
        String textOfAlert = alert.getText();
        System.out.println(textOfAlert);
        softAssert.assertEquals(textOfAlert, "I am a JS Prompt", "The text is different from the one that was expected");

        alert.dismiss();
        String dismissConfirmation = $(By.cssSelector("p")).getText();
        System.out.println(dismissConfirmation);
        softAssert.assertEquals(dismissConfirmation, "You entered: null", "The paragraph should contain message with null value as our input");

    }

    @Test
    public void dropDownTest() throws InterruptedException {
        open("https://www.globalsqa.com/demo-site/select-dropdown-menu/");
        SelenideElement dropDown = $("select");
        dropDown.selectOption("Canada");
        Thread.sleep(2000);
        dropDown.selectOption(45);
        Thread.sleep(2000);
        dropDown.selectOptionByValue("MEX");
        Thread.sleep(2000);
    }

    @Test
    public void dragAndDropTest() throws InterruptedException {
        open("https://www.globalsqa.com/demo-site/draganddrop/");
        WebDriverRunner.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        $(By.id("Accepted Elements")).click();
        switchTo().frame($(By.xpath("//*[@id=\"post-2669\"]/div[2]/div/div/div[2]/p/iframe")));
        switchTo().activeElement();
        SelenideElement pictureOne = $(By.id("draggable"));


        SelenideElement droppable = $("#droppable");
        System.out.println(droppable.getText());
        actions().clickAndHold(pictureOne).moveToElement(droppable).release().build().perform();
        switchTo().defaultContent();
        $(By.id("Photo Manager")).click();
        Thread.sleep(2000);
        closeWindow();
    }

    @AfterClass(alwaysRun = true)
    public void endTime() {
        endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime) / 1000;
        System.out.println("Time of execution of the test suite: " + totalTime);
    }
}
