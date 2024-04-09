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
import dataProvider.LogoutDataProvider;

public class GearVNLogoutTest {
	public String baseUrl = "https://gearvn.com/";
    String driverPath =  "F:\\FPT Polytechnic\\SPRING 2024\\BLOCK 2\\SOF304_Kiem thu nang cao\\chromedriver-win64\\chromedriver.exe";
    public WebDriver driver;
    
    @BeforeTest
    public void launchBrowser() {
        System.out.println("launch chrome browser");
        driver = new ChromeDriver();
        driver.get(baseUrl);
    }
    
    @Test(dataProvider = "logoutData", dataProviderClass = LogoutDataProvider.class)
    public void GearVNLogout(String email, String password, String expectedResult) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement loginElement = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div/div[2]/div[6]/div[2]/div/div[1]/div[2]/button[1]"));
        js.executeScript("arguments[0].click();", loginElement);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            // Handle exception
        }
        WebElement emailInput = driver.findElement(By.id("login-customer[email]"));
        emailInput.clear();
        emailInput.sendKeys(email);
        WebElement passwordInput = driver.findElement(By.id("login-customer[password]"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        WebElement loginBtn = driver.findElement(By.xpath("/html/body/div[1]/div[11]/div/div/div[2]/div/div/div[1]/form/div[4]/button"));
        js.executeScript("arguments[0].click();", loginBtn);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            // Handle exception
        }
    	WebElement logout = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div/div[2]/div[6]/div[2]/div/div[3]/ul/li/a/div[2]/div"));
        js.executeScript("arguments[0].click();", logout);
        WebElement agreeBtn = driver.findElement(By.xpath("/html/body/div[4]/div/div[6]/button[1]"));
        js.executeScript("arguments[0].click();", agreeBtn);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            // Handle exception        	
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
