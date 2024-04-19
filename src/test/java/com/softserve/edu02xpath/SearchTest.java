package com.softserve.edu02xpath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;


import io.github.bonigarcia.wdm.WebDriverManager;

public class SearchTest {
    private static final String BASE_URL = "https://demo.opencart.com/index.php";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private static final Long ONE_SECOND_DELAY = 1000L;
    private static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    private static WebDriver driver;
    private boolean isTestSuccessful = true;

    private void takeScreenShot(String testname)  {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./" + currentTime + "_" + testname + "_screenshot.png"));
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }

    private void takePageSource(String testname) {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        String pageSource = driver.getPageSource();
        byte[] strToBytes = pageSource.getBytes();
        Path path = Paths.get("./" + currentTime + "_" + testname + "_source.html");
        try {
            Files.write(path, strToBytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }


    private void presentationSleep() {
        presentationSleep(1);
    }

    private void presentationSleep(int seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY); // For Presentation ONLY
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        //
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("--remote-allow-origins=*");
        //driver = new ChromeDriver(options);
        driver = new ChromeDriver();
        //
        //driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS)); // 0 by default
        driver.manage().window().maximize();
        System.out.println("@BeforeAll executed");
    }

    @AfterAll
    public static void tear() {
        if (driver != null) {
            driver.quit(); // close()
        }
        System.out.println("@AfterAll executed");
    }

    @BeforeEach
    public void setupThis() {
        isTestSuccessful = false;
        driver.get(BASE_URL);
        presentationSleep(); // For Presentation ONLY
        System.out.println("\t@BeforeEach executed");
    }

    @AfterEach
    public void tearThis(TestInfo testInfo) throws InterruptedException {
        presentationSleep(); // For Presentation ONLY
        System.out.println("\t@AfterEach executed, name = " + testInfo.getDisplayName());
        //
        if (!isTestSuccessful) {
            takeScreenShot(testInfo.getDisplayName());
            takePageSource(testInfo.getDisplayName());
            System.out.println("\t\t@AfterEach, ERRROR name = " + testInfo.getDisplayName());
        }
        // TODO
        // Close Session
        System.out.println("\t@AfterEach executed");
        presentationSleep(4); // For Presentation ONLY
    }

    private WebElement getProductByName(String name) {
        WebElement result = null;
        List<WebElement> containers = driver.findElements(By.cssSelector("div#content div.col"));
        for (WebElement current : containers) {
            if (current.findElement(By.cssSelector("h4 > a")).getText().equals(name)) {
            //if (current.findElement(By.xpath(".//h4/a")).getText().equals(name)) {
                //`System.out.printf("***current = "+ current.getText());
                result = current;
                break;
            }
        }
        if (result == null) {
            // Develop Custom Exception
            throw new RuntimeException("WebElement by title/name: " + name + " not found");
        }
        return result;
    }

    @Test
    public void findByCss() {
        // Precondition
        WebElement usd = driver.findElement(By.cssSelector("a[href='USD']"));
        System.out.println("*** 1. usd.isDisplayed() = " + usd.isDisplayed());
        // Choose Curency
        driver.findElement(By.cssSelector("a.dropdown-toggle[href='#']")).click();
        presentationSleep(); // For Presentation ONLY
        System.out.println("*** 2. usd.isDisplayed() = " + usd.isDisplayed());
        //
        driver.findElement(By.cssSelector("a[href='USD']")).click();
        presentationSleep(); // For Presentation ONLY
        //
        //driver.findElement(By.cssSelector("a:contains('MacBook')")).click(); // Selenium ERROR
        //driver.findElement(By.cssSelector("div#content div.col h4:has(> a:contains('MacBook'))")).click(); // Selenium ERROR
        //driver.findElement(By.cssSelector("div#content div.col h4:has(> a)")).click();
        //
        // Search a $("div#content div.col h4 > a")
        // Search h4 $("div#content div.col h4:has(> a)")
        // Search Price $("div#content div.col h4:has(> a[href*='id=43']) + p + div span.price-new")
        // Search Price $("div#content div.col div:has(> h4 > a[href*='id=43']) span.price-new")
        // Search Price $("div#content div.col div:has(> h4 > a[href*='id=43']) span.price-new")
        //
        // Check
        //WebElement price = driver.findElement(By.cssSelector("div#content div.col div:has(> h4 > a[href*='id=43']) span.price-new")); // id=43 Hardcode Invalid Solution
//        WebElement price = getProductByName("MacBook").findElement(By.cssSelector("span.price-new"));
        //WebElement price = getProductByName("MacBook").findElement(By.xpath(".//p[@class='price']"));
        //WebElement price = driver.findElement(By.cssSelector("#content > div:nth-child(8) > div:nth-child(2) > div > div:nth-child(2) > div.caption > h4 > a"));
        //
        WebElement price = getProductByName("iPhone").findElement(By.cssSelector("span.price-new"));
        //
        System.out.println("price.getText() = " + price.getText());
        //
        // Scrolling by Actions
        Actions action = new Actions(driver);
        action.moveToElement(price).perform();
        //
        // Scrolling by JavaScript injection
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", price);
        presentationSleep(); // For Presentation ONLY
        //
        //Assertions.assertTrue(price.getText().contains("$602.00"));
        Assertions.assertTrue(price.getText().contains("$123.20"));
        //
        // Return to Previous State
        presentationSleep(); // For Presentation ONLY
        isTestSuccessful = true;
    }

    //@Test
    public void findByXPath() {
        // Precondition
        // Choose Curency
        driver.findElement(By.xpath("//a[@href='#']")).click();
        presentationSleep(); // For Presentation ONLY
        driver.findElement(By.xpath("//a[@href='USD']")).click();
        presentationSleep(); // For Presentation ONLY
        //
        // Steps
        //
        // $x("//a[text()='MacBook']/../..//span[contains(text(),'602')]")
        // "//a[text()='MacBook']/../..//span[@class='price-new']"  // OK
        // "//a[text()='MacBook']/../following-sibling::div/span[@class='price-new']"
        WebElement price = driver.findElement(By.xpath("//a[text()='MacBook']/../following-sibling::div/span[@class='price-new']"));
        // Scrolling by Action class
        Actions action = new Actions(driver);
        action.moveToElement(price).perform();
        presentationSleep(); // For Presentation ONLY
        // Check
        System.out.println("price.getText() = " + price.getText());
        Assertions.assertTrue(price.getText().contains("$602.00"));
        //
        // Return to Previous State
        presentationSleep(); // For Presentation ONLY
        isTestSuccessful = true;
    }

}
