package testCase;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import dataProvider.SearchDataProvider;

public class GearVNSearchTest {
	public String baseUrl = "https://gearvn.com/";
    String driverPath =  "F:\\FPT Polytechnic\\SPRING 2024\\BLOCK 2\\SOF304_Kiem thu nang cao\\chromedriver-win64\\chromedriver.exe";
    public WebDriver driver;
    
    @BeforeTest
    public void launchBrowser() {
        System.out.println("launch chrome browser");
        driver = new ChromeDriver();
        driver.get(baseUrl);
    }
    
    @Test(dataProvider = "searchData", dataProviderClass = SearchDataProvider.class)
    public void GearVNSearch( String keyword, String expectedResult) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement keywordInput = driver.findElement(By.id("inputSearchAuto"));
        keywordInput.clear();
        keywordInput.sendKeys(keyword);
        
        WebElement searchBtn = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div/div[2]/div[1]/div/form/button/svg/path[1]"));
        js.executeScript("arguments[0].click();", searchBtn);
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (Exception e) {
            // Handle exception
        }

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
