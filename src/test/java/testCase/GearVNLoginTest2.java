package testCase;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import dataProvider.LoginDataProvider;

public class GearVNLoginTest2 {
    public String baseUrl = "https://gearvn.com/";
    String driverPath =  "F:\\FPT Polytechnic\\SPRING 2024\\BLOCK 2\\SOF304_Kiem thu nang cao\\chromedriver-win64\\chromedriver.exe";
    public WebDriver driver;
    
    @BeforeTest
    public void launchBrowser() {
        System.out.println("launch chrome browser");
        driver = new ChromeDriver();
        driver.get(baseUrl);
    }
    
    @Test(dataProvider = "loginData", dataProviderClass = LoginDataProvider.class)
    public void GearVNLogin(String email, String password, String expectedResult) {
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
        
        // Kiểm tra truy cập vào phần tra cứu đơn hàng
        boolean canAccessOrderTracking = false;
        try {
            WebElement orderTrackingLink = driver.findElement(By.xpath("//a[@class='header-action_text logged']"));
            orderTrackingLink.click();
            canAccessOrderTracking = true;
        } catch (NoSuchElementException e) {
            System.out.println("Cannot find order tracking link.");
        }

        
        // Nếu có thể truy cập vào phần tra cứu đơn hàng, coi test case là pass
        if (canAccessOrderTracking) {
            System.out.println("Login successful. Can access order tracking.");
        } else {
            System.out.println("Login failed. Cannot access order tracking.");
            // Có thể throw một exception ở đây để đánh dấu test case fail
        }
        
        // Đăng xuất
        WebElement logout = driver.findElement(By.xpath("/html/body/div[1]/header/div/div/div/div[2]/div[6]/div[2]/div/div[3]/ul/li/a/div[2]/div"));
        js.executeScript("arguments[0].click();", logout);
        WebElement agreeBtn = driver.findElement(By.xpath("/html/body/div[4]/div/div[6]/button[1]"));
        js.executeScript("arguments[0].click();", agreeBtn);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            // Handle exception        	
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}